package com.odontosuitepatients.application.dto.attachment;

import java.util.UUID;

public record AttachmentCreateRequest(
        String fileName,
        String contentType,
        Long fileSize,
        String storageKey,
        String description,
        UUID encounterId
) {}
