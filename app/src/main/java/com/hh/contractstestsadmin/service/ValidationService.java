package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ValidationService {

    public List<ValidationPreviewDto> getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws StandNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("validation-preview-list-exemple.json").getFile());
            List<ValidationPreviewDto> result = objectMapper.readValue(file, new TypeReference<>(){});
            if (sizeLimit == null) {
                sizeLimit = 5L;
            }
            return result.stream().limit(sizeLimit).toList();
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void runValidation(String standName) throws StandNotFoundException {

    }
}
