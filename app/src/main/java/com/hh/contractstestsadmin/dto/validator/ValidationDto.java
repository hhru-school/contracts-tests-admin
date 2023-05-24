package com.hh.contractstestsadmin.dto.validator;

import java.util.List;

public class ValidationDto {

  private String validatorReport;

  private List<WrongExpectationDto> wrongExpectations;

  public String getValidatorReport() {
    return validatorReport;
  }

  public void setValidatorReport(String validatorReport) {
    this.validatorReport = validatorReport;
  }

  public List<WrongExpectationDto> getWrongExpectations() {
    return wrongExpectations;
  }

  public void setWrongExpectations(List<WrongExpectationDto> wrongExpectations) {
    this.wrongExpectations = wrongExpectations;
  }
}
