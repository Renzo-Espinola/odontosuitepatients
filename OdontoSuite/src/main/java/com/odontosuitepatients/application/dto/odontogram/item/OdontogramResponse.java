package com.odontosuitepatients.application.dto.odontogram.item;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OdontogramResponse {
    Long odontogramId;
    Long patientId;
    List<OdontogramItemResponse>items;
}
