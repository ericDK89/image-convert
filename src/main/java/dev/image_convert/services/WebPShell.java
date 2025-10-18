package dev.image_convert.services;

import dev.image_convert.exceptions.ExtensionException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class WebPShell {

  @Autowired
  ConvertImage convertImage;

  @ShellMethod(key = "webp", value = "Convert to WebP")
  public String convertToWebP(@ShellOption(help = "image path", defaultValue = "./") String imgPath)
      throws IOException {
    var testPath = "/home/eric/Downloads/p.png";

    try {
      File imgFile = new File(testPath);

      if (!imgFile.exists()) {
        throw new IOException();
      }

      String fileName = imgFile.getName();
      int lastIndex = fileName.lastIndexOf(".");
      String fileExtension = lastIndex > 0 ? fileName.substring(lastIndex + 1) : "";

      if (!fileExtension.equals("png")) {
        throw new ExtensionException(fileExtension);
      }

      BufferedImage img = ImageIO.read(new File("/home/eric/Downloads/p.png"));

      return convertImage.execute(img, "webp");

    } catch (IOException e) {
      throw new IOException(e);
    }
  }

}
