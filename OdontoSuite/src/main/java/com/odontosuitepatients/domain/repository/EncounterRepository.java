package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.OffsetDateTime;
import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    List<Encounter> findByPatientIdOrderByDateTimeDesc(Long patientId);

    @Query("""
        select e
        from Encounter e
        where e.patient.id = :patientId
          and (:from is null or e.dateTime >= :from)
          and (:to   is null or e.dateTime <= :to)
        order by e.dateTime desc
    """)
    List<Encounter> list(
            @Param("patientId") Long patientId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to
    );
}

