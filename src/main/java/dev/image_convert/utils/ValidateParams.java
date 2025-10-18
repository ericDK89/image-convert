package dev.image_convert.utils;

import dev.image_convert.exceptions.InvalidShellParamsException;
import org.springframework.stereotype.Service;

@Service
public class ValidateParams {

  public void execute(String s, String d) {
    if (s == null || d == null) {
      throw new NullPointerException("Parameter cannot be null");
    }

    if (!s.isEmpty() && !d.isEmpty()) {
      throw new InvalidShellParamsException(
          "Cannot use both --s and --d parameters simultaneously");
    }

    if (s.isEmpty() && d.isEmpty()) {
      throw new InvalidShellParamsException(
          "Must provide either a directory path (--d) or a single file path (--s)");
    }
  }
}
