package com.entreprise.conges_service.exception;

// Notre erreur metier sur-mesure
// RuntimeException = pas besoin de la declarer dans chaque methode qui peut la lancer
public class SoldeInsuffisantException extends RuntimeException {

    public SoldeInsuffisantException(String message) {
        super(message); // passe le message a la classe parente RuntimeException
    }
}