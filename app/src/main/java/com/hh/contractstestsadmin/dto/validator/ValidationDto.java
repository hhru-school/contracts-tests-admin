package com.hh.contractstestsadmin.dto.validator;

import java.util.List;

public class ValidationDto {

  private String validatorReport;

  private List<WrongExpectationDto> wrongExpectationsDto;

  public String getValidatorReport() {
    return validatorReport;
  }

  public void setValidatorReport(String validatorReport) {
    this.validatorReport = validatorReport;
  }

  public List<WrongExpectationDto> getWrongExpectationsDto() {
    return wrongExpectationsDto;
  }

  public void setWrongExpectationsDto(List<WrongExpectationDto> wrongExpectationsDto) {
    this.wrongExpectationsDto = wrongExpectationsDto;
  }
}
