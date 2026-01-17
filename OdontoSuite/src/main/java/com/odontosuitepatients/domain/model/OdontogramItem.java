package com.odontosuitepatients.domain.model;

import com.odontosuitepatients.domain.enums.OdontogramStatus;
import com.odontosuitepatients.domain.enums.ToothSurface;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "odontogram_items",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_odontogram_tooth_surface",
                columnNames = {"odontogram_id", "tooth_code", "surface"}
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OdontogramItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "odontogram_id", nullable = false)
    private Odontogram odontogram;

    @Column(name = "tooth_code", nullable = false, length = 10)
    private String toothCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=10)
    private ToothSurface surface;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OdontogramStatus status;

    @Column(columnDefinition = "text")
    private String note;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        var now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
