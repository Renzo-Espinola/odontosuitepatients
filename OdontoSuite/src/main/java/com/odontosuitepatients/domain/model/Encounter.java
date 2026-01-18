package com.odontosuitepatients.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import lombok.*;

@Entity
@Table(name = "clinical_notes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamptz")
    private OffsetDateTime dateTime;

    @Column(length = 10)
    private String tooth;

    @Column(length = 200)
    private String diagnosis;

    @Column(length = 200)
    private String treatment;

    @Column(columnDefinition = "text")
    private String observations;
}

