package dev.image_convert.utils;

import dev.image_convert.exceptions.QualityRangeException;
import org.springframework.stereotype.Service;

@Service
public class ValidateQuality {

  public float execute(String strQuality) throws NullPointerException, NumberFormatException {
    try {
      float quality = Float.parseFloat(strQuality);

      if (quality > 1 || quality < 0) {
        throw new QualityRangeException(String.valueOf(quality));
      }

      return quality;

    } catch (NumberFormatException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }
}
