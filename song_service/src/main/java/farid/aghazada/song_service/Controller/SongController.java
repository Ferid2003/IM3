package farid.aghazada.song_service.Controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import farid.aghazada.song_service.DTO.Request.SongCreationDTO;
import farid.aghazada.song_service.DTO.Response.SongResponseDTO;
import farid.aghazada.song_service.Service.SongService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Map<String, Integer>> createSongMetadata(@RequestBody @Valid SongCreationDTO songMetadata) {
        return ResponseEntity.ok(songService.createSongMetadata(songMetadata));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongMetadata(@PathVariable Integer id) {
        return ResponseEntity.ok(songService.getSongMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Integer>>> deleteSongMetadatas(@RequestParam String id) {
        return ResponseEntity.ok(songService.deleteSongMetadatas(id));
    }
}
