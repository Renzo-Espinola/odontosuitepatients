package com.odontosuitepatients.application.dto.odontogram.item;

import com.odontosuitepatients.domain.enums.OdontogramStatus;
import com.odontosuitepatients.domain.enums.ToothSurface;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OdontogramItemUpsertRequest {
    @NotBlank String toothCode;          // ej: "36"
    ToothSurface surface;                // ej: O, M, D, V, L (puede ser null)
    @NotNull OdontogramStatus status;    // ej: CARIES, RESTORATION, etc
    String note;                         // texto libre

    // NUEVO: si true => crea nota clínica automática
    @Builder.Default
    boolean createClinicalNote = false;

    // opcionales para enriquecer la nota (si no vienen, se autogeneran)
    String clinicalDiagnosis;  // ej: "Caries"
    String clinicalTreatment;  // ej: "Obturación resina"
    String clinicalObservations;
}

