package com.odontosuitepatients.application.dto.clinicalnote;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

import lombok.Value;

@Value
public class ClinicalNoteRequest {
    @NotNull
    OffsetDateTime dateTime;

    String tooth;
    String diagnosis;
    String treatment;
    String observations;
}
