package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.enums.ToothSurface;
import com.odontosuitepatients.domain.model.OdontogramItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OdontogramItemRepository extends JpaRepository<OdontogramItem, Long> {

    @Query("""
        select i from OdontogramItem i
        where i.odontogram.id = :odontogramId
        order by i.toothCode asc, i.surface asc
    """)
    List<OdontogramItem> findAllByOdontogramId(@Param("odontogramId") Long odontogramId);

    Optional<OdontogramItem> findByOdontogramIdAndToothCodeAndSurface(
            Long odontogramId, String toothCode, ToothSurface surface);
}
