package com.hh.contractstestsadmin.dto.api;

public class ErrorMessageDto {

  private String massage;

  public ErrorMessageDto(String massage) {
    this.massage = massage;
  }

  public String getMassage() {
    return massage;
  }

  public void setMassage(String massage) {
    this.massage = massage;
  }

}
