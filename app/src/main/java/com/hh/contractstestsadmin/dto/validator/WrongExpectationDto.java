package com.hh.contractstestsadmin.dto.validator;

import java.util.List;

public class WrongExpectationDto {

  private String producerName;

  private String producerVersion;

  private Boolean isProducerRelease;

  private String consumerName;

  private String consumerVersion;

  private Boolean isConsumerRelease;

  private RequestDto request;

  private ResponseDto response;

  private List<MessageDto> messages;

}
