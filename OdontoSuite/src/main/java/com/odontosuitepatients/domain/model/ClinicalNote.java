package com.odontosuitepatients.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "clinical_notes",
        indexes = @Index(name="idx_clinical_notes_patient_date", columnList="patient_id,date_time"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClinicalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 10)
    private String tooth;

    @Column(length = 200)
    private String diagnosis;

    @Column(length = 200)
    private String treatment;

    @Column(columnDefinition = "text")
    private String observations;
}

