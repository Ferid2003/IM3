package farid.aghazada.resource_service.Exception;

import org.springframework.http.HttpStatus;

public class InvalidIdException extends AppException {

    public InvalidIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
