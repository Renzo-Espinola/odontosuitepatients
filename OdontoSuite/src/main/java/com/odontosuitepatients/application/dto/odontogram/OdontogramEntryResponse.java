package com.odontosuitepatients.application.dto.odontogram;

import com.odontosuitepatients.domain.enums.ToothCondition;
import com.odontosuitepatients.domain.enums.ToothSurface;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class OdontogramEntryResponse {
    Long id;
    Long patientId;
    String toothCode;
    ToothSurface surface;
    ToothCondition condition;
    String note;
    Instant updatedAt;
}

