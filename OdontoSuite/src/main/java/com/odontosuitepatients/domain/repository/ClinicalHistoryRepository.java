package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalHistoryEntry;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalHistoryRepository extends JpaRepository<ClinicalHistoryEntry, Long> {

    List<ClinicalHistoryEntry> findByPatientIdOrderByOccurredAtDescIdDesc(Long patientId);

    List<ClinicalHistoryEntry> findByPatientIdAndOccurredAtGreaterThanEqualOrderByOccurredAtDescIdDesc(
            Long patientId, OffsetDateTime from);

    List<ClinicalHistoryEntry> findByPatientIdAndOccurredAtLessThanEqualOrderByOccurredAtDescIdDesc(
            Long patientId, OffsetDateTime to);

    List<ClinicalHistoryEntry> findByPatientIdAndOccurredAtBetweenOrderByOccurredAtDescIdDesc(
            Long patientId, OffsetDateTime from, OffsetDateTime to);
}

