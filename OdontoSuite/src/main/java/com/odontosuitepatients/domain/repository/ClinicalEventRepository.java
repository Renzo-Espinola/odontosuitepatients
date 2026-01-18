package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicalEventRepository extends JpaRepository<ClinicalEvent, Long> {
    List<ClinicalEvent> findByPatientIdOrderByCreatedAtDesc(Long patientId, Pageable pageable);
}
