package farid.aghazada.song_service.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import farid.aghazada.song_service.DTO.Request.SongCreationDTO;
import farid.aghazada.song_service.DTO.Response.SongResponseDTO;
import farid.aghazada.song_service.Entity.SongMetadataEntity;
import farid.aghazada.song_service.Exception.CSVFormatException;
import farid.aghazada.song_service.Exception.CSVLengthRestrictionException;
import farid.aghazada.song_service.Exception.InvalidIdException;
import farid.aghazada.song_service.Exception.SongMetadataAlreadyExistsException;
import farid.aghazada.song_service.Exception.SongMetadataNotFoundException;
import farid.aghazada.song_service.Repository.SongRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public Map<String, Integer> createSongMetadata(SongCreationDTO songMetadata) {
        if (songRepository.findById(songMetadata.id()).isPresent()) {
            throw new SongMetadataAlreadyExistsException(songMetadata.id());
        }
        SongMetadataEntity songMetadataEntity = songRepository.save(
                SongMetadataEntity.builder()
                .id(songMetadata.id())
                .name(songMetadata.name())
                .artist(songMetadata.artist())
                .album(songMetadata.album())
                .duration(songMetadata.duration())
                .year(songMetadata.year())
                .build()
        );
        Map<String, Integer> response = new HashMap<>();
        response.put("id", songMetadataEntity.getId());
        return response; 
    }

    public SongResponseDTO getSongMetadata(Integer id) {
        if (id <= 0) {
            throw new InvalidIdException("Invalid value '" + id + "' for ID. Must be a positive integer");
        }
        Optional<SongMetadataEntity> songMetadata = songRepository.findById(id);
        if (songMetadata.isPresent()) {
            return SongResponseDTO.fromSongMetadataEntity(songMetadata.get());
        }
        throw new SongMetadataNotFoundException("Song metadata for ID=" + id + " not found");
    }

    public Map<String, List<Integer>> deleteSongMetadatas(String ids) {
        if (ids.length() > 200) {
            throw new CSVLengthRestrictionException(
                    "CSV string is too long: received " + ids.length() + " characters, maximum allowed is 200");
        }
        List<Integer> parsedIds = getIntegerIdsFromCSV(ids);
        List<Integer> existingIds = songRepository.findAllById(parsedIds).stream()
                .map(SongMetadataEntity::getId)
                .toList();
        songRepository.deleteAllByIdInBatch(existingIds);
        Map<String, List<Integer>> response = new HashMap<>();
        response.put("ids", existingIds);
        return response;
    }

    private List<Integer> getIntegerIdsFromCSV(String ids) {
        List<Integer> result = new ArrayList<>();
        for (String token : ids.split(",")) {
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
}
