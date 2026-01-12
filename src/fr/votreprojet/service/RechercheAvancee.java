package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import fr.votreprojet.util.Categorie;
import fr.votreprojet.util.StatutAnnonce;
import java.util.*;

public class RechercheAvancee implements StrategieRecherche {
    private PlateformeService plateformeService;

    public RechercheAvancee() {
        this.plateformeService = PlateformeService.getInstance();
    }

    @Override
    public List<Annonce> rechercher(Map<String, Object> criteres) {
        System.out.println(" Recherche avancée en cours...");
        List<Annonce> resultats = new ArrayList<>();

        // Utiliser les VRAIES données
        Map<Long, Annonce> toutesLesAnnonces = plateformeService.getAnnonces();

        for (Annonce annonce : toutesLesAnnonces.values()) {
            if (annonce.getStatut() != StatutAnnonce.PUBLIEE) {
                continue;
            }

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

        return resultats;
    }

}