package com.odontosuitepatients.domain.model;

import com.odontosuitepatients.domain.enums.ClinicalEventType;
import com.odontosuitepatients.domain.enums.ToothSurface;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clinical_event")
public class ClinicalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClinicalEventType type;

    @Column(name = "tooth_code")
    private String toothCode;

    @Column(name = "surface")
    private ToothSurface surface;

    @Column(name = "from_status")
    private String fromStatus;

    @Column(name = "to_status")
    private String toStatus;

    @Column(columnDefinition = "text")
    private String note;
}
