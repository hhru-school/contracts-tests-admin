package com.hh.contractstestsadmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ValidationService {

    private final ContractsDao contractsDao;

    private final ValidationDao validationDao;

    public ValidationService(ContractsDao contractsDao, ValidationDao validationDao){
        this.contractsDao = contractsDao;
        this.validationDao = validationDao;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private boolean isStandExists(String standName) throws ContractsDaoException {
        return contractsDao.getStandNames()
                .orElseGet(Collections::emptyList)
                .stream()
                .anyMatch(s -> s.equals(standName));
    }

    public List<ValidationPreviewDto> getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws ValidationHistoryNotFoundException, ContractsDaoException {
        if(!isStandExists(standName)){
            throw new ValidationHistoryNotFoundException("Stand '" + standName + "' does not exist");
        }
        return validationDao.getAllValidationsByStandName(standName).stream()
                .sorted(Comparator.comparing(Validation::getCreatedDate).reversed())
                .limit(sizeLimit)
                .map(ValidationMapper::map)
                .toList();
    }

    public void runValidation(String standName) throws StandNotFoundException {

    }
}
