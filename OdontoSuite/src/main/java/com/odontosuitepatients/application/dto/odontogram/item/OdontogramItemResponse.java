package com.odontosuitepatients.application.dto.odontogram.item;

import com.odontosuitepatients.domain.enums.OdontogramStatus;
import com.odontosuitepatients.domain.enums.ToothSurface;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OdontogramItemResponse {
    Long id;
    String toothCode;
    ToothSurface surface;
    OdontogramStatus status;
    String note;
}

