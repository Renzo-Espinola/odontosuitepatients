package com.odontosuitepatients.application.service.clinicalhistory;

import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryRequest;
import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryResponse;
import com.odontosuitepatients.domain.model.ClinicalHistoryEntry;
import com.odontosuitepatients.domain.repository.ClinicalHistoryRepository;
import com.odontosuitepatients.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClinicalHistoryServiceImpl implements ClinicalHistoryService {

    private final ClinicalHistoryRepository historyRepo;
    private final PatientRepository patientRepo;

    @Transactional
    @Override
    public ClinicalHistoryEntryResponse create(Long patientId, ClinicalHistoryEntryRequest req) {
        var patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + patientId));

        var entry = ClinicalHistoryEntry.builder()
                .patient(patient)
                .type(req.getType())
                .occurredAt(req.getOccurredAt())
                .toothCode(req.getToothCode())
                .surface(req.getSurface())
                .title(req.getTitle())
                .note(req.getNote())
                .build();

        return toResponse(historyRepo.save(entry));
    }

    @Transactional
    @Override
    public ClinicalHistoryEntryResponse update(Long patientId, Long entryId, ClinicalHistoryEntryRequest req) {
        var entry = loadOwnedEntry(patientId, entryId);

        entry.setType(req.getType());
        entry.setOccurredAt(req.getOccurredAt() != null ? req.getOccurredAt() : entry.getOccurredAt());
        entry.setToothCode(req.getToothCode());
        entry.setSurface(req.getSurface());
        entry.setTitle(req.getTitle());
        entry.setNote(req.getNote());

        return toResponse(historyRepo.save(entry));
    }

    @Transactional(readOnly = true)
    @Override
    public ClinicalHistoryEntryResponse get(Long patientId, Long entryId) {
        return toResponse(loadOwnedEntry(patientId, entryId));
    }

    @Transactional
    @Override
    public void delete(Long patientId, Long entryId) {
        var entry = loadOwnedEntry(patientId, entryId);
        historyRepo.delete(entry);
    }

    private ClinicalHistoryEntry loadOwnedEntry(Long patientId, Long entryId) {
        var entry = historyRepo.findById(entryId)
                .orElseThrow(() -> new EntityNotFoundException("Clinical history entry not found: " + entryId));

        if (!entry.getPatient().getId().equals(patientId)) {
            throw new EntityNotFoundException("Entry does not belong to patient. patientId=" + patientId);
        }
        return entry;
    }

    private ClinicalHistoryEntryResponse toResponse(ClinicalHistoryEntry e) {
        return ClinicalHistoryEntryResponse.builder()
                .id(e.getId())
                .patientId(e.getPatient().getId())
                .type(e.getType())
                .occurredAt(e.getOccurredAt())
                .toothCode(e.getToothCode())
                .surface(e.getSurface())
                .title(e.getTitle())
                .note(e.getNote())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalHistoryEntryResponse> list(Long patientId, OffsetDateTime from, OffsetDateTime to) {

        List<ClinicalHistoryEntry> entries;

        if (from == null && to == null) {
            entries = historyRepo.findByPatientIdOrderByOccurredAtDescIdDesc(patientId);
        } else if (from != null && to == null) {
            entries = historyRepo.findByPatientIdAndOccurredAtGreaterThanEqualOrderByOccurredAtDescIdDesc(patientId, from);
        } else if (from == null) { // to != null
            entries = historyRepo.findByPatientIdAndOccurredAtLessThanEqualOrderByOccurredAtDescIdDesc(patientId, to);
        } else {
            entries = historyRepo.findByPatientIdAndOccurredAtBetweenOrderByOccurredAtDescIdDesc(patientId, from, to);
        }

        return entries.stream().map(this::toResponse).toList();
    }

}

