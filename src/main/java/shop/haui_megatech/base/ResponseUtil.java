package shop.haui_megatech.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	public static ResponseEntity<RestData<?>> success(Object data) {
		return success(HttpStatus.OK, data);
	}

	public static ResponseEntity<RestData<?>> success(HttpStatus status, Object data) {
		RestData<?> response = new RestData<>(data);
		return new ResponseEntity<>(response, status);
	}

	public static ResponseEntity<RestData<?>> error(HttpStatus status, String clientMessage, String devMessage) {
		RestData<?> response = RestData.error(clientMessage, devMessage);
		return new ResponseEntity<>(response, status);
	}

	public static ResponseEntity<RestData<?>> error(HttpStatus status, String clientMessage) {
		RestData<?> response = RestData.error(clientMessage);
		return new ResponseEntity<>(response, status);
	}
}
