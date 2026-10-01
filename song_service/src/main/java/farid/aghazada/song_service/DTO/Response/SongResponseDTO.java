package farid.aghazada.song_service.DTO.Response;

import farid.aghazada.song_service.Entity.SongMetadataEntity;

public record SongResponseDTO (

        Integer id,
        String name,
        String artist,
        String album,
        String duration,
        String year
        ) {

    public static SongResponseDTO fromSongMetadataEntity(SongMetadataEntity songMetadataEntity) {
        return new SongResponseDTO(
                songMetadataEntity.getId(),
                songMetadataEntity.getName(),
                songMetadataEntity.getArtist(),
                songMetadataEntity.getAlbum(),
                songMetadataEntity.getDuration(),
                songMetadataEntity.getYear());
    }
        }

