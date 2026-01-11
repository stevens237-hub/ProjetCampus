package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.util.Categorie;
import fr.votreprojet.modele.Etudiant;
import java.util.*;

public class RechercheSimple implements StrategieRecherche {
    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println(" Recherche simple en cours...");
        List<Annonce> resultats = new ArrayList<>();
        
        // Simulation - dans la vraie application, on irait chercher dans une base de données
        for (Annonce annonce : getAnnoncesTest()) {
            boolean match = true;
            
            if (criteres.containsKey("titre") && criteres.get("titre") instanceof String) {
                String titreRecherche = ((String) criteres.get("titre")).toLowerCase();
                if (!annonce.getTitre().toLowerCase().contains(titreRecherche)) {
                    match = false;
                }
            }
            
            if (criteres.containsKey("categorie") && criteres.get("categorie") instanceof Categorie) {
                Categorie categorieRecherche = (Categorie) criteres.get("categorie");
                if (!annonce.getCategorie().equals(categorieRecherche)) {
                    match = false;
                }
            }
            
            if (criteres.containsKey("prixMax") && criteres.get("prixMax") instanceof Double) {
                Double prixMax = (Double) criteres.get("prixMax");
                if (annonce.getPrix() > prixMax) {
                    match = false;
                }
            }
            
            if (match) {
                resultats.add(annonce);
            }
        }
        
        System.out.println( resultats.size() + " résultat(s) trouvé(s)");
        return resultats;
    }
    
    private List<Annonce> getAnnoncesTest() {
        // Annonces de test
        List<Annonce> annonces = new ArrayList<>();
        Etudiant etudiantTest = new Etudiant("test@univ_smb.fr", "Testeur");
        
        Annonce a1 = new Annonce("Livre Java", "Livre sur la programmation Java", 
                                Categorie.MATERIEL_SCOLAIRE, fr.votreprojet.util.TypeEchange.VENTE, 
                                25.0, etudiantTest);
        a1.publier();
        
        Annonce a2 = new Annonce("Cours Maths", "Cours particuliers de mathématiques", 
                                Categorie.SERVICES, fr.votreprojet.util.TypeEchange.SERVICE, 
                                15.0, etudiantTest);
        a2.publier();
        
        annonces.add(a1);
        annonces.add(a2);
        
        return annonces;
    }
}