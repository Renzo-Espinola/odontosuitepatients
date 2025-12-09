package com.odontosuitepatients.mapper;

import com.odontosuitepatients.domain.dto.PatientRequest;
import com.odontosuitepatients.domain.dto.PatientResponse;
import com.odontosuitepatients.domain.model.Patient;
import org.springframework.stereotype.Component;

@SuppressWarnings("PMD.AtLeastOneConstructor")
@Component
public class PatientMapper {

    public Patient toEntity(final PatientRequest r) {
        return Patient.builder()
                .firstName(r.firstName())
                .lastName(r.lastName())
                .documentNumber(r.documentNumber())
                .birthDate(r.birthDate())
                .phone(r.phone())
                .email(r.email())
                .address(r.address())
                .obraSocial(r.obraSocial())
                .obraSocialNumber(r.obraSocialNumber())
                .active(true)
                .build();
    }

    public void updateEntity(final Patient p, final PatientRequest r) {
        p.setFirstName(r.firstName());
        p.setLastName(r.lastName());
        p.setDocumentNumber(r.documentNumber());
        p.setBirthDate(r.birthDate());
        p.setPhone(r.phone());
        p.setEmail(r.email());
        p.setAddress(r.address());
        p.setObraSocial(r.obraSocial());
        p.setObraSocialNumber(r.obraSocialNumber());
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
