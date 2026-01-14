package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.enums.ToothSurface;
import com.odontosuitepatients.domain.model.OdontogramEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OdontogramEntryRepository extends JpaRepository<OdontogramEntry, Long> {

    List<OdontogramEntry> findByPatientIdOrderByToothCodeAscSurfaceAsc(Long patientId);

    Optional<OdontogramEntry> findByPatientIdAndToothCodeAndSurface(
            Long patientId, String toothCode, ToothSurface surface);

    void deleteByPatientIdAndToothCode(Long patientId, String toothCode);
}

