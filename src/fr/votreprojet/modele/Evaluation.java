package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Evaluation extends SujetAbstrait {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private Integer note;
    private String commentaire;
    private Etudiant evaluateur;
    private Etudiant evalue;
    private Date dateEvaluation;
    private List<Signalement> signalements;
    
    public Evaluation(Integer note, String commentaire, Etudiant evaluateur, Etudiant evalue) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        
        this.id = counter.getAndIncrement();
        this.note = note;
        this.commentaire = commentaire;
        this.evaluateur = evaluateur;
        this.evalue = evalue;
        this.dateEvaluation = new Date();
        this.signalements = new ArrayList<>();
        
        // Attacher automatiquement l'évalué
        attacher(evalue);
    }
    
    public void publier() {
        notifier(TypeEvenement.EVALUATION_AJOUTEE);
        System.out.println(" Évaluation publiée avec succès");
    }
    
    public Signalement signaler(TypeSignalement type, String description) {
        Signalement signalement = new Signalement(type, description, evalue);
        signalement.setCibleEvaluation(this);
        signalements.add(signalement);
        notifier(TypeEvenement.EVALUATION_SIGNALEE);
        return signalement;
    }
    
    public void modifier(Integer nouvelleNote, String nouveauCommentaire) {
        if (nouvelleNote != null && nouvelleNote >= 1 && nouvelleNote <= 5) {
            this.note = nouvelleNote;
        }
        
        if (nouveauCommentaire != null) {
            this.commentaire = nouveauCommentaire;
        }
        
        notifier(TypeEvenement.EVALUATION_MODIFIEE);
        System.out.println(" Évaluation modifiée");
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    public void afficher() {
        System.out.println("\n ÉVALUATION #" + id);
        System.out.println("────────────────────────────");
        System.out.println("Note : " + note + "/5");
        System.out.println("Commentaire : " + commentaire);
        System.out.println("Évaluateur : " + evaluateur.getNom());
        System.out.println("Évalué : " + evalue.getNom());
        System.out.println("Date : " + dateEvaluation);
        System.out.println("Signalements : " + signalements.size());
        System.out.println("────────────────────────────");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getNote() { return note; }
    public void setNote(Integer note) { 
        if (note >= 1 && note <= 5) {
            this.note = note;
        }
    }
    
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    
    public Etudiant getEvaluateur() { return evaluateur; }
    public void setEvaluateur(Etudiant evaluateur) { this.evaluateur = evaluateur; }
    
    public Etudiant getEvalue() { return evalue; }
    public void setEvalue(Etudiant evalue) { this.evalue = evalue; }
    
    public Date getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(Date dateEvaluation) { this.dateEvaluation = dateEvaluation; }
    
    public List<Signalement> getSignalements() { return new ArrayList<>(signalements); }
}