package com.odontosuitepatients.application.service.clinicalhistory;


import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryAttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClinicalHistoryAttachmentService {

    ClinicalHistoryAttachmentResponse upload(
            Long patientId,
            Long historyEntryId,
            MultipartFile file);

    List<ClinicalHistoryAttachmentResponse> list(Long historyEntryId);

    void delete(Long patientId, Long attachmentId);
}

