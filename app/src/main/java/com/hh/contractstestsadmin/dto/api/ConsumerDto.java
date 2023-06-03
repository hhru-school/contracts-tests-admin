package com.hh.contractstestsadmin.dto.api;

import java.util.Objects;

public class ConsumerDto extends ServiceDto {

  private String expectationLink;

  public ConsumerDto(ServiceDto serviceDto) {
    Objects.requireNonNull(serviceDto);
    this.setId(serviceDto.getId());
    this.setName(serviceDto.getName());
    this.setRelease(serviceDto.isRelease());
    this.setVersion(serviceDto.getVersion());
  }

  public String getExpectationLink() {
    return expectationLink;
  }

  public void setExpectationLink(String expectationLink) {
    this.expectationLink = expectationLink;
  }

}
