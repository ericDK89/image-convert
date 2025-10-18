package dev.image_convert.services;

import dev.image_convert.exceptions.InvalidShellParamsException;
import dev.image_convert.exceptions.MissingImageFileExcpetion;
import dev.image_convert.utils.ValidateImage;
import dev.image_convert.utils.ValidateQuality;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class WebPShell {

  @Autowired
  ValidateQuality validateQuality;

  @Autowired
  ValidateImage validateImage;

  @Autowired
  ConvertImage convertImage;

  @ShellMethod(key = "webp", value = "Convert to WebP")
  public String convertToWebP(
      @ShellOption(help = "Directory path", defaultValue = "") String d,
      @ShellOption(help = "Single file path", defaultValue = "") String s,
      @ShellOption(help = "Quality", defaultValue = "0.75") String q
  )
      throws IOException, InvalidShellParamsException, MissingImageFileExcpetion {
    var testPath = "/home/eric/Downloads/p.png";

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

    float quality = validateQuality.execute(q);

    if (!s.isEmpty()) {
      File imgFile = new File(testPath);

      validateImage.execute(imgFile, testPath);

      return convertImage.convertSingleFile(imgFile, quality);
    }

    if (!d.isEmpty()) {
      // convertHoleDirectory
    }

    return "s";
  }

}
