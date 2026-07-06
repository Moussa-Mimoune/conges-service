package com.entreprise.conges_service.controller;

import com.entreprise.conges_service.model.DemandeConge;
import com.entreprise.conges_service.repository.DemandeCongeRepository;
import com.entreprise.conges_service.service.CongeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demandes")
public class CongeController {

    private final CongeService service;
    private final DemandeCongeRepository demandeRepository;

    // Spring injecte automatiquement les deux dependances
    public CongeController(CongeService service, DemandeCongeRepository demandeRepository) {
        this.service = service;
        this.demandeRepository = demandeRepository;
    }

    @PostMapping                         // POST /api/demandes
    @ResponseStatus(HttpStatus.CREATED)  // renvoie 201 en cas de succes
    public DemandeConge soumettre(@RequestBody Map<String, String> body) {
        Long employeId = Long.parseLong(body.get("employeId"));
        LocalDate debut = LocalDate.parse(body.get("dateDebut"));
        LocalDate fin   = LocalDate.parse(body.get("dateFin"));
        return service.soumettre(employeId, debut, fin);
    }

    @GetMapping("/employe/{employeId}")  // GET /api/demandes/employe/1
    public List<DemandeConge> listerParEmploye(@PathVariable Long employeId) {
        // @PathVariable = recupere {employeId} directement depuis l'URL
        return demandeRepository.findByEmployeId(employeId);
    }
}