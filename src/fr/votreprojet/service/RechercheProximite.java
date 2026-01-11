package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.util.Categorie;
import fr.votreprojet.modele.Etudiant;
import java.util.*;

public class RechercheProximite implements StrategieRecherche {
    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println("📍 Recherche par proximité en cours...");
        List<Annonce> resultats = new ArrayList<>();
        
        for (Annonce annonce : getAnnoncesTest()) {
            boolean match = true;
            
            // Critères de base
            if (criteres.containsKey("titre") && criteres.get("titre") instanceof String) {
                String titreRecherche = ((String) criteres.get("titre")).toLowerCase();
                if (!annonce.getTitre().toLowerCase().contains(titreRecherche)) {
                    match = false;
                }
            }
            
            // Critère de localisation
            if (criteres.containsKey("localisation") && criteres.get("localisation") instanceof String) {
                String localisationRecherche = ((String) criteres.get("localisation")).toLowerCase();
                if (!annonce.getLocalisation().toLowerCase().contains(localisationRecherche)) {
                    match = false;
                }
            }
            
            // Critère de rayon (simulation)
            if (criteres.containsKey("rayon") && criteres.get("rayon") instanceof Integer) {
                Integer rayon = (Integer) criteres.get("rayon");
                // Simulation de calcul de distance
                if (rayon < 5) { // Rayon trop petit
                    match = false;
                }
            }
            
            if (match) {
                resultats.add(annonce);
            }
        }
        
        // Tri par proximité (simulé)
        resultats.sort((a1, a2) -> {
            // Simulation : on suppose que les annonces avec "Campus" sont plus proches
            if (a1.getLocalisation().contains("Campus") && !a2.getLocalisation().contains("Campus")) {
                return -1;
            } else if (!a1.getLocalisation().contains("Campus") && a2.getLocalisation().contains("Campus")) {
                return 1;
            }
            return 0;
        });
        
        System.out.println("✅ " + resultats.size() + " résultat(s) trouvé(s) à proximité");
        return resultats;
    }
    
    private List<Annonce> getAnnoncesTest() {
        List<Annonce> annonces = new ArrayList<>();
        Etudiant etudiantTest = new Etudiant("test@esisar.fr", "Testeur");
        
        Annonce a1 = new Annonce("Livre près du campus", "Livre disponible près du campus ESISAR", 
                                Categorie.MATERIEL_SCOLAIRE, fr.votreprojet.util.TypeEchange.VENTE, 
                                20.0, etudiantTest);
        a1.setLocalisation("Campus ESISAR, Grenoble");
        a1.publier();
        
        Annonce a2 = new Annonce("Cours à distance", "Cours en ligne disponible", 
                                Categorie.SERVICES, fr.votreprojet.util.TypeEchange.SERVICE, 
                                15.0, etudiantTest);
        a2.setLocalisation("En ligne");
        a2.publier();
        
        Annonce a3 = new Annonce("Colocation centre-ville", "Chambre en colocation", 
                                Categorie.BIENS_QUOTIDIENS, fr.votreprojet.util.TypeEchange.LOCATION, 
                                400.0, etudiantTest);
        a3.setLocalisation("Centre-ville Grenoble");
        a3.publier();
        
        annonces.add(a1);
        annonces.add(a2);
        annonces.add(a3);
        
        return annonces;
    }
}