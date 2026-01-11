package fr.votreprojet.util;

public enum StatutEvenement {

    PLANIFIÉ("Planifié"),
    EN_COURS("En cours"),
    TERMINÉ("Terminé"),
    ANNULE("Annulé");
    
    private final String libelle;
    
    StatutEvenement(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}
