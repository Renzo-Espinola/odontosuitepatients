package com.odontosuitepatients.application.dto.clinicalhistory;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClinicalHistoryAttachmentResponse {
    Long id;
    String fileName;
    String originalName;
    String contentType;
    Long sizeBytes;
    OffsetDateTime createdAt;
    String downloadUrl;
}

