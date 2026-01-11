package fr.votreprojet.util;

public enum Categorie {

    MATERIEL_SCOLAIRE("Matériel scolaire"),
    SERVICES("Services"),
    BIENS_QUOTIDIENS("Biens quotidiens"),
    ALIMENTAIRE("Alimentaire"),
    ÉVÉNEMENT("Événement"),
    TONTINE("Tontine");
    
    private final String libelle;
    
    Categorie(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public static Categorie fromString(String text) {
        for (Categorie c : Categorie.values()) {
            if (c.libelle.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}
