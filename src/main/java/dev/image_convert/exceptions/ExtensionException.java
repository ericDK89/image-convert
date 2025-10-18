package dev.image_convert.exceptions;

public class ExtensionException extends RuntimeException {

  public ExtensionException(String message) {
    super("Type file invalid: " + message);
  }
}
