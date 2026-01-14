package com.odontosuitepatients.application.service.clinicalnote;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import com.odontosuitepatients.domain.model.ClinicalNote;
import com.odontosuitepatients.domain.model.Patient;
import com.odontosuitepatients.domain.repository.ClinicalNoteRepository;
import com.odontosuitepatients.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalNoteServiceImpl implements ClinicalNoteService {

    private final ClinicalNoteRepository noteRepository;
    private final PatientRepository patientRepository;

    public ClinicalNoteResponse create(Long patientId, ClinicalNoteRequest req) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));

        ClinicalNote note = ClinicalNote.builder()
                .patient(patient)
                .dateTime(req.getDateTime() != null ? req.getDateTime() : LocalDateTime.now())
                .tooth(req.getTooth())
                .diagnosis(req.getDiagnosis())
                .treatment(req.getTreatment())
                .observations(req.getObservations())
                .build();

        return toResponse(noteRepository.save(note));
    }

    public List<ClinicalNoteResponse> list(Long patientId, LocalDateTime from, LocalDateTime to) {
        return noteRepository.list(patientId, from, to).stream().map(this::toResponse).toList();
    }

    public ClinicalNoteResponse update(Long noteId, ClinicalNoteRequest req) {
        ClinicalNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("ClinicalNote not found: " + noteId));

        note.setDateTime(req.getDateTime() != null ? req.getDateTime() : note.getDateTime());
        note.setTooth(req.getTooth());
        note.setDiagnosis(req.getDiagnosis());
        note.setTreatment(req.getTreatment());
        note.setObservations(req.getObservations());

        return toResponse(noteRepository.save(note));
    }

    public void delete(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new IllegalArgumentException("ClinicalNote not found: " + noteId);
        }
        noteRepository.deleteById(noteId);
    }

    private ClinicalNoteResponse toResponse(ClinicalNote n) {
        return new ClinicalNoteResponse(
                n.getId(),
                n.getPatient().getId(),
                n.getDateTime(),
                n.getTooth(),
                n.getDiagnosis(),
                n.getTreatment(),
                n.getObservations()
        );
    }
}

