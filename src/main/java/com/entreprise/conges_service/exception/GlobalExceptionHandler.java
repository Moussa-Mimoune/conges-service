package com.entreprise.conges_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// @RestControllerAdvice = capte les exceptions de TOUTE l'application
// plus besoin de gerer les erreurs dans chaque controller individuellement
@RestControllerAdvice
public class GlobalExceptionHandler {

    // quand SoldeInsuffisantException est levee n'importe ou -> renvoie 409
    @ExceptionHandler(SoldeInsuffisantException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> soldeInsuffisant(SoldeInsuffisantException e) {
        return Map.of("erreur", e.getMessage());
    }

    // quand IllegalArgumentException est levee n'importe ou -> renvoie 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalide(IllegalArgumentException e) {
        return Map.of("erreur", e.getMessage());
    }
}