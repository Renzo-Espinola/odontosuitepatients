package com.odontosuitepatients.application.dto.clinicalhistory;

import com.odontosuitepatients.domain.enums.ClinicalHistoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClinicalHistoryEntryRequest {
    @NotNull
    ClinicalHistoryType type;
    OffsetDateTime occurredAt;
    String toothCode;
    String surface;
    String title;

    @NotBlank String note;
}

