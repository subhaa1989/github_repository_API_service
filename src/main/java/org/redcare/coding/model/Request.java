package org.redcare.coding.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Builder
@Data
public class Request {

  private String language;
  private int count;
  private LocalDate fromDate;
}
