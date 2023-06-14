package com.hh.contractstestsadmin.validator.mapper;

import com.atlassian.oai.validator.report.ValidationReport;
import com.hh.contractstestsadmin.dto.validator.MessageDto;
import com.hh.contractstestsadmin.model.ErrorLevel;
import ru.hh.contract.validator.dto.ValidationMessage;

import java.util.Arrays;

public class ValidationMessageMapper {
    public static MessageDto map(ValidationMessage validationMessage) {
        MessageDto messageDto = new MessageDto();
        messageDto.setKey(validationMessage.getKey());
        messageDto.setMessage(validationMessage.getMessage());
        messageDto.setLevel(mapLevel(validationMessage.getLevel()));
        messageDto.setResponseStatus(validationMessage.getResponseStatus());
        return messageDto;
    }

    private static ErrorLevel mapLevel(ValidationReport.Level level) {
        return Arrays.stream(ErrorLevel.values())
                .filter(errorLevel -> errorLevel.name().equals(level.name().toUpperCase()))
                .findFirst().orElse(ErrorLevel.UNDEFINED);
    }
}
