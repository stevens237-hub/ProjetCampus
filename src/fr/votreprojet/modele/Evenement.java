package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Evenement extends SujetAbstrait {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private String titre;
    private String description;
    private TypeEvenement type;
    private Date date;
    private String lieu;
    private Etudiant createur;
    private List<Etudiant> participants;
    private Integer capaciteMax;
    private StatutEvenement statut;
    
    public Evenement(String titre, String description, Date date, String lieu, 
                     Integer capaciteMax, Etudiant createur) {
        this.id = counter.getAndIncrement();
        this.titre = titre;
        this.description = description;
        this.type = TypeEvenement.EVENEMENT_CREE;
        this.date = date;
        this.lieu = lieu;
        this.createur = createur;
        this.capaciteMax = capaciteMax;
        this.statut = StatutEvenement.PLANIFIÉ;
        this.participants = new ArrayList<>();
        this.participants.add(createur);
    }
    
    public void creer() {
        notifier(TypeEvenement.EVENEMENT_CREE);
        System.out.println(" Événement créé avec succès !");
    }
    
    public void modifier(String nouveauTitre, String nouvelleDescription, Date nouvelleDate) {
        boolean changement = false;
        
        if (nouveauTitre != null && !nouveauTitre.equals(titre)) {
            this.titre = nouveauTitre;
            changement = true;
        }
        
        if (nouvelleDescription != null && !nouvelleDescription.equals(description)) {
            this.description = nouvelleDescription;
            changement = true;
        }
        
        if (nouvelleDate != null && !nouvelleDate.equals(date)) {
            this.date = nouvelleDate;
            changement = true;
        }
        
        if (changement) {
            notifier(TypeEvenement.EVENEMENT_MODIFIE);
            System.out.println(" Événement modifié avec succès");
        }
    }
    
    public void annuler() {
        this.statut = StatutEvenement.ANNULE;
        notifier(TypeEvenement.EVENEMENT_ANNULE);
        System.out.println(" Événement annulé");
    }
    
    public void ajouterParticipant(Etudiant etudiant) {
        if (participants.size() < capaciteMax && !participants.contains(etudiant)) {
            participants.add(etudiant);
            notifier(TypeEvenement.EVENEMENT_INSCRIT);
            System.out.println(" " + etudiant.getNom() + " inscrit à l'événement");
        } else if (participants.size() >= capaciteMax) {
            System.out.println(" Capacité maximale atteinte");
        }
    }
    
    public void retirerParticipant(Etudiant etudiant) {
        if (participants.contains(etudiant)) {
            participants.remove(etudiant);
            notifier(TypeEvenement.EVENEMENT_DESINSCRIT);
            System.out.println(" " + etudiant.getNom() + " désinscrit de l'événement");
        }
    }
    
    public void demarrer() {
        this.statut = StatutEvenement.EN_COURS;
        System.out.println(" Événement démarré");
    }
    
    public void terminer() {
        this.statut = StatutEvenement.TERMINÉ;
        System.out.println(" Événement terminé");
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    public void afficherDetails() {
        System.out.println("\n ÉVÉNEMENT #" + id);
        System.out.println("════════════════════════════════");
        System.out.println("Titre : " + titre);
        System.out.println("Description : " + description);
        System.out.println("Date : " + date);
        System.out.println("Lieu : " + lieu);
        System.out.println("Créateur : " + createur.getNom());
        System.out.println("Statut : " + statut.getLibelle());
        System.out.println("Participants : " + participants.size() + "/" + capaciteMax);
        System.out.println("════════════════════════════════");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public TypeEvenement getType() { return type; }
    public void setType(TypeEvenement type) { this.type = type; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    public Etudiant getCreateur() { return createur; }
    public void setCreateur(Etudiant createur) { this.createur = createur; }
    
    public List<Etudiant> getParticipants() { return new ArrayList<>(participants); }
    
    public Integer getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(Integer capaciteMax) { this.capaciteMax = capaciteMax; }
    
    public StatutEvenement getStatut() { return statut; }
    public void setStatut(StatutEvenement statut) { this.statut = statut; }
}