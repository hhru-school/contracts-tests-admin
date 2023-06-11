package com.hh.contractstestsadmin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry implements Serializable {
  private String key;

  private String value;

  public Entry(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public Entry() {
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
