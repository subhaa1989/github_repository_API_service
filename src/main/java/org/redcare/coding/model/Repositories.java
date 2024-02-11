package org.redcare.coding.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class Repositories implements Serializable {
  public int total_count;
  public boolean incomplete_results;
  public ArrayList<Item> items;
}
