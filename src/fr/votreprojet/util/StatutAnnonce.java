package fr.votreprojet.util;

public enum StatutAnnonce {

    PUBLIEE("Publiée"),
    RÉSERVÉE("Réservée"),
    TERMINÉE("Terminée"),
    SUPPRIMÉE("Supprimée"),
    SIGNALÉE("Signalée");
    
    private final String libelle;
    
    StatutAnnonce(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }

}
