package com.odontosuitepatients.application.service.odontogram;

import com.odontosuitepatients.application.dto.odontogram.OdontogramEntryResponse;
import com.odontosuitepatients.application.dto.odontogram.OdontogramResponse;
import com.odontosuitepatients.application.dto.odontogram.item.OdontogramItemUpsertRequest;

import java.util.List;

public interface OdontogramService {

    OdontogramResponse getOrCreate(Long patientId);

    OdontogramResponse upsertItem(Long patientId, OdontogramItemUpsertRequest request);

    void deleteItem(Long patientId, Long itemId);
}

