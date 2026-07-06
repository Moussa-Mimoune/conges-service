package com.entreprise.conges_service.controller;

import com.entreprise.conges_service.model.DemandeConge;
import com.entreprise.conges_service.service.CongeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/demandes")
public class CongeController {

    private final CongeService service;

    public CongeController(CongeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeConge soumettre(@RequestBody Map<String, String> body) {
        Long employeId = Long.parseLong(body.get("employeId"));
        LocalDate debut = LocalDate.parse(body.get("dateDebut"));
        LocalDate fin   = LocalDate.parse(body.get("dateFin"));
        return service.soumettre(employeId, debut, fin);
    }
}