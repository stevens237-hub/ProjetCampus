package fr.votreprojet.util;

public enum StatutTontine {

    EN_COURS("En cours"),
    TERMINÉE("Terminée");
    
    private final String libelle;
    
    StatutTontine(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }

}
