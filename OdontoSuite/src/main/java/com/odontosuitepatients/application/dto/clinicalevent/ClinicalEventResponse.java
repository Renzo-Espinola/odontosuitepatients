package com.odontosuitepatients.application.dto.clinicalevent;

import com.odontosuitepatients.domain.enums.ToothSurface;

import java.time.OffsetDateTime;

public record ClinicalEventResponse(
        Long id,
        Long patientId,
        OffsetDateTime createdAt,
        String type,
        String toothCode,
        ToothSurface surface,
        String fromStatus,
        String toStatus,
        String note
) {}

