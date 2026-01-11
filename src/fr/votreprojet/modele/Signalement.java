package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.service.Observateur;
import fr.votreprojet.service.Sujet;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Signalement extends SujetAbstrait implements Observateur {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private TypeSignalement type;
    private String description;
    private Date dateSignalement;
    private StatutSignalement statut;
    private Etudiant signaleur;
    private Annonce cibleAnnonce;
    private Message cibleMessage;
    private Evaluation cibleEvaluation;
    private List<String> preuves;
    
    public Signalement(TypeSignalement type, String description, Etudiant signaleur) {
        this.id = counter.getAndIncrement();
        this.type = type;
        this.description = description;
        this.signaleur = signaleur;
        this.dateSignalement = new Date();
        this.statut = StatutSignalement.EN_ATTENTE;
        this.preuves = new ArrayList<>();
    }
    
    public void creer() {
        notifier(TypeEvenement.SIGNALEMENT_CREE);
        System.out.println(" Signalement créé avec succès");
    }
    
    public void escalader() {
        this.statut = StatutSignalement.TRAITEMENT;
        notifier(TypeEvenement.SIGNALEMENT_ESCALADE);
        System.out.println(" Signalement escaladé");
    }
    
    public void resoudre() {
        this.statut = StatutSignalement.RÉSOLU;
        notifier(TypeEvenement.SIGNALEMENT_RESOLU);
        System.out.println(" Signalement résolu");
    }
    
    public void rejeter() {
        this.statut = StatutSignalement.REJETÉ;
        System.out.println(" Signalement rejeté");
    }
    
    public void ajouterPreuve(String preuve) {
        preuves.add(preuve);
        System.out.println(" Preuve ajoutée");
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    // Implémentation de l'interface Observateur
    @Override
    public void actualiser(Sujet sujet, TypeEvenement typeEvenement) {
        // Logique de notification pour le signalement
        System.out.println(" Signalement #" + id + " notifié : " + typeEvenement);
    }
    
    public void afficherDetails() {
        System.out.println("\n SIGNALEMENT #" + id);
        System.out.println("════════════════════════════════");
        System.out.println("Type : " + type.getLibelle());
        System.out.println("Description : " + description);
        System.out.println("Date : " + dateSignalement);
        System.out.println("Statut : " + statut.getLibelle());
        System.out.println("Signaleur : " + signaleur.getNom());
        
        if (cibleAnnonce != null) {
            System.out.println("Cible : Annonce - " + cibleAnnonce.getTitre());
        } else if (cibleMessage != null) {
            System.out.println("Cible : Message");
        } else if (cibleEvaluation != null) {
            System.out.println("Cible : Évaluation");
        }
        
        System.out.println("Preuves : " + preuves.size());
        System.out.println("════════════════════════════════");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public TypeSignalement getType() { return type; }
    public void setType(TypeSignalement type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getDateSignalement() { return dateSignalement; }
    public void setDateSignalement(Date dateSignalement) { this.dateSignalement = dateSignalement; }
    
    public StatutSignalement getStatut() { return statut; }
    public void setStatut(StatutSignalement statut) { this.statut = statut; }
    
    public Etudiant getSignaleur() { return signaleur; }
    public void setSignaleur(Etudiant signaleur) { this.signaleur = signaleur; }
    
    public Annonce getCibleAnnonce() { return cibleAnnonce; }
    public void setCibleAnnonce(Annonce cibleAnnonce) { 
        this.cibleAnnonce = cibleAnnonce;
        if (cibleAnnonce != null) {
            cibleAnnonce.attacher(this); 
        }
    }
    
    public Message getCibleMessage() { return cibleMessage; }
    public void setCibleMessage(Message cibleMessage) { 
        this.cibleMessage = cibleMessage;
        if (cibleMessage != null) {
            cibleMessage.attacher(this); 
        }
    }
    
    public Evaluation getCibleEvaluation() { return cibleEvaluation; }
    public void setCibleEvaluation(Evaluation cibleEvaluation) { 
        this.cibleEvaluation = cibleEvaluation;
        if (cibleEvaluation != null) {
            cibleEvaluation.attacher(this); 
        }
    }
    
    public List<String> getPreuves() { return new ArrayList<>(preuves); }
}