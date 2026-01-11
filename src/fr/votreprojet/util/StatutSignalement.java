package fr.votreprojet.util;

public enum StatutSignalement {

    EN_ATTENTE("En attente"),
    TRAITEMENT("En traitement"),
    RÉSOLU("Résolu"),
    REJETÉ("Rejeté");
    
    private final String libelle;
    
    StatutSignalement(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }

}
