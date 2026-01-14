package com.odontosuitepatients.application.dto.patient;

import java.time.LocalDate;

public record PatientCreateRequest(
        String documentType,
        String documentNumber,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String sex,
        String phone,
        String email,
        String address,
        String insuranceName,
        String insuranceNumber
) {}
