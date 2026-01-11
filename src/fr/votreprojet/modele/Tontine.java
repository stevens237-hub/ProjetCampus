package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Tontine extends SujetAbstrait {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private String nom;
    private Double montantMensuel;
    private Etudiant createur;
    private List<ParticipationTontine> participants;
    private Date dateDebut;
    private StatutTontine statut;
    private Integer tourActuel;
    
    public Tontine(String nom, Double montantMensuel, Etudiant createur) {
        this.id = counter.getAndIncrement();
        this.nom = nom;
        this.montantMensuel = montantMensuel;
        this.createur = createur;
        this.participants = new ArrayList<>();
        this.dateDebut = new Date();
        this.statut = StatutTontine.EN_COURS;
        this.tourActuel = 1;
        
        // Ajouter le créateur comme premier participant
        ParticipationTontine participation = new ParticipationTontine(createur, this, 1);
        participants.add(participation);
    }
    
    public void creer() {
        notifier(TypeEvenement.TONTINE_CREE);
        System.out.println("✅ Tontine créée avec succès !");
    }
    
    public void modifier(String nouveauNom, Double nouveauMontant) {
        boolean changement = false;
        
        if (nouveauNom != null && !nouveauNom.equals(nom)) {
            this.nom = nouveauNom;
            changement = true;
        }
        
        if (nouveauMontant != null && !nouveauMontant.equals(montantMensuel)) {
            this.montantMensuel = nouveauMontant;
            changement = true;
        }
        
        if (changement) {
            notifier(TypeEvenement.TONTINE_MODIFIEE);
            System.out.println("✏️ Tontine modifiée avec succès");
        }
    }
    
    public void ajouterParticipant(Etudiant etudiant) {
        int ordre = participants.size() + 1;
        ParticipationTontine participation = new ParticipationTontine(etudiant, this, ordre);
        participants.add(participation);
        notifier(TypeEvenement.TONTINE_NOUVEAU_PARTICIPANT);
        System.out.println("✅ " + etudiant.getNom() + " a rejoint la tontine (ordre: " + ordre + ")");
    }
    
    public void effectuerPaiement(Etudiant etudiant) {
        for (ParticipationTontine participation : participants) {
            if (participation.getEtudiant().equals(etudiant)) {
                participation.ajouterPaiement(montantMensuel);
                notifier(TypeEvenement.TONTINE_PAIEMENT_EFFECTUE);
                System.out.println("💰 " + etudiant.getNom() + " a effectué son paiement");
                return;
            }
        }
        System.out.println("❌ Participant non trouvé");
    }
    
    public void distribuerTour() {
        if (tourActuel > participants.size()) {
            System.out.println("❌ Tous les tours ont été distribués");
            return;
        }
        
        for (ParticipationTontine participation : participants) {
            if (participation.getOrdreTour() == tourActuel && !participation.aRecu()) {
                participation.recevoirTour();
                notifier(TypeEvenement.TONTINE_TOUR_DISTRIBUE);
                System.out.println("🎁 Tour " + tourActuel + " distribué à " + 
                                 participation.getEtudiant().getNom());
                tourActuel++;
                return;
            }
        }
    }
    
    public void terminer() {
        this.statut = StatutTontine.TERMINÉE;
        System.out.println("🏁 Tontine terminée");
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    public void afficherDetails() {
        System.out.println("\n💰 TONTINE #" + id);
        System.out.println("════════════════════════════════");
        System.out.println("Nom : " + nom);
        System.out.println("Montant mensuel : " + montantMensuel + "€");
        System.out.println("Créateur : " + createur.getNom());
        System.out.println("Date de début : " + dateDebut);
        System.out.println("Statut : " + statut.getLibelle());
        System.out.println("Tour actuel : " + tourActuel);
        System.out.println("Participants (" + participants.size() + ") :");
        
        for (ParticipationTontine participation : participants) {
            System.out.println("  - " + participation.getEtudiant().getNom() + 
                             " (Ordre: " + participation.getOrdreTour() + 
                             ", A reçu: " + (participation.aRecu() ? "Oui" : "Non") + ")");
        }
        System.out.println("════════════════════════════════");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Double getMontantMensuel() { return montantMensuel; }
    public void setMontantMensuel(Double montantMensuel) { this.montantMensuel = montantMensuel; }
    
    public Etudiant getCreateur() { return createur; }
    public void setCreateur(Etudiant createur) { this.createur = createur; }
    
    public List<ParticipationTontine> getParticipants() { return new ArrayList<>(participants); }
    
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    
    public StatutTontine getStatut() { return statut; }
    public void setStatut(StatutTontine statut) { this.statut = statut; }
    
    public Integer getTourActuel() { return tourActuel; }
    public void setTourActuel(Integer tourActuel) { this.tourActuel = tourActuel; }
}