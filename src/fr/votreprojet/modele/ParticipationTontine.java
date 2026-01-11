package fr.votreprojet.modele;

import java.util.*;

public class ParticipationTontine {
    private Etudiant etudiant;
    private Tontine tontine;
    private Date dateAdhesion;
    private Integer ordreTour;
    private Boolean aRecu;
    private List<Double> paiements;
    
    public ParticipationTontine(Etudiant etudiant, Tontine tontine, Integer ordreTour) {
        this.etudiant = etudiant;
        this.tontine = tontine;
        this.dateAdhesion = new Date();
        this.ordreTour = ordreTour;
        this.aRecu = false;
        this.paiements = new ArrayList<>();
    }
    
    public void ajouterPaiement(Double montant) {
        paiements.add(montant);
        System.out.println(" Paiement de " + montant + "€ enregistré pour " + etudiant.getNom());
    }
    
    public void recevoirTour() {
        this.aRecu = true;
        System.out.println(" " + etudiant.getNom() + " a reçu le tour de tontine");
    }
    
    public Double getTotalPaye() {
        return paiements.stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public void afficherDetails() {
        System.out.println("\n PARTICIPATION TONTINE");
        System.out.println("────────────────────────────");
        System.out.println("Étudiant : " + etudiant.getNom());
        System.out.println("Tontine : " + tontine.getNom());
        System.out.println("Date d'adhésion : " + dateAdhesion);
        System.out.println("Ordre de tour : " + ordreTour);
        System.out.println("A reçu le tour : " + (aRecu ? "Oui" : "Non"));
        System.out.println("Paiements : " + paiements.size() + " (" + getTotalPaye() + "€)");
        System.out.println("────────────────────────────");
    }
    
    // Getters et setters
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    
    public Tontine getTontine() { return tontine; }
    public void setTontine(Tontine tontine) { this.tontine = tontine; }
    
    public Date getDateAdhesion() { return dateAdhesion; }
    public void setDateAdhesion(Date dateAdhesion) { this.dateAdhesion = dateAdhesion; }
    
    public Integer getOrdreTour() { return ordreTour; }
    public void setOrdreTour(Integer ordreTour) { this.ordreTour = ordreTour; }
    
    public Boolean aRecu() { return aRecu; }
    public void setARecu(Boolean aRecu) { this.aRecu = aRecu; }
    
    public List<Double> getPaiements() { return new ArrayList<>(paiements); }
}