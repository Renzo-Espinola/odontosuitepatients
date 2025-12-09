package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalNoteRepository extends JpaRepository<ClinicalNote, Long> {
}
