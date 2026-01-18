package com.odontosuitepatients.web.controller.clientevent;

import com.odontosuitepatients.application.dto.clinicalevent.ClinicalEventResponse;
import com.odontosuitepatients.application.dto.clinicalevent.CreateClinicalEventRequest;
import com.odontosuitepatients.application.service.clinicalevent.ClinicalEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clinical-events")
public class ClientEventController {

    private final ClinicalEventService clinicalEventService;

    public ClientEventController(ClinicalEventService clinicalEventService) {
        this.clinicalEventService = clinicalEventService;
    }

    @GetMapping
    public List<ClinicalEventResponse> list(@RequestParam Long patientId,
                                            @RequestParam(defaultValue = "20") int limit) {
        return clinicalEventService.list(patientId, limit);
    }

    @PostMapping
    public ClinicalEventResponse create(@RequestBody CreateClinicalEventRequest req) {
        return clinicalEventService.create(req);
    }
}
