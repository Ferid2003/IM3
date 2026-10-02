package farid.aghazada.resource_service.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import farid.aghazada.resource_service.DTO.SongMetadataRequestDTO;

@Component
public class SongServiceClient {

    private final RestClient restClient;

    public SongServiceClient(@LoadBalanced RestClient.Builder builder, @Value("${song.service.name}") String songServiceName) {
        this.restClient = builder.baseUrl("http://" + songServiceName).build();
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
