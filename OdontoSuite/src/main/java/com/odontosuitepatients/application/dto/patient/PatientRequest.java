package com.odontosuitepatients.application.dto.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PatientRequest{
    @NotBlank String firstName;
    @NotBlank String lastName;
    @NotBlank String documentNumber;
    LocalDate birthDate;
    String phone;
    @Email String email;
    String address;
    String obraSocial;
    String obraSocialNumber;
}
