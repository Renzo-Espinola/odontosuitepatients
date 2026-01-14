package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalHistoryEntry;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClinicalHistoryRepository extends JpaRepository<ClinicalHistoryEntry, Long> {

    @Query("""
        select e from ClinicalHistoryEntry e
        where e.patient.id = :patientId
          and (:from is null or e.occurredAt >= :from)
          and (:to is null or e.occurredAt <= :to)
        order by e.occurredAt desc, e.id desc
    """)
    List<ClinicalHistoryEntry> list(
            @Param("patientId") Long patientId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to
    );
}

