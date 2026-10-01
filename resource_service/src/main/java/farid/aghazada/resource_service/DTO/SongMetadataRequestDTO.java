package farid.aghazada.resource_service.DTO;

public record SongMetadataRequestDTO(
        Integer id,
        String name,
        String artist,
        String album,
        String duration,
        String year
) {
}
