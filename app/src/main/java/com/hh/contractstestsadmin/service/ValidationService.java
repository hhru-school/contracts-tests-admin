package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ValidationService {

    public List<ValidationPreviewDto> getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws StandNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test-data/validation-preview-list-exemple.json").getFile());
        List<ValidationPreviewDto> result = objectMapper.readValue(file, new TypeReference<>(){});
        if (sizeLimit == null) {
            sizeLimit = 5L;
        }
        return result.stream().limit(sizeLimit).toList();
    }

    public void runValidation(String standName) throws StandNotFoundException {

    }
}
