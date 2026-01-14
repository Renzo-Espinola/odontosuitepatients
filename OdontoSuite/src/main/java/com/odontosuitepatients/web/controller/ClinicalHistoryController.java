package com.odontosuitepatients.web.controller;

import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryRequest;
import com.odontosuitepatients.application.dto.clinicalhistory.ClinicalHistoryEntryResponse;
import com.odontosuitepatients.application.service.clinicalhistory.ClinicalHistoryService;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients/{patientId}/history")
public class ClinicalHistoryController {

    private final ClinicalHistoryService historyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicalHistoryEntryResponse create(
            @PathVariable Long patientId,
            @Valid @RequestBody ClinicalHistoryEntryRequest req) {
        return historyService.create(patientId, req);
    }

    @PutMapping("/{entryId}")
    public ClinicalHistoryEntryResponse update(
            @PathVariable Long patientId,
            @PathVariable Long entryId,
            @Valid @RequestBody ClinicalHistoryEntryRequest req) {
        return historyService.update(patientId, entryId, req);
    }

    @GetMapping("/{entryId}")
    public ClinicalHistoryEntryResponse get(
            @PathVariable Long patientId,
            @PathVariable Long entryId) {
        return historyService.get(patientId, entryId);
    }

    @GetMapping
    public List<ClinicalHistoryEntryResponse> list(
            @PathVariable Long patientId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime to) {
        return historyService.list(patientId, from, to);
    }

    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long patientId, @PathVariable Long entryId) {
        historyService.delete(patientId, entryId);
    }
}

