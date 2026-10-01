package farid.aghazada.resource_service.Exception;

import org.springframework.http.HttpStatus;

public class CSVFormatException extends AppException {

    public CSVFormatException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
