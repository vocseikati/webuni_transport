package hu.katka.transport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ResponseStatusException {

  public EntityNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
