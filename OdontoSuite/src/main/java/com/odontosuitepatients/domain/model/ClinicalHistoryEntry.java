package com.odontosuitepatients.domain.model;

import com.odontosuitepatients.domain.enums.ClinicalHistoryType;
import com.odontosuitepatients.domain.enums.ToothSurface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Table(name = "clinical_history_entries",
        indexes = {
                @Index(name = "idx_history_patient_occurred", columnList = "patient_id, occurred_at"),
                @Index(name = "idx_history_patient_type", columnList = "patient_id, type")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClinicalHistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ClinicalHistoryType type;

    @Column(name = "tooth_code", length = 10)
    private String toothCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "surface", length = 10)
    private ToothSurface surface;

    @Column(length = 120)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "text")
    private String note;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        var now = OffsetDateTime.now();
        if (occurredAt == null) occurredAt = now;
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}

