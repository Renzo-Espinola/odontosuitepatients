package com.odontosuitepatients.application.service.odontogram;

import com.odontosuitepatients.application.dto.odontogram.item.OdontogramItemResponse;
import com.odontosuitepatients.application.dto.odontogram.item.OdontogramItemUpsertRequest;
import com.odontosuitepatients.application.dto.odontogram.item.OdontogramResponse;
import com.odontosuitepatients.domain.enums.OdontogramStatus;
import com.odontosuitepatients.domain.enums.ToothSurface;
import com.odontosuitepatients.domain.model.*;
import com.odontosuitepatients.domain.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OdontogramServiceImpl implements OdontogramService {

    private final PatientRepository patientRepository;
    private final OdontogramRepository odontogramRepository;
    private final OdontogramItemRepository itemRepository;
    private final EncounterRepository encounterRepository;

    @Override
    @Transactional
    public OdontogramResponse getOrCreate(final Long patientId) {
        final Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Patient not found: " + patientId));


        final Odontogram odontogram = odontogramRepository.findByPatientId(patientId)
                .orElseGet(() -> odontogramRepository.save(Odontogram.builder().patient(patient).build()));

        return toResponse(odontogram);
    }

    @Override
    @Transactional
    public OdontogramResponse upsertItem(final Long patientId, final OdontogramItemUpsertRequest request) {
        if (request.getToothCode() == null || request.getToothCode().isBlank()) {
            throw new IllegalArgumentException("toothCode is required");
        }
        if (request.getStatus() == null) {
            throw new IllegalArgumentException("status is required");
        }

        final Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalStateException("Patient not found: " + patientId));

        final Odontogram odontogram = odontogramRepository.findByPatientId(patientId)
                .orElseGet(() -> odontogramRepository.save(Odontogram.builder().patient(patient).build()));

        final ToothSurface surface = request.getSurface() != null ? request.getSurface() : ToothSurface.GENERAL;

        validateSurface(request.getStatus(), surface);

        final OdontogramItem item = itemRepository
                .findByOdontogramIdAndToothCodeAndSurface(odontogram.getId(), request.getToothCode(), surface)
                .map(existing -> {
                    existing.setStatus(request.getStatus()); existing.setNote(request.getNote());
                    return existing;
                })
                .orElseGet(() -> OdontogramItem.builder()
                        .odontogram(odontogram)
                        .toothCode(request.getToothCode())
                        .surface(surface)
                        .status(request.getStatus())
                        .note(request.getNote())
                        .build());

        itemRepository.save(item);

        if (request.isCreateClinicalNote()) {
            encounterRepository.save(buildAutoClinicalNote(patient, request));
        }

        return toResponse(odontogram);
    }

    @Override
    @Transactional
    public void deleteItem(final Long patientId, final Long itemId) {
        final Odontogram odontogram = odontogramRepository.findByPatientId(patientId)
                .orElseThrow(() -> new IllegalStateException("Odontogram not found for patient: " + patientId));

        final OdontogramItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("Odontogram item not found: " + itemId));

        if (item.getOdontogram() == null || !item.getOdontogram().getId().equals(odontogram.getId())) {
            throw new IllegalStateException("Item does not belong to patient's odontogram");
        }

        itemRepository.delete(item);
    }

    // ----------------- Auto note builder -----------------

    private Encounter buildAutoClinicalNote(final Patient patient, final OdontogramItemUpsertRequest req) {
        final ToothSurface surface = req.getSurface() != null ? req.getSurface() : ToothSurface.GENERAL;

        final String tooth = formatTooth(req.getToothCode(), surface);

        // Ejemplo pedido: “36 O: CARIES”
        final String defaultDiagnosis = tooth + ": " + req.getStatus().name();

        final String diagnosis = isBlank(req.getClinicalDiagnosis()) ? defaultDiagnosis : req.getClinicalDiagnosis();
        final String treatment = isBlank(req.getClinicalTreatment()) ? null : req.getClinicalTreatment();

        // Si no mandan observaciones, uso note si existe
        final String observations = !isBlank(req.getClinicalObservations())
                ? req.getClinicalObservations()
                : (isBlank(req.getNote()) ? null : req.getNote());

        return Encounter.builder()
                .patient(patient)
                .dateTime(OffsetDateTime.now())
                .tooth(tooth)
                .diagnosis(diagnosis)
                .treatment(treatment)
                .observations(observations)
                .build();
    }

    private String formatTooth(final String toothCode, final ToothSurface surface) {
        return surface == null ? toothCode : toothCode + " " + surface.name();
    }

    private boolean isBlank(final String s) {
        return s == null || s.isBlank();
    }

    // ----------------- mapping response -----------------

    private OdontogramResponse toResponse(final Odontogram odontogram) {
        final List<OdontogramItemResponse> items = itemRepository.findAllByOdontogramId(odontogram.getId())
                .stream()
                .map(this::toItemResponse)
                .toList();

        return OdontogramResponse.builder()
                .odontogramId(odontogram.getId())
                .patientId(odontogram.getPatient().getId())
                .items(items)
                .build();
    }

    private OdontogramItemResponse toItemResponse(final OdontogramItem item) {
        return OdontogramItemResponse.builder()
                .id(item.getId())
                .toothCode(item.getToothCode())
                .surface(item.getSurface())
                .status(item.getStatus())
                .note(item.getNote())
                .build();
    }

    private void validateSurface(OdontogramStatus status, ToothSurface surface) {
        boolean mustBeGeneral = status == OdontogramStatus.IMPLANT
                || status == OdontogramStatus.EXTRACTED
                || status == OdontogramStatus.MISSING;

        if (mustBeGeneral && surface != ToothSurface.GENERAL) {
            throw new IllegalArgumentException("Surface not allowed for status " + status);
        }
    }
}

