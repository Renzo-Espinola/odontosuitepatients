package com.odontosuitepatients.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String documentNumber;

    private LocalDate birthDate;

    private String phone;

    @Email
    private String email;

    private String address;

    private String obraSocial;

    private String obraSocialNumber;

    @Column(name = "photo_url")
    private String photoUrl;

    @Builder.Default
    private boolean active = true;

}
