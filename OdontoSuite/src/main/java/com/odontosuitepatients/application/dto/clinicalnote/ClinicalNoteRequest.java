package com.odontosuitepatients.application.dto.clinicalnote;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ClinicalNoteRequest {
    @NotNull LocalDateTime dateTime;
    String tooth;
    String diagnosis;
    String treatment;
    String observations;
}
