package dev.image_convert.utils;

import dev.image_convert.exceptions.MissingImageFileExcpetion;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class ValidateImage {

  public void execute(File imgFile, String path) throws MissingImageFileExcpetion {
    if (!imgFile.exists()) {
      throw new MissingImageFileExcpetion("File not found: " + path);
    }

    if (!imgFile.isFile()) {
      throw new MissingImageFileExcpetion("Path is not a file: " + path);
    }
  }
}
