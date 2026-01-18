package com.odontosuitepatients.application.service.clinicalevent;

import com.odontosuitepatients.application.dto.clinicalevent.ClinicalEventResponse;
import com.odontosuitepatients.application.dto.clinicalevent.CreateClinicalEventRequest;
import com.odontosuitepatients.domain.model.ClinicalEvent;

import java.util.List;

public interface ClinicalEventService {
    ClinicalEventResponse create(CreateClinicalEventRequest req);

    List<ClinicalEventResponse> list(Long patientId, int limit);

}
