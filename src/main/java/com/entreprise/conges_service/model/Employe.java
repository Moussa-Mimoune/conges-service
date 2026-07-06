package com.entreprise.conges_service.model;

import jakarta.persistence.*;

@Entity                          // cette classe = une table en base de donnees
@Table(name = "employe")         // nom de la table dans PostgreSQL
public class Employe {

    @Id                          // cle primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // valeur auto-generee par la base (1, 2, 3...)
    private Long id;

    @Column(nullable = false)    // colonne obligatoire, ne peut pas etre null
    private String nom;

    @Column(name = "solde_conges", nullable = false)  // mappe sur la colonne "solde_conges"
    private int soldeConges;

    protected Employe() {}       // constructeur vide requis par JPA (ne pas supprimer)

    public Employe(Long id, String nom, int soldeConges) {
        this.id = id;
        this.nom = nom;
        this.soldeConges = soldeConges;
    }

    // getters : permettent de lire les valeurs des champs prives
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public int getSoldeConges() { return soldeConges; }

    // setter : permet de modifier le solde apres creation
    public void setSoldeConges(int soldeConges) { this.soldeConges = soldeConges; }
}