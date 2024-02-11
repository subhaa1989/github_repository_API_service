package org.redcare.coding.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class License implements Serializable {
  public String key;
  public String name;
  public String spdx_id;
  public String url;
  public String node_id;
}
