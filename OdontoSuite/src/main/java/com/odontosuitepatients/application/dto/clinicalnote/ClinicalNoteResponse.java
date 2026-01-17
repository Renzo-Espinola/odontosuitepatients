package com.odontosuitepatients.application.dto.clinicalnote;

import java.time.OffsetDateTime;

import lombok.Value;

@Value
public class ClinicalNoteResponse {
    Long id;
    Long patientId;
    OffsetDateTime dateTime;
    String tooth;
    String diagnosis;
    String treatment;
    String observations;
}

