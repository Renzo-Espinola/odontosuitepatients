package com.odontosuitepatients.application.dto.clinicalhistory;


import java.time.OffsetDateTime;

import com.odontosuitepatients.domain.enums.ClinicalHistoryType;
import com.odontosuitepatients.domain.enums.ToothSurface;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClinicalHistoryEntryResponse {
    Long id;
    Long patientId;
    ClinicalHistoryType type;
    OffsetDateTime occurredAt;
    String toothCode;
    ToothSurface surface;
    String title;
    String note;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}

