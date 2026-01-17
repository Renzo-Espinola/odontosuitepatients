package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByLastNameContainingIgnoreCase(String lastName);

    @Query("""
                select p from Patient p
                where
                  (:activeOnly = false or p.active = true)
                  and (
                    lower(p.lastName) like lower(concat('%', :q, '%'))
                    or lower(p.firstName) like lower(concat('%', :q, '%'))
                    or p.documentNumber like concat('%', :q, '%')
                  )
                order by p.lastName asc, p.firstName asc
            """)
    List<Patient> search(@Param("q") String q, @Param("activeOnly") boolean activeOnly);

    boolean existsByDocumentNumberAndIdNot(String documentNumber, Long id);
}
