package farid.aghazada.resource_service.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import farid.aghazada.resource_service.Service.ResourceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Map<String, Integer>> uploadMp3(@RequestBody byte[] audioData, @RequestHeader("Content-Type") String contentType) {
        return ResponseEntity.ok(resourceService.uploadMp3(audioData, contentType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getMp3(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(resourceService.getMp3(id));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Integer>>> deleteMp3(@RequestParam String id) {
        return ResponseEntity.ok(resourceService.deleteResources(id));
    }

}
