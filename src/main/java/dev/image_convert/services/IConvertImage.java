package dev.image_convert.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IConvertImage {

  String convertSingleFile(File imgFile, float quality) throws IOException;

  List<String> convertDirectory(Path inputDir, float quality) throws IOException;
}
