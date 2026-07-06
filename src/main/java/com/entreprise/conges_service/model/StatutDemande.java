package com.entreprise.conges_service.model;

// enum = liste fermee des valeurs possibles pour le statut d'une demande
public enum StatutDemande {
    EN_ATTENTE,  // demande soumise, en attente de validation
    APPROUVE,    // demande validee par le manager
    REFUSE       // demande refusee
}
