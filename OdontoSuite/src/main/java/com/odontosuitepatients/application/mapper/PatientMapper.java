package com.odontosuitepatients.application.mapper;

import com.odontosuitepatients.application.dto.patient.PatientRequest;
import com.odontosuitepatients.application.dto.patient.PatientResponse;
import com.odontosuitepatients.domain.model.Patient;
import org.springframework.stereotype.Component;

@SuppressWarnings("PMD.AtLeastOneConstructor")
@Component
public class PatientMapper {

    public Patient toEntity(final PatientRequest r) {
        return Patient.builder()
                .firstName(r.getFirstName())
                .lastName(r.getLastName())
                .documentNumber(r.getDocumentNumber())
                .birthDate(r.getBirthDate())
                .phone(r.getPhone())
                .email(r.getEmail())
                .address(r.getAddress())
                .obraSocial(r.getObraSocial())
                .obraSocialNumber(r.getObraSocialNumber())
                .active(true)
                .build();
    }

    public void updateEntity(final Patient p, final PatientRequest r) {
        p.setFirstName(r.getFirstName());
        p.setLastName(r.getLastName());
        p.setDocumentNumber(r.getDocumentNumber());
        p.setBirthDate(r.getBirthDate());
        p.setPhone(r.getPhone());
        p.setEmail(r.getEmail());
        p.setAddress(r.getAddress());
        p.setObraSocial(r.getObraSocial());
        p.setObraSocialNumber(r.getObraSocialNumber());
    }

    public PatientResponse toResponse(final Patient p) {
        return new PatientResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getDocumentNumber(),
                p.getBirthDate(),
                p.getPhone(),
                p.getEmail(),
                p.getAddress(),
                p.getObraSocial(),
                p.getObraSocialNumber(),
                p.isActive());
    }
}
