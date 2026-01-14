package com.odontosuitepatients.application.dto.attachment;

import java.time.Instant;
import java.util.UUID;

public record AttachmentResponse(
        UUID id,
        UUID patientId,
        UUID encounterId,
        String fileName,
        String contentType,
        Long fileSize,
        String storageKey,
        String description,
        Instant createdAt
) {}