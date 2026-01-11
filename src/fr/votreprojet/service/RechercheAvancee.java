package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.modele.Etudiant;
import fr.votreprojet.util.Categorie;
import fr.votreprojet.util.StatutAnnonce;
import java.util.*;

public class RechercheAvancee implements StrategieRecherche {
    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println("🔍🔍 Recherche avancée en cours...");
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
            
            if (criteres.containsKey("categorie") && criteres.get("categorie") instanceof Categorie) {
                Categorie categorieRecherche = (Categorie) criteres.get("categorie");
                if (!annonce.getCategorie().equals(categorieRecherche)) {
                    match = false;
                }
            }
            
            // Critères avancés
            if (criteres.containsKey("prixMin") && criteres.get("prixMin") instanceof Double) {
                Double prixMin = (Double) criteres.get("prixMin");
                if (annonce.getPrix() < prixMin) {
                    match = false;
                }
            }
            
            if (criteres.containsKey("prixMax") && criteres.get("prixMax") instanceof Double) {
                Double prixMax = (Double) criteres.get("prixMax");
                if (annonce.getPrix() > prixMax) {
                    match = false;
                }
            }
            
            if (criteres.containsKey("statut") && criteres.get("statut") instanceof StatutAnnonce) {
                StatutAnnonce statutRecherche = (StatutAnnonce) criteres.get("statut");
                if (!annonce.getStatut().equals(statutRecherche)) {
                    match = false;
                }
            }
            
            // Recherche dans la description
            if (criteres.containsKey("description") && criteres.get("description") instanceof String) {
                String descriptionRecherche = ((String) criteres.get("description")).toLowerCase();
                if (!annonce.getDescription().toLowerCase().contains(descriptionRecherche)) {
                    match = false;
                }
            }
            
            if (match) {
                resultats.add(annonce);
            }
        }
        
        // Tri par pertinence (simulé)
        if (criteres.containsKey("tri") && criteres.get("tri").equals("prix")) {
            resultats.sort(Comparator.comparing(Annonce::getPrix));
        } else if (criteres.containsKey("tri") && criteres.get("tri").equals("date")) {
            resultats.sort(Comparator.comparing(Annonce::getDatePublication).reversed());
        }
        
        System.out.println("✅ " + resultats.size() + " résultat(s) trouvé(s) avec recherche avancée");
        return resultats;
    }
    
    private List<Annonce> getAnnoncesTest() {
        List<Annonce> annonces = new ArrayList<>();
        Etudiant etudiantTest = new Etudiant("test@esisar.fr", "Testeur");
        
        Annonce a1 = new Annonce("Livre Java Avancé", "Livre sur les design patterns en Java", 
                                Categorie.MATERIEL_SCOLAIRE, fr.votreprojet.util.TypeEchange.VENTE, 
                                35.0, etudiantTest);
        a1.publier();
        
        Annonce a2 = new Annonce("Cours Python", "Cours particuliers de Python pour débutants", 
                                Categorie.SERVICES, fr.votreprojet.util.TypeEchange.SERVICE, 
                                20.0, etudiantTest);
        a2.publier();
        
        Annonce a3 = new Annonce("Vélo électrique", "Vélo électrique en bon état", 
                                Categorie.BIENS_QUOTIDIENS, fr.votreprojet.util.TypeEchange.VENTE, 
                                300.0, etudiantTest);
        a3.publier();
        
        annonces.add(a1);
        annonces.add(a2);
        annonces.add(a3);
        
        return annonces;
    }
}