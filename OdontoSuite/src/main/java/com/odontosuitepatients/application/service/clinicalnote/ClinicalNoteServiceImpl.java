package com.odontosuitepatients.application.service.clinicalnote;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import com.odontosuitepatients.application.mapper.ClinicalNoteMapper;
import com.odontosuitepatients.domain.model.Encounter;
import com.odontosuitepatients.domain.model.Patient;
import com.odontosuitepatients.domain.repository.EncounterRepository;
import com.odontosuitepatients.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalNoteServiceImpl implements ClinicalNoteService {

    private final PatientRepository patientRepository;
    private final EncounterRepository encounterRepository;

    @Override
    public ClinicalNoteResponse create(Long patientId, ClinicalNoteRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));

        Encounter encounter = Encounter.builder()
                .patient(patient)
                .dateTime(request.getDateTime())
                .tooth(request.getTooth())
                .diagnosis(request.getDiagnosis())
                .treatment(request.getTreatment())
                .observations(request.getObservations())
                .build();

        return ClinicalNoteMapper.toResponse(encounterRepository.save(encounter));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalNoteResponse> list(Long patientId) {
        return encounterRepository.findByPatientIdOrderByDateTimeDesc(patientId)
                .stream()
                .map(ClinicalNoteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClinicalNoteResponse get(Long patientId, Long id) {
        Encounter e = encounterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clinical note not found: " + id));

        if (!e.getPatient().getId().equals(patientId)) {
            throw new IllegalArgumentException("Clinical note does not belong to patient");
        }

        return ClinicalNoteMapper.toResponse(e);
    }

    @Override
    public void delete(Long patientId, Long id) {
        Encounter e = encounterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clinical note not found: " + id));

        if (!e.getPatient().getId().equals(patientId)) {
            throw new IllegalArgumentException("Clinical note does not belong to patient");
        }

        encounterRepository.delete(e);
    }
}

