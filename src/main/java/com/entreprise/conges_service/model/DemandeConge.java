package com.entreprise.conges_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demande_conge")
public class DemandeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)     // plusieurs demandes peuvent appartenir a UN employe
    @JoinColumn(name = "employe_id") // nom de la colonne de jointure dans la table
    private Employe employe;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;     // LocalDate = une date sans heure (ex: 2026-07-01)

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)     // stocke le statut en texte ("EN_ATTENTE") et non en nombre
    @Column(nullable = false)
    private StatutDemande statut;

    protected DemandeConge() {}      // constructeur vide requis par JPA

    public DemandeConge(Employe employe, LocalDate dateDebut,
                        LocalDate dateFin, StatutDemande statut) {
        this.employe = employe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public Employe getEmploye() { return employe; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public StatutDemande getStatut() { return statut; }
}