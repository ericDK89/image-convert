package dev.image_convert.exceptions;

public class QualityRangeException extends RuntimeException {

  public QualityRangeException(String message) {
    super("Quality must be between 0 and 1 inclusive. " + message);
  }
}
