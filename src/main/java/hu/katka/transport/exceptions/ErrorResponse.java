package hu.katka.transport.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponse {

  private int statusCode;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
  private String message;

  public static class ErrorResponseBuilder {

    private int statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public ErrorResponseBuilder() {
    }

    public ErrorResponseBuilder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public ErrorResponseBuilder timeStamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public ErrorResponseBuilder message(String message) {
      this.message = message;
      return this;
    }


    public ErrorResponse build() {
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.statusCode = this.statusCode;
      errorResponse.timestamp = this.timestamp;
      errorResponse.message = this.message;
      return errorResponse;
    }
  }

  public int getStatusCode() {
    return statusCode;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

}
