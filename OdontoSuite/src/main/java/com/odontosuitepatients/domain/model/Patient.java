package com.odontosuitepatients.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "patients",
        indexes = {
                @Index(name = "idx_patients_last_name", columnList = "last_name"),
                @Index(name = "idx_patients_doc", columnList = "document_number")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @NotBlank
    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 40)
    private String phone;

    @Email
    @Column(length = 120)
    private String email;

    @Column(length = 200)
    private String address;

    @Column(name = "obra_social", length = 120)
    private String obraSocial;

    @Column(name = "obra_social_number", length = 60)
    private String obraSocialNumber;

    @Column(name = "photo_url", length = 300)
    private String photoUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}

