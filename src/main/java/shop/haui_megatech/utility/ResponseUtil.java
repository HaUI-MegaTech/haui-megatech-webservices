package shop.haui_megatech.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<?> ok(Object response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> created(Object response) {
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> noContent(Object response) {
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<?> notFound(Object response) {
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> badRequest(Object response) {
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> internalServerError(Object response) {
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
