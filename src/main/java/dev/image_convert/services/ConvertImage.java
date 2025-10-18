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

  public String execute(BufferedImage img, String extension) throws IOException {
    Iterator<ImageWriter> writerIterator = ImageIO.getImageWritersByFormatName("webp");

    if (!writerIterator.hasNext()) {
      throw new IOException("Writer not available.");
    }

    Path currDir = Paths.get("").toAbsolutePath();
    String imgDir = "src/static/images/";
    Path saveImgDir = currDir.resolve(imgDir);

    if (!Files.exists(saveImgDir)) {
      Files.createDirectories(saveImgDir);
    }

    String imgFile = "/img_" + Instant.now().toEpochMilli() + ".webp";
    File outputFile = new File(saveImgDir.toFile(), imgFile);

    try (FileImageOutputStream output = new FileImageOutputStream(
        new File(outputFile.toURI()))) {

      ImageWriter writer = writerIterator.next();

      ImageWriteParam params = writer.getDefaultWriteParam();
      if (params.canWriteCompressed()) {
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionType("Lossy");
        params.setCompressionQuality(0.5f);
      }

      writer.setOutput(output);
      writer.write(null, new IIOImage(img, null, null), params);

    } catch (IOException e) {
      throw new IOException(e);
    }

    return "Success";
  }
}