package com.odontosuitepatients.web.controller;

import com.odontosuitepatients.application.dto.odontogram.OdontogramEntryResponse;
import com.odontosuitepatients.application.dto.odontogram.OdontogramResponse;
import com.odontosuitepatients.application.dto.odontogram.item.OdontogramItemUpsertRequest;
import com.odontosuitepatients.application.service.odontogram.OdontogramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients/{patientId}/odontogram")
public class OdontogramController {

    private final OdontogramService odontogramService;

    @GetMapping
    public OdontogramResponse get(@PathVariable Long patientId) {
        return odontogramService.getOrCreate(patientId);
    }

    /**
     * Upsert por (toothCode + surface). Si surface es null => estado general de la pieza.
     */
    @PutMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public OdontogramResponse upsertItem(
            @PathVariable Long patientId,
            @Valid @RequestBody OdontogramItemUpsertRequest request
    ) {
        return odontogramService.upsertItem(patientId, request);
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long patientId, @PathVariable Long itemId) {
        odontogramService.deleteItem(patientId, itemId);
    }
}

