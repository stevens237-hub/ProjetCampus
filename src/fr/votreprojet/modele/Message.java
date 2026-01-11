package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Message extends SujetAbstrait {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private String contenu;
    private Etudiant expediteur;
    private Etudiant destinataire;
    private Date dateEnvoi;
    private boolean lu;
    private List<Signalement> signalements;
    
    public Message(String contenu, Etudiant expediteur, Etudiant destinataire) {
        this.id = counter.getAndIncrement();
        this.contenu = contenu;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.dateEnvoi = new Date();
        this.lu = false;
        this.signalements = new ArrayList<>();
        
        // Attacher automatiquement le destinataire
        attacher(destinataire);
    }
    
    public void envoyer() {
        notifier(TypeEvenement.NOUVEAU_MESSAGE);
        System.out.println("📤 Message envoyé à " + destinataire.getNom());
    }
    
    public void marquerCommeLu() {
        if (!this.lu) {
            this.lu = true;
            notifier(TypeEvenement.MESSAGE_LU);
            System.out.println("👁️ Message marqué comme lu");
        }
    }
    
    public Signalement signaler(TypeSignalement type, String description) {
        Signalement signalement = new Signalement(type, description, destinataire);
        signalement.setCibleMessage(this);
        signalements.add(signalement);
        notifier(TypeEvenement.MESSAGE_SIGNALE);
        return signalement;
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    public void afficher() {
        System.out.println("\n💬 MESSAGE #" + id);
        System.out.println("────────────────────────────");
        System.out.println("De : " + expediteur.getNom());
        System.out.println("À : " + destinataire.getNom());
        System.out.println("Date : " + dateEnvoi);
        System.out.println("Contenu : " + contenu);
        System.out.println("Lu : " + (lu ? "Oui" : "Non"));
        System.out.println("Signalements : " + signalements.size());
        System.out.println("────────────────────────────");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    
    public Etudiant getExpediteur() { return expediteur; }
    public void setExpediteur(Etudiant expediteur) { this.expediteur = expediteur; }
    
    public Etudiant getDestinataire() { return destinataire; }
    public void setDestinataire(Etudiant destinataire) { this.destinataire = destinataire; }
    
    public Date getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(Date dateEnvoi) { this.dateEnvoi = dateEnvoi; }
    
    public boolean isLu() { return lu; }
    public void setLu(boolean lu) { this.lu = lu; }
    
    public List<Signalement> getSignalements() { return new ArrayList<>(signalements); }
}