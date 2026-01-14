package com.odontosuitepatients.application.dto.patient;

import java.time.LocalDate;

public record PatientResponse(Long id,
        String firstName,
        String lastName,
        String documentNumber,
        LocalDate birthDate,
        String phone,
        String email,
        String address,
        String obraSocial,
        String obraSocialNumber,
        boolean active) {
}
