package dev.image_convert.services;

import dev.image_convert.utils.IsImageFile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertImage implements IConvertImage {

  @Autowired
  IsImageFile isImageFile;

  @Override
  public String convertSingleFile(File imgFile, float quality) throws IOException {
    BufferedImage img = ImageIO.read(imgFile);
    return convertToWebp(img, quality);
  }

  @Override
  public List<String> convertDirectory(Path inputDir, float quality) throws IOException {
    try (Stream<Path> filesList = Files.list(inputDir)) {

      List<String> convertedFiles = new ArrayList<>();

      List<Path> imagesFiles = filesList
          .filter(isImageFile::execute).toList();

      try (ProgressBar pb = new ProgressBar("Converting Images", imagesFiles.size())) {
        for (Path imageFile : imagesFiles) {
          try {
            BufferedImage img = ImageIO.read(imageFile.toFile());
            convertedFiles.add(convertToWebp(img, quality));
            pb.step();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }

      return convertedFiles;

    } catch (RuntimeException e) {
      throw new RuntimeException();
    }
  }

  private String convertToWebp(BufferedImage img, float quality) throws IOException {
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
