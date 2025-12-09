package com.odontosuitepatients.service.patient;

import com.odontosuitepatients.domain.dto.PatientRequest;
import com.odontosuitepatients.domain.dto.PatientResponse;
import java.util.List;

public interface PatientService {

    PatientResponse create(PatientRequest request);

    PatientResponse update(Long id, PatientRequest request);

    void delete(Long id);

    PatientResponse getById(Long id);

    List<PatientResponse> list(String lastNameFilter);

}
