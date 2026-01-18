package com.odontosuitepatients.application.dto.clinicalevent;

import com.odontosuitepatients.domain.enums.ToothSurface;

public record CreateClinicalEventRequest(
        Long patientId,
        String type,            // "NOTE" | "ODONTOGRAM_CHANGE"
        String toothCode,       // optional
        ToothSurface surface,         // optional
        String fromStatus,      // optional
        String toStatus,        // optional
        String note             // optional
) {}

