package com.fst.elearning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String nom;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String prenom;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String photoUrl;

    @Column(nullable = false)
    private boolean actif = true;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    // Relations
    @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Cours> coursEnseignes = new ArrayList<>();

    @OneToMany(mappedBy = "apprenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Inscription> inscriptions = new ArrayList<>();

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public String getInitiales() {
        return (prenom.isEmpty() ? "" : String.valueOf(prenom.charAt(0)).toUpperCase())
             + (nom.isEmpty() ? "" : String.valueOf(nom.charAt(0)).toUpperCase());
    }

    public enum Role {
        ADMIN, FORMATEUR, APPRENANT
    }
}
