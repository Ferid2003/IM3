package farid.aghazada.resource_service.Exception;

import org.springframework.http.HttpStatus;

public class CSVLengthRestrictionException extends AppException {

    public CSVLengthRestrictionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
