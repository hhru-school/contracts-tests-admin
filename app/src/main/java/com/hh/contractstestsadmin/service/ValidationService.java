package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {

    public List<ValidationPreviewDto> getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws StandNotFoundException {
        return new ArrayList<>();
    }

    public void runValidation(String standName) throws StandNotFoundException {

    }
}
