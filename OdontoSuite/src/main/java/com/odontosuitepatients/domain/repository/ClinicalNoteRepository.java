package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClinicalNoteRepository extends JpaRepository<ClinicalNote, Long> {

    List<ClinicalNote> findByPatientIdOrderByDateTimeDesc(Long patientId);

    @Query("""
      select n from ClinicalNote n
      where n.patient.id = :patientId
        and (:from is null or n.dateTime >= :from)
        and (:to is null or n.dateTime <= :to)
      order by n.dateTime desc
  """)
    List<ClinicalNote> list(
            @Param("patientId") Long patientId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

