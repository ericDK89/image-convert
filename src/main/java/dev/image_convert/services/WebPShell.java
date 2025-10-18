package dev.image_convert.services;

import dev.image_convert.exceptions.InvalidShellParamsException;
import dev.image_convert.exceptions.MissingImageFileExcpetion;
import dev.image_convert.utils.ValidateImage;
import dev.image_convert.utils.ValidateParams;
import dev.image_convert.utils.ValidateQuality;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
  ValidateParams validateParams;

  @Autowired
  ConvertImage convertImage;

  @ShellMethod(key = "webp", value = "Convert to WebP")
  public List<String> convertToWebP(
      @ShellOption(help = "Directory path", defaultValue = "") String d,
      @ShellOption(help = "Single file path", defaultValue = "") String s,
      @ShellOption(help = "Quality", defaultValue = "0.75") String q
  )
      throws IOException, InvalidShellParamsException, MissingImageFileExcpetion {
    var testPath = "/home/eric/Downloads/p.png";
    var testPath2 = "/home/eric/Downloads/";

    validateParams.execute(s, d);

    float quality = validateQuality.execute(q);

    if (!s.isEmpty()) {
      File imgFile = new File(testPath);
      validateImage.singleFile(imgFile, testPath);
      return List.of(convertImage.convertSingleFile(imgFile, quality));
    }

    if (!d.isEmpty()) {
      Path inputDir = Paths.get(testPath2);
      validateImage.directory(inputDir);
      return convertImage.convertDirectory(inputDir, quality);
    }

    return null;
  }

}
