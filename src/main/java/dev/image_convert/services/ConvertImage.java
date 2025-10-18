package dev.image_convert.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ConvertImage {

  public String convertSingleFile(File imgFile, float quality) throws IOException {
    BufferedImage img = ImageIO.read(imgFile);
    return convertToWebp(img, quality);
  }

  public String convertToWebp(BufferedImage img, Float quality) throws IOException {
    Iterator<ImageWriter> writerIterator = ImageIO.getImageWritersByFormatName("webp");

    if (!writerIterator.hasNext()) {
      throw new IOException("Writer not available.");
    }

    Path absolutePath = Paths.get("").toAbsolutePath();
    Path saveImgDir = absolutePath.resolve("src/static/images/");

    if (!Files.exists(saveImgDir)) {
      Files.createDirectories(saveImgDir);
    }

    String convertedImgPath = "/img_" + Instant.now().toEpochMilli() + ".webp";
    File outputFile = new File(saveImgDir.toFile(), convertedImgPath);

    try (FileImageOutputStream output = new FileImageOutputStream(
        new File(outputFile.toURI()))) {

      ImageWriter writer = writerIterator.next();

      ImageWriteParam params = writer.getDefaultWriteParam();
      if (params.canWriteCompressed()) {
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionType("Lossy");
        params.setCompressionQuality(quality);
      }

      writer.setOutput(output);
      writer.write(null, new IIOImage(img, null, null), params);

      return convertedImgPath;

    } catch (IOException e) {
      throw new IOException(e);
    }
  }
}