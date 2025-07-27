package hae.woori.onceaday.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<ExceptionResponse> handleException(RuntimeException exception) {
		exception.printStackTrace();
		ExceptionResponse responseBody = new ExceptionResponse(
			exception.getMessage(), "INTERNAL_SERVER_ERROR"
		);

		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {ClientSideException.class, HandlerMethodValidationException.class})
	public ResponseEntity<ExceptionResponse> handleException(ClientSideException exception) {
		ExceptionResponse responseBody = new ExceptionResponse(
			exception.getMessage(), "CLIENT_SIDE_ERROR"
		);

		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}
}
