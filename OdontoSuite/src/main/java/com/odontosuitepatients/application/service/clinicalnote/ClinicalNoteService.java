package com.odontosuitepatients.application.service.clinicalnote;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ClinicalNoteService {
    ClinicalNoteResponse create(Long patientId, ClinicalNoteRequest request);

    List<ClinicalNoteResponse> list(
            Long patientId,
            LocalDateTime from,
            LocalDateTime to
    );

    ClinicalNoteResponse update(Long noteId, ClinicalNoteRequest request);

    void delete(Long noteId);
}
