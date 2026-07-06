package com.entreprise.conges_service.repository;

import com.entreprise.conges_service.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

// En heritant de JpaRepository, Spring genere automatiquement toutes les
// operations de base : findById, save, findAll, delete...
// On n'a pas besoin d'ecrire une seule ligne de SQL pour ca.
// <Employe, Long> = l'entite geree et le type de sa cle primaire
public interface EmployeRepository extends JpaRepository<Employe, Long> {

}