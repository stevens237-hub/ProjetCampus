package fr.votreprojet.util;

public enum TypeSignalement {

    ANNONCE_FRAUDULEUSE("Annonce frauduleuse"),
    COMPORTEMENT_ABUSIF("Comportement abusif"),
    CONTENU_OFFENSANT("Contenu offensant"),
    HARCÈLEMENT("Harcèlement"),
    AUTRE("Autre");
    
    private final String libelle;
    
    TypeSignalement(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }

}
