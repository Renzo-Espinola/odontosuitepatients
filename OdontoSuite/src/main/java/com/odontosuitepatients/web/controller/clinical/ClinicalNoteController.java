package com.odontosuitepatients.web.controller.clinical;

import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteRequest;
import com.odontosuitepatients.application.dto.clinicalnote.ClinicalNoteResponse;
import com.odontosuitepatients.application.service.clinicalnote.ClinicalNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients/{patientId}/clinical-notes")
public class ClinicalNoteController {

    private final ClinicalNoteService clinicalNoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicalNoteResponse create(@PathVariable Long patientId,
                                       @Valid @RequestBody ClinicalNoteRequest request) {
        return clinicalNoteService.create(patientId, request);
    }

    @GetMapping
    public List<ClinicalNoteResponse> list(@PathVariable Long patientId) {
        return clinicalNoteService.list(patientId);
    }

    @GetMapping("/{id}")
    public ClinicalNoteResponse get(@PathVariable Long patientId, @PathVariable Long id) {
        return clinicalNoteService.get(patientId, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long patientId, @PathVariable Long id) {
        clinicalNoteService.delete(patientId, id);
    }
}

