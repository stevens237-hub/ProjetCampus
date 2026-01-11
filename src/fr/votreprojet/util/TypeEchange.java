package fr.votreprojet.util;

public enum TypeEchange {

    DON("Don"),
    VENTE("Vente"),
    TROC("Troc"),
    LOCATION("Location"),
    SERVICE("Service");
    
    private final String libelle;
    
    TypeEchange(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }

}
