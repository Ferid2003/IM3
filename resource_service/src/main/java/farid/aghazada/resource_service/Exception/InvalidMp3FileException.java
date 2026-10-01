package farid.aghazada.resource_service.Exception;

import org.springframework.http.HttpStatus;

public class InvalidMp3FileException extends AppException {

    public InvalidMp3FileException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
