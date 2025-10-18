package dev.image_convert.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class IsImageFile {

  public boolean execute(Path path) {
    if (!Files.isRegularFile(path)) {
      return false;
    }

    String fileName = path.getFileName().toString().toLowerCase();
    return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
        fileName.endsWith(".png") || fileName.endsWith(".bmp") ||
        fileName.endsWith(".gif") || fileName.endsWith(".tiff");
  }
}
