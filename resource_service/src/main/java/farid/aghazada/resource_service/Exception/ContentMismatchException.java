package farid.aghazada.resource_service.Exception;

import org.springframework.http.HttpStatus;

public class ContentMismatchException extends AppException {

    public ContentMismatchException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
