package hu.katka.transport.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e,
                                                             WebRequest request) {
    FieldError fieldErrors = e.getBindingResult().getFieldError();

    ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
        .statusCode(BAD_REQUEST.value())
        .timeStamp(LocalDateTime.now())
        .message(Objects.requireNonNull(fieldErrors).getDefaultMessage())
        .build();

    return ResponseEntity.status(BAD_REQUEST).body(response);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> notFoundHandler(EntityNotFoundException e,
                                                       WebRequest request) {
    ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
        .statusCode(e.getStatus().value())
        .timeStamp(LocalDateTime.now())
        .message(e.getMessage())
        .build();

    return ResponseEntity.status(e.getStatus()).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {

    ErrorResponse response = getBadRequestResponse(e.getMessage());

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  private ErrorResponse getBadRequestResponse(String message) {
    return new ErrorResponse.ErrorResponseBuilder()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .timeStamp(LocalDateTime.now())
        .message(HttpStatus.BAD_REQUEST + " \"" + message + "\"")
        .build();
  }
}
