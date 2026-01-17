package com.odontosuitepatients.application.service.clinicalnote;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import java.util.List;

public interface ClinicalNoteService {
    ClinicalNoteResponse create(Long patientId, ClinicalNoteRequest request);
    List<ClinicalNoteResponse> list(Long patientId);
    ClinicalNoteResponse get(Long patientId, Long id);
    void delete(Long patientId, Long id);
}
