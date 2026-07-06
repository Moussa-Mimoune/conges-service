package com.entreprise.conges_service.repository;

import com.entreprise.conges_service.model.DemandeConge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    // Spring Data genere automatiquement la requete SQL depuis le nom de la methode :
    // findBy + Employe + Id -> SELECT * FROM demande_conge WHERE employe_id = ?
    // pas besoin d'ecrire une seule ligne de SQL
    List<DemandeConge> findByEmployeId(Long employeId);
}