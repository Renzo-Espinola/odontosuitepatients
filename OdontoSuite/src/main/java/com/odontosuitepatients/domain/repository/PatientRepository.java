package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);
}
