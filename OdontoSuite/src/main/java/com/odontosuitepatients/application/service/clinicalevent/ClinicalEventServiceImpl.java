package com.odontosuitepatients.application.service.clinicalevent;
import com.odontosuitepatients.application.dto.clinicalevent.ClinicalEventResponse;
import com.odontosuitepatients.application.dto.clinicalevent.CreateClinicalEventRequest;
import com.odontosuitepatients.domain.enums.ClinicalEventType;
import com.odontosuitepatients.domain.model.ClinicalEvent;
import com.odontosuitepatients.domain.repository.ClinicalEventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicalEventServiceImpl implements ClinicalEventService {
    private final ClinicalEventRepository repo;

    public ClinicalEventServiceImpl(ClinicalEventRepository repo) {
        this.repo = repo;
    }

    public ClinicalEventResponse create(CreateClinicalEventRequest req) {
        ClinicalEvent e = new ClinicalEvent();
        e.setPatientId(req.patientId());
        e.setType(ClinicalEventType.valueOf(req.type()));
        e.setToothCode(req.toothCode());
        e.setSurface(req.surface());
        e.setFromStatus(req.fromStatus());
        e.setToStatus(req.toStatus());
        e.setNote(req.note());

        ClinicalEvent saved = repo.save(e);
        return toResponse(saved);
    }

    public List<ClinicalEventResponse> list(Long patientId, int limit) {
        return repo.findByPatientIdOrderByCreatedAtDesc(patientId, PageRequest.of(0, limit))
                .stream().map(this::toResponse).toList();
    }

    private ClinicalEventResponse toResponse(ClinicalEvent e) {
        return new ClinicalEventResponse(
                e.getId(),
                e.getPatientId(),
                e.getCreatedAt(),
                e.getType().name(),
                e.getToothCode(),
                e.getSurface(),
                e.getFromStatus(),
                e.getToStatus(),
                e.getNote()
        );
    }
}

