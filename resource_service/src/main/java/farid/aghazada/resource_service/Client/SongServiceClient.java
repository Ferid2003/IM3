package farid.aghazada.resource_service.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import farid.aghazada.resource_service.DTO.SongMetadataRequestDTO;

@Component
public class SongServiceClient {

    private final RestClient restClient;

    public SongServiceClient(@Value("${song.service.url}") String songServiceUrl) {
        this.restClient = RestClient.create(songServiceUrl);
    }

    public void createSongMetadata(SongMetadataRequestDTO payload) {
        restClient.post()
                .uri("/songs")
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteSongMetadata(String csvIds) {
        restClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/songs").queryParam("id", csvIds).build())
                .retrieve()
                .toBodilessEntity();
    }
}
