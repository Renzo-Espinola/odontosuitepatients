package com.odontosuitepatients.application.dto.clinicalnote;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ClinicalNoteResponse {
    Long id;
    Long patientId;
    LocalDateTime dateTime;
    String tooth;
    String diagnosis;
    String treatment;
    String observations;
}

