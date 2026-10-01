package farid.aghazada.song_service.Exception;

import org.springframework.http.HttpStatus;

public class SongMetadataNotFoundException extends AppException {

    public SongMetadataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
