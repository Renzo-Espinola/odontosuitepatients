package com.odontosuitepatients.web.controller;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import com.odontosuitepatients.application.service.clinicalnote.ClinicalNoteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClinicalNoteController {

    private final ClinicalNoteServiceImpl service;

    @PostMapping("/api/patients/{patientId}/clinical-notes")
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicalNoteResponse create(
            @PathVariable Long patientId,
            @Valid @RequestBody ClinicalNoteRequest req
    ) {
        return service.create(patientId, req);
    }

    @GetMapping("/api/patients/{patientId}/clinical-notes")
    public List<ClinicalNoteResponse> list(
            @PathVariable Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.list(patientId, from, to);
    }

    @PutMapping("/api/clinical-notes/{noteId}")
    public ClinicalNoteResponse update(
            @PathVariable Long noteId,
            @Valid @RequestBody ClinicalNoteRequest req
    ) {
        return service.update(noteId, req);
    }

    @DeleteMapping("/api/clinical-notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long noteId) {
        service.delete(noteId);
    }
}

