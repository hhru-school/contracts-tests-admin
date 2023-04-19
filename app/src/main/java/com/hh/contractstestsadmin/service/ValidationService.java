package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;

import java.util.ArrayList;

public class ValidationService {

    public ArrayList<ValidationPreviewDto>  getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws StandNotFoundException {
        return new ArrayList<>();
    }

    public void runValidation(String standName) throws StandNotFoundException {

    }
}
