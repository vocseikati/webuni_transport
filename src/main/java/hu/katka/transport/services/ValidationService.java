package hu.katka.transport.services;

public class ValidationService {

  public static void requireNull(Object o, String message) {
    if (o != null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void requireNonNull(Object o, String message) {
    if (o instanceof String) {
      if (((String) o).isEmpty()) {
        throw new IllegalArgumentException(message);
      }
    }
    if (o == null) {
      throw new IllegalArgumentException(message);
    }
  }


}
