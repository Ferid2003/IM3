package farid.aghazada.resource_service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farid.aghazada.resource_service.Client.SongServiceClient;
import farid.aghazada.resource_service.DTO.SongMetadataRequestDTO;
import farid.aghazada.resource_service.Entity.ResourceEntity;
import farid.aghazada.resource_service.Exception.CSVFormatException;
import farid.aghazada.resource_service.Exception.CSVLengthRestrictionException;
import farid.aghazada.resource_service.Exception.ContentMismatchException;
import farid.aghazada.resource_service.Exception.InvalidIdException;
import farid.aghazada.resource_service.Exception.InvalidMp3FileException;
import farid.aghazada.resource_service.Exception.ResourceNotFoundException;
import farid.aghazada.resource_service.Repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final SongServiceClient songServiceClient;

    public Map<String, Integer> uploadMp3(byte[] audioData, String contentType) {
        if (!"audio/mpeg".equalsIgnoreCase(contentType)) {
            throw new ContentMismatchException("Invalid file format: " + contentType + ". Only MP3 files are allowed");
        }

        SongMetadataRequestDTO tags = extractTags(audioData);

        ResourceEntity saved = resourceRepository.save(
                ResourceEntity.builder()
                        .audioData(audioData)
                        .build()
        );

        songServiceClient.createSongMetadata(new SongMetadataRequestDTO(
                saved.getId(), tags.name(), tags.artist(), tags.album(), tags.duration(), tags.year()
        ));

        return Map.of("id", saved.getId());
    }

    public byte[] getMp3(Integer id) {
        if (id <= 0) {
            throw new InvalidIdException("Invalid value '" + id + "' for ID. Must be a positive integer");
        }
        return resourceRepository.findById(id)
                .map(ResourceEntity::getAudioData)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with ID=" + id + " not found"));
    }

    @Transactional
    public Map<String, List<Integer>> deleteResources(String csvIds) {
        if (csvIds.length() > 200) {
            throw new CSVLengthRestrictionException(
                    "CSV string is too long: received " + csvIds.length() + " characters, maximum allowed is 200");
        }
        List<Integer> parsedIds = parseCsvIds(csvIds);
        List<Integer> existingIds = resourceRepository.findAllById(parsedIds).stream()
                .map(ResourceEntity::getId)
                .toList();
        resourceRepository.deleteAllByIdInBatch(existingIds);
        if (!existingIds.isEmpty()) {
            String existingCsv = existingIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            songServiceClient.deleteSongMetadata(existingCsv);
        }
        return Map.of("ids", existingIds);
    }

    private List<Integer> parseCsvIds(String csvIds) {
        List<Integer> result = new ArrayList<>();
        for (String token : csvIds.split(",")) {
            try {
                int value = Integer.parseInt(token.trim());
                if (value <= 0) {
                    throw new NumberFormatException();
                }
                result.add(value);
            } catch (NumberFormatException e) {
                throw new CSVFormatException("Invalid ID format: '" + token + "'. Only positive integers are allowed");
            }
        }
        return result;
    }

    private SongMetadataRequestDTO extractTags(byte[] audioData) {
        try {
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            try (TikaInputStream stream = TikaInputStream.get(audioData)) {
                parser.parse(stream, new BodyContentHandler(-1), metadata, new ParseContext());
            }
            String name = metadata.get(TikaCoreProperties.TITLE);
            String artist = metadata.get(XMPDM.ARTIST);
            String album = metadata.get(XMPDM.ALBUM);
            String year = metadata.get(XMPDM.RELEASE_DATE);
            String duration = toMinutesSeconds(metadata.get(XMPDM.DURATION));
            return new SongMetadataRequestDTO(null, name, artist, album, duration, year);
        } catch (Exception e) {
            throw new InvalidMp3FileException("Uploaded file is not a valid MP3 file");
        }
    }

    private String toMinutesSeconds(String durationSeconds) {
        double totalSeconds = Double.parseDouble(durationSeconds);
        int minutes = (int) totalSeconds / 60;
        int seconds = (int) totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
