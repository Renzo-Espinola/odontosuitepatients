package com.odontosuitepatients.application.mapper;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import com.odontosuitepatients.domain.model.Encounter;

public class ClinicalNoteMapper {

    private ClinicalNoteMapper() {}

    public static ClinicalNoteResponse toResponse(Encounter e) {
        return new ClinicalNoteResponse(
                e.getId(),
                e.getPatient().getId(),
                e.getDateTime(),
                e.getTooth(),
                e.getDiagnosis(),
                e.getTreatment(),
                e.getObservations()
        );
    }
}
