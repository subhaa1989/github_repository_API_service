package org.redcare.coding.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {

  public static LocalDate toDate(final String value) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    return LocalDate.parse(value, formatter);
  }
}
