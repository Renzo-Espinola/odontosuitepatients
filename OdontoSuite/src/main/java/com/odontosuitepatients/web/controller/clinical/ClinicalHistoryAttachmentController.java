package com.odontosuitepatients.web.controller.clinical;

import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryAttachmentResponse;
import com.odontosuitepatients.application.service.clinicalhistory.ClinicalHistoryAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients/{patientId}/history/{entryId}/attachments")
public class ClinicalHistoryAttachmentController {

    private final ClinicalHistoryAttachmentService attachmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicalHistoryAttachmentResponse upload(
            @PathVariable Long patientId,
            @PathVariable Long entryId,
            @RequestPart("file") MultipartFile file) {

        return attachmentService.upload(patientId, entryId, file);
    }

    @GetMapping
    public List<ClinicalHistoryAttachmentResponse> list(
            @PathVariable Long entryId) {
        return attachmentService.list(entryId);
    }

    @DeleteMapping("/{attachmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long patientId,
            @PathVariable Long attachmentId) {
        attachmentService.delete(patientId, attachmentId);
    }
}

