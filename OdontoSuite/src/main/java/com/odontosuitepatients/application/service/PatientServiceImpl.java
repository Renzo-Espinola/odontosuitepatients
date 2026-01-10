package com.odontosuitepatients.application.service;

import com.odontosuitepatients.application.dto.PatientRequest;
import com.odontosuitepatients.application.dto.PatientResponse;
import com.odontosuitepatients.application.mapper.PatientMapper;
import com.odontosuitepatients.domain.model.Patient;
import com.odontosuitepatients.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientResponse create(final PatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        patient = patientRepository.save(patient);
        return patientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse update(final Long id, final PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        patientMapper.updateEntity(patient, request);
        patient = patientRepository.save(patient);
        return patientMapper.toResponse(patient);
    }

    @Override
    public void delete(final Long id) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        patient.setActive(false);
        patientRepository.save(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getById(final Long id) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> list(final String lastNameFilter) {
        final List<Patient> patients = (lastNameFilter == null || lastNameFilter.isBlank())
                ? patientRepository.findAll()
                : patientRepository.findByLastNameContainingIgnoreCase(lastNameFilter);

        return patients.stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> search(final String q, final boolean activeOnly) {
        final String query = (q == null) ? "" : q.trim();
        if (query.isBlank()) {
            return List.of();
        }

        return patientRepository.search(query, activeOnly)
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }
}
