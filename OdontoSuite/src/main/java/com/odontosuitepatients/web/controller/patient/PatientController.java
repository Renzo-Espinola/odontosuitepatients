package com.odontosuitepatients.web.controller.patient;

import com.odontosuitepatients.application.dto.patient.PatientRequest;
import com.odontosuitepatients.application.dto.patient.PatientResponse;
import com.odontosuitepatients.application.service.patient.PatientService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse create(@Valid @RequestBody final PatientRequest patientRequest) {
        return patientService.create(patientRequest);
    }

    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable final Long id,
            @Valid @RequestBody final PatientRequest patientRequest) {
        return patientService.update(id, patientRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        patientService.delete(id);
    }

    @GetMapping("/{id}")
    public PatientResponse get(@PathVariable final Long id) {
        return patientService.getById(id);
    }

    @GetMapping
    public List<PatientResponse> list(@RequestParam(required = false) final String lastName) {
        return patientService.list(lastName);
    }

    @GetMapping("/search")
    public List<PatientResponse> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "true") boolean activeOnly) {
        return patientService.search(q, activeOnly);
    }

}
