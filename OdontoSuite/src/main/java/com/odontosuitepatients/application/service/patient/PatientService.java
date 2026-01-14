package com.odontosuitepatients.application.service.patient;

import com.odontosuitepatients.application.dto.patient.PatientRequest;
import com.odontosuitepatients.application.dto.patient.PatientResponse;
import java.util.List;

public interface PatientService {

    PatientResponse create(PatientRequest request);

    PatientResponse update(Long id, PatientRequest request);

    void delete(Long id);

    PatientResponse getById(Long id);

    List<PatientResponse> list(String lastNameFilter);

    List<PatientResponse> search(String q, boolean activeOnly);

}
