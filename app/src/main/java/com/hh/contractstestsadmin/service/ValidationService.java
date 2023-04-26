package com.hh.contractstestsadmin.service;

import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dto.ValidationPreviewDto;
import com.hh.contractstestsadmin.dto.ValidationStatus;
import com.hh.contractstestsadmin.exception.ContractsDaoException;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.exception.ValidationHistoryNotFoundException;
import com.hh.contractstestsadmin.model.Validation;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.transaction.annotation.Transactional;

public class ValidationService {

    private final ContractsDao contractsDao;

    private final ValidationDao validationDao;

    private ValidationService selfReference;

    public ValidationService(ContractsDao contractsDao, ValidationDao validationDao){
        this.contractsDao = contractsDao;
        this.validationDao = validationDao;
    }

    public void setSelfReference(ValidationService selfReference){
        this.selfReference = selfReference;
    }

    @Transactional
    public List<Validation> getAllValidationsByStandName(String standName){
        return validationDao.getAllValidationsByStandName(standName);
    }

    @Transactional
    public void saveValidation(Validation validation){
        validationDao.save(validation);
    }

    private void checkStandExistence(String standName) throws ContractsDaoException {
        if(contractsDao.getStandNames().stream().noneMatch(s -> s.equals(standName))){
            throw new ValidationHistoryNotFoundException("Stand '" + standName + "' does not exist");
        }
    }

    public List<ValidationPreviewDto> getHistoryPreview(
            String standName,
            Long sizeLimit)
            throws ValidationHistoryNotFoundException, ContractsDaoException {
        checkStandExistence(standName);
        Stream<ValidationPreviewDto> validationPreviewStream = selfReference.getAllValidationsByStandName(standName).stream()
                    .sorted(Comparator.comparing(Validation::getCreatedDate).reversed())
                    .map(ValidationMapper::map);
        if(sizeLimit == null){
            return validationPreviewStream.toList();
        }
        return validationPreviewStream
                .limit(sizeLimit)
                .toList();
    }

    public void runValidation(String standName) throws StandNotFoundException, ContractsDaoException {
        checkStandExistence(standName);
        Validation validation = new Validation();
        validation.setCreatedDate(LocalDateTime.now());
        validation.setStandName(standName);
        validation.setStatus(ValidationStatus.IN_PROGRESS);
        selfReference.saveValidation(validation);
    }
}
