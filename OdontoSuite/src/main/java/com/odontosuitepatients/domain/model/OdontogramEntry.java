package com.odontosuitepatients.domain.model;

import com.odontosuitepatients.domain.enums.ToothCondition;
import com.odontosuitepatients.domain.enums.ToothSurface;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "odontogram_entries",
        uniqueConstraints = @UniqueConstraint(
                name = "ux_odontogram_entry_patient_tooth_surface",
                columnNames = {"patient_id", "tooth_code", "surface"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OdontogramEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "tooth_code", nullable = false, length = 4)
    private String toothCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "surface", nullable = false, length = 20)
    private ToothSurface surface;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false, length = 30)
    private ToothCondition condition;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        var now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}

