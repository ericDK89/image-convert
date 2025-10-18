package dev.image_convert.utils;

import dev.image_convert.exceptions.MissingImageFileExcpetion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class ValidateImage {

  public void singleFile(File imgFile, String path) throws MissingImageFileExcpetion {
    if (!imgFile.exists()) {
      throw new MissingImageFileExcpetion("File not found: " + path);
    }

    if (!imgFile.isFile()) {
      throw new MissingImageFileExcpetion("Path is not a file: " + path);
    }
  }

  public void directory(Path inputDir) throws IOException {
    if (!Files.exists(inputDir) || !Files.isDirectory(inputDir)) {
      throw new IOException("Directory doesn't exists or isn't a directory: " + inputDir);
    }
  }
}
