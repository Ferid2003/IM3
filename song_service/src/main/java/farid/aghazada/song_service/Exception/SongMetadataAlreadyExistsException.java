package farid.aghazada.song_service.Exception;

import org.springframework.http.HttpStatus;

public class SongMetadataAlreadyExistsException extends AppException {

    public SongMetadataAlreadyExistsException(Integer id) {
        super("Metadata for resource ID=" + id + " already exists", HttpStatus.CONFLICT);
    }

}
