package com.entreprise.conges_service;

import com.entreprise.conges_service.exception.SoldeInsuffisantException;
import com.entreprise.conges_service.model.DemandeConge;
import com.entreprise.conges_service.model.Employe;
import com.entreprise.conges_service.model.StatutDemande;
import com.entreprise.conges_service.repository.DemandeCongeRepository;
import com.entreprise.conges_service.repository.EmployeRepository;
import com.entreprise.conges_service.service.CongeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // active Mockito pour cette classe
class CongeServiceTest {

    @Mock
    EmployeRepository employeRepository;      // faux repository, on controle ses reponses

    @Mock
    DemandeCongeRepository demandeRepository; // faux repository

    @InjectMocks
    CongeService service;                     // Mockito injecte les 2 mocks dans le service

    @Test
    void demande_creee_si_solde_suffisant() {
        // ARRANGE : on programme le faux repository pour renvoyer un employe avec 20 jours
        when(employeRepository.findById(1L))
                .thenReturn(Optional.of(new Employe(1L, "Moussa", 20)));
        // quand save est appele, on renvoie l'objet recu tel quel
        when(demandeRepository.save(any(DemandeConge.class)))
                .thenAnswer(i -> i.getArgument(0));

        // ACT : on demande 5 jours (solde = 20, ca passe)
        DemandeConge resultat = service.soumettre(
                1L,
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 8, 5));

        // ASSERT : la demande est bien EN_ATTENTE
        assertEquals(StatutDemande.EN_ATTENTE, resultat.getStatut());
        // save a bien ete appele exactement 1 fois
        verify(demandeRepository, times(1)).save(any());
    }

    @Test
    void erreur_409_si_solde_insuffisant() {
        // ARRANGE : employe avec seulement 3 jours de solde
        when(employeRepository.findById(1L))
                .thenReturn(Optional.of(new Employe(1L, "Moussa", 3)));

        // ACT + ASSERT : on demande 5 jours -> doit lever SoldeInsuffisantException
        assertThrows(SoldeInsuffisantException.class, () ->
                service.soumettre(
                        1L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5)));

        // save ne doit JAMAIS etre appele si le solde est insuffisant
        verify(demandeRepository, never()).save(any());
    }

    @Test
    void erreur_400_si_employe_inexistant() {
        // ARRANGE : le faux repository renvoie un Optional vide (employe introuvable)
        when(employeRepository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT + ASSERT : doit lever IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                service.soumettre(
                        99L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5)));
    }

    @Test
    void erreur_400_si_dates_incoherentes() {
        when(employeRepository.findById(1L))
                .thenReturn(Optional.of(new Employe(1L, "Moussa", 20)));

        // ACT + ASSERT : date de fin AVANT date de debut -> erreur
        assertThrows(IllegalArgumentException.class, () ->
                service.soumettre(
                        1L,
                        LocalDate.of(2026, 8, 5),   // debut
                        LocalDate.of(2026, 8, 1)));  // fin AVANT debut
    }
}