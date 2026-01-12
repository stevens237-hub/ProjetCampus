package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.util.StatutAnnonce;
import java.util.*;

public class RechercheProximite implements StrategieRecherche {
    private PlateformeService plateformeService;

    public RechercheProximite() {
        this.plateformeService = PlateformeService.getInstance();
    }

    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println("Recherche par proximité en cours...");
        List<Annonce> resultats = new ArrayList<>();

        Map<Long, Annonce> toutesLesAnnonces = plateformeService.getAnnonces();

        String localisationUtilisateur = (String) criteres.getOrDefault("localisation", "Campus Polytech");

        for (Annonce annonce : toutesLesAnnonces.values()) {
            if (annonce.getStatut() != StatutAnnonce.PUBLIEE) {
                continue;
            }

            boolean match = true;

            // Critère : titre (optionnel)
            if (criteres.containsKey("titre") && criteres.get("titre") instanceof String) {
                String titreRecherche = ((String) criteres.get("titre")).toLowerCase();
                if (!annonce.getTitre().toLowerCase().contains(titreRecherche)) {
                    match = false;
                }
            }

            // Critère : catégorie (optionnel)
            if (criteres.containsKey("categorie")) {
                Object categorieObj = criteres.get("categorie");
                if (categorieObj != null && !categorieObj.equals(annonce.getCategorie())) {
                    match = false;
                }
            }

            // Critère de proximité : comparer les localisations
            if (criteres.containsKey("rayon") && criteres.get("rayon") instanceof Integer) {
                Integer rayonMax = (Integer) criteres.get("rayon");
                if (!estDansRayon(annonce.getLocalisation(), localisationUtilisateur, rayonMax)) {
                    match = false;
                }
            } else {
                // Proximité par défaut : même campus
                if (!annonce.getLocalisation().equalsIgnoreCase(localisationUtilisateur)) {
                    match = false;
                }
            }

            // Critère : prix maximum (optionnel)
            if (criteres.containsKey("prixMax") && criteres.get("prixMax") instanceof Double) {
                Double prixMax = (Double) criteres.get("prixMax");
                if (annonce.getPrix() == null || annonce.getPrix() > prixMax) {
                    match = false;
                }
            }

            if (match) {
                resultats.add(annonce);
            }
        }

        // Trier par proximité (simulation)
        if (criteres.containsKey("localisation")) {
            resultats.sort(Comparator.comparing(a -> 
                calculerDistanceSimulee(a.getLocalisation(), localisationUtilisateur)));
        }

        System.out.println("Recherche par proximité terminée :");
        System.out.println("   • Annonces dans système : " + toutesLesAnnonces.size());
        System.out.println("   • Annonces publiées : " + 
            toutesLesAnnonces.values().stream()
                .filter(a -> a.getStatut() == StatutAnnonce.PUBLIEE)
                .count());
        System.out.println("   • Résultats trouvés : " + resultats.size());
        System.out.println("   • Localisation utilisée : " + localisationUtilisateur);

        return resultats;
    }

    private boolean estDansRayon(String localisation1, String localisation2, int rayonMax) {
        // Simulation simple de proximité
        if (localisation1 == null || localisation2 == null) return false;
        
        // Même localisation = distance 0
        if (localisation1.equalsIgnoreCase(localisation2)) return true;
        
        // Vérification des campus (simplifiée)
        if (localisation1.toLowerCase().contains("annecy") && 
            localisation2.toLowerCase().contains("annecy")) {
            return true;
        }
        if (localisation1.toLowerCase().contains("grenoble") && 
            localisation2.toLowerCase().contains("grenoble")) {
            return true;
        }
        
        // Distance simulée basée sur le nom
        int distance = calculerDistanceSimulee(localisation1, localisation2);
        return distance <= rayonMax;
    }

    private int calculerDistanceSimulee(String loc1, String loc2) {
        // Simulation de distance (pour la démo)
        // Dans une vraie app, on utiliserait des coordonnées GPS
        
        if (loc1.equalsIgnoreCase(loc2)) return 0;
        
        String loc1Lower = loc1.toLowerCase();
        String loc2Lower = loc2.toLowerCase();
        
        // Même campus = distance 0-1
        if ((loc1Lower.contains("annecy") && loc2Lower.contains("annecy")) ||
            (loc1Lower.contains("grenoble") && loc2Lower.contains("grenoble")) ||
            (loc1Lower.contains("valence") && loc2Lower.contains("valence"))) {
            return new Random().nextInt(5); // 0-4 km
        }
        
        // Campus différents = distance aléatoire 10-100km
        return 10 + new Random().nextInt(90);
    }

    // Méthode utilitaire pour obtenir les annonces proches d'un campus
    public List<Annonce> rechercherParCampus(String campus) {
        Map<String, Object> criteres = new HashMap<>();
        criteres.put("localisation", campus);
        return rechercher(criteres);
    }

    // Méthode utilitaire pour les annonces gratuites à proximité
    public List<Annonce> rechercherGratuitProche(String localisation) {
        Map<String, Object> criteres = new HashMap<>();
        criteres.put("localisation", localisation);
        criteres.put("prixMax", 0.0);
        return rechercher(criteres);
    }
}