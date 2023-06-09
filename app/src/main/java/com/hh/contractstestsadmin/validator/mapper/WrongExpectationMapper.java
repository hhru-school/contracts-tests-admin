package com.hh.contractstestsadmin.validator.mapper;

import com.hh.contractstestsadmin.validator.dto.MessageDto;
import com.hh.contractstestsadmin.validator.dto.WrongExpectationDto;
import ru.hh.contract.validator.dto.ContractValidationResultDto;

import java.util.List;

public class WrongExpectationMapper {

    public static WrongExpectationDto map(ContractValidationResultDto contractValidationResultDto) {
        WrongExpectationDto wrongExpectationDto = new WrongExpectationDto();
        wrongExpectationDto.setConsumerName(contractValidationResultDto.getConsumerName());
        wrongExpectationDto.setConsumerVersion(contractValidationResultDto.getConsumerVersion());
        wrongExpectationDto.setProducerName(contractValidationResultDto.getProducerName());
        wrongExpectationDto.setProducerVersion(contractValidationResultDto.getProducerVersion());
        List<MessageDto> messageDtos = contractValidationResultDto.getMessages().stream()
                .map(ValidationMessageMapper::map)
                .toList();
        wrongExpectationDto.setMessages(messageDtos);
        wrongExpectationDto.setRequest(ExpectedRequestMapper.map(contractValidationResultDto.getRequest()));
        wrongExpectationDto.setResponse(ExpectedResponseMapper.map(contractValidationResultDto.getResponse()));
        return wrongExpectationDto;
    }
}
