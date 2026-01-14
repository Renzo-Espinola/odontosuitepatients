package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.Odontogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OdontogramRepository extends JpaRepository<Odontogram, Long> {
    Optional<Odontogram> findByPatientId(Long patientId);
}

