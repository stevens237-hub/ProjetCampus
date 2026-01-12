package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.util.Categorie;
import fr.votreprojet.util.StatutAnnonce;
import fr.votreprojet.util.TypeEchange;
import java.util.*;

public class RechercheSimple implements StrategieRecherche {
   // RÉFÉRENCE au service pour accéder aux vraies données
    private PlateformeService plateformeService;
    
    public RechercheSimple() {
        this.plateformeService = PlateformeService.getInstance();
    }
    
    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println(" Recherche simple en cours...");
        List<Annonce> resultats = new ArrayList<>();
        
        Map<Long, Annonce> toutesLesAnnonces = plateformeService.getAnnonces();
        
        for (Annonce annonce : toutesLesAnnonces.values()) {
            // Ne chercher que les annonces PUBLIÉES
            if (annonce.getStatut() != StatutAnnonce.PUBLIEE) {
                continue;
            }
            
            boolean match = true;
            
            // Critère : titre
            if (criteres.containsKey("titre") && criteres.get("titre") instanceof String) {
                String titreRecherche = ((String) criteres.get("titre")).toLowerCase();
                String titreAnnonce = annonce.getTitre() != null ? 
                                      annonce.getTitre().toLowerCase() : "";
                
                if (!titreAnnonce.contains(titreRecherche)) {
                    match = false;
                }
            }
            
            // Critère : catégorie
            if (criteres.containsKey("categorie") && criteres.get("categorie") instanceof Categorie) {
                Categorie categorieRecherche = (Categorie) criteres.get("categorie");
                if (annonce.getCategorie() != categorieRecherche) {
                    match = false;
                }
            }
            
            // Critère : prix maximum
            if (criteres.containsKey("prixMax") && criteres.get("prixMax") instanceof Double) {
                Double prixMax = (Double) criteres.get("prixMax");
                if (annonce.getPrix() == null || annonce.getPrix() > prixMax) {
                    match = false;
                }
            }
            
            // Critère : type d'échange
            if (criteres.containsKey("typeEchange") && criteres.get("typeEchange") instanceof TypeEchange) {
                TypeEchange typeRecherche = (TypeEchange) criteres.get("typeEchange");
                if (annonce.getTypeEchange() != typeRecherche) {
                    match = false;
                }
            }
            
            // Critère : mot-clé dans description
            if (criteres.containsKey("motCle") && criteres.get("motCle") instanceof String) {
                String motCle = ((String) criteres.get("motCle")).toLowerCase();
                String description = annonce.getDescription() != null ? 
                                     annonce.getDescription().toLowerCase() : "";
                
                if (!description.contains(motCle)) {
                    match = false;
                }
            }
            
            if (match) {
                resultats.add(annonce);
            }
        }
        
        // Log détaillé pour debug
        System.out.println("🔍 Recherche terminée :");
        System.out.println("   • Annonces dans système : " + toutesLesAnnonces.size());
        System.out.println("   • Annonces publiées : " + 
                          toutesLesAnnonces.values().stream()
                              .filter(a -> a.getStatut() == StatutAnnonce.PUBLIEE)
                              .count());
        System.out.println("   • Résultats trouvés : " + resultats.size());
        
        return resultats;
    }
}