package com.odontosuitepatients.application.service.clinicalhistory;

import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryRequest;
import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface ClinicalHistoryService {
    ClinicalHistoryEntryResponse create(Long patientId, ClinicalHistoryEntryRequest req);
    ClinicalHistoryEntryResponse update(Long patientId, Long entryId, ClinicalHistoryEntryRequest req);
    ClinicalHistoryEntryResponse get(Long patientId, Long entryId);
    List<ClinicalHistoryEntryResponse> list(Long patientId, OffsetDateTime from, OffsetDateTime to);
    void delete(Long patientId, Long entryId);
}

