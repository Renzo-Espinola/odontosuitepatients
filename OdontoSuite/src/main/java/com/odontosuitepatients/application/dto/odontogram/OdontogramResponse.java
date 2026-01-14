package com.odontosuitepatients.application.dto.odontogram;

import com.odontosuitepatients.application.dto.odontogram.item.OdontogramItemResponse;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OdontogramResponse {
    Long odontogramId;
    Long patientId;
    List<OdontogramItemResponse> items;
}

