package com.odontosuitepatients.application.service.clinicalhistory;

import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryAttachmentResponse;
import com.odontosuitepatients.domain.model.ClinicalHistoryAttachment;
import com.odontosuitepatients.domain.model.ClinicalHistoryEntry;
import com.odontosuitepatients.domain.repository.ClinicalHistoryAttachmentRepository;
import com.odontosuitepatients.domain.repository.ClinicalHistoryRepository;
import com.odontosuitepatients.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalHistoryAttachmentServiceImpl implements ClinicalHistoryAttachmentService {

    private final ClinicalHistoryRepository historyRepo;
    private final ClinicalHistoryAttachmentRepository attachmentRepo;
    private final StorageService storageService;

    @Override
    @Transactional
    public ClinicalHistoryAttachmentResponse upload(
            Long patientId,
            Long historyEntryId,
            MultipartFile file) {

        final ClinicalHistoryEntry entry = historyRepo.findById(historyEntryId)
                .orElseThrow(() -> new EntityNotFoundException("History entry not found"));

        if (!entry.getPatient().getId().equals(patientId)) {
            throw new EntityNotFoundException("Entry does not belong to patient");
        }

        final String storedPath = storageService.storeClinicalAttachment(patientId, historyEntryId, file);

        final ClinicalHistoryAttachment attachment =
                ClinicalHistoryAttachment.builder()
                        .historyEntry(entry)
                        .fileName(storedPath)
                        .originalName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .sizeBytes(file.getSize())
                        .build();

        return toResponse(attachmentRepo.save(attachment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalHistoryAttachmentResponse> list(Long historyEntryId) {
        return attachmentRepo.findByHistoryEntryId(historyEntryId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long patientId, Long attachmentId) {
        final ClinicalHistoryAttachment attachment =
                attachmentRepo.findById(attachmentId)
                        .orElseThrow(() -> new EntityNotFoundException("Attachment not found"));

        if (!attachment.getHistoryEntry().getPatient().getId().equals(patientId)) {
            throw new EntityNotFoundException("Attachment does not belong to patient");
        }

        storageService.delete(attachment.getFileName());
        attachmentRepo.delete(attachment);
    }

    private ClinicalHistoryAttachmentResponse toResponse(ClinicalHistoryAttachment a) {
        return ClinicalHistoryAttachmentResponse.builder()
                .id(a.getId())
                .fileName(a.getFileName())
                .originalName(a.getOriginalName())
                .contentType(a.getContentType())
                .sizeBytes(a.getSizeBytes())
                .createdAt(a.getCreatedAt())
                .downloadUrl("/api/files/" + a.getFileName())
                .build();
    }
}
