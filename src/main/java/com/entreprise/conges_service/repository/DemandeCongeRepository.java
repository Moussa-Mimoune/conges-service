package com.entreprise.conges_service.repository;

import com.entreprise.conges_service.model.DemandeConge;
import org.springframework.data.jpa.repository.JpaRepository;

// Meme principe que EmployeRepository
// Spring genere automatiquement toutes les operations CRUD pour DemandeConge
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

}