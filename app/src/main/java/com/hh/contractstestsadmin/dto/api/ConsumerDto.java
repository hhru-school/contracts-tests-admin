package com.hh.contractstestsadmin.dto.api;

public class ConsumerDto extends ServiceDto {

  private String expectationLink;

  public ConsumerDto() {
  }

  public String getExpectationLink() {
    return expectationLink;
  }

  public void setExpectationLink(String expectationLink) {
    this.expectationLink = expectationLink;
  }

}

