package com.entreprise.conges_service.service;

import com.entreprise.conges_service.exception.SoldeInsuffisantException;
import com.entreprise.conges_service.model.DemandeConge;
import com.entreprise.conges_service.model.Employe;
import com.entreprise.conges_service.model.StatutDemande;
import com.entreprise.conges_service.repository.DemandeCongeRepository;
import com.entreprise.conges_service.repository.EmployeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service  // dit a Spring de gerer cette classe et de pouvoir l'injecter ailleurs
public class CongeService {

    private final EmployeRepository employeRepository;
    private final DemandeCongeRepository demandeRepository;

    // injection par constructeur : Spring fournit les repositories automatiquement
    // c'est ce qui rend la classe testable (on pourra passer de faux repositories en test)
    public CongeService(EmployeRepository employeRepository,
                        DemandeCongeRepository demandeRepository) {
        this.employeRepository = employeRepository;
        this.demandeRepository = demandeRepository;
    }

    public DemandeConge soumettre(Long employeId, LocalDate debut, LocalDate fin) {

        // cherche l'employe par son id, leve une erreur s'il n'existe pas
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employe introuvable : " + employeId));

        // verifie que les dates sont coherentes
        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException(
                    "La date de fin est avant la date de debut");
        }

        // calcule le nombre de jours demandes (+1 car debut et fin sont inclus)
        long jours = ChronoUnit.DAYS.between(debut, fin) + 1;

        // LA regle metier : on ne peut pas demander plus que le solde disponible
        if (jours > employe.getSoldeConges()) {
            throw new SoldeInsuffisantException(
                    "Solde insuffisant : " + jours + " jours demandes, "
                            + employe.getSoldeConges() + " disponibles");
        }

        // tout est ok : on cree et enregistre la demande en base
        DemandeConge demande = new DemandeConge(employe, debut, fin, StatutDemande.EN_ATTENTE);
        return demandeRepository.save(demande); // save = INSERT en base
    }
}