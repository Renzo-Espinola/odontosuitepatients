package com.odontosuitepatients.domain.repository;

import com.odontosuitepatients.domain.model.ClinicalHistoryAttachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalHistoryAttachmentRepository
        extends JpaRepository<ClinicalHistoryAttachment, Long> {

    List<ClinicalHistoryAttachment> findByHistoryEntryId(Long historyEntryId);
}

