package fr.votreprojet.modele;

import fr.votreprojet.service.Observateur;
import fr.votreprojet.service.Sujet;
import fr.votreprojet.service.StrategieRecherche;
import fr.votreprojet.util.*;
import java.util.*;

public class Etudiant extends Utilisateur implements Observateur {
    private String numeroEtudiant;
    private String campus;
    private String filiere;
    private Integer anneeEtude;
    private List<String> competences;
    private StrategieRecherche strategieRecherche;
    
    public Etudiant(String email, String nom) {
        super(email, nom);
        this.numeroEtudiant = generateNumeroEtudiant();
        this.campus = "Annecy";
        this.filiere = "IDU";
        this.anneeEtude = 1;
        this.competences = new ArrayList<>();
        this.strategieRecherche = null; // Initialisé plus tard
    }
    
    private String generateNumeroEtudiant() {
        return "ETU" + String.format("%06d", this.getId());
    }
    
    @Override
    public void actualiser(Sujet sujet, TypeEvenement typeEvenement) {
        System.out.println( nom + " notifié : " + typeEvenement);
        
        switch (typeEvenement) {
            case NOUVELLE_ANNONCE:
                if (sujet instanceof Annonce) {
                    Annonce annonce = (Annonce) sujet;
                    System.out.println("    Nouvelle annonce : " + annonce.getTitre());
                }
                break;
            case NOUVEAU_MESSAGE:
                System.out.println("    Vous avez un nouveau message");
                break;
            case EVENEMENT_CREE:
                System.out.println("    Nouvel événement créé");
                break;
            case ANNONCE_MODIFIEE:
            case ANNONCE_SUPPRIMEE:
            case ANNONCE_SIGNALEE:
            case ANNONCE_RESERVEE:
            case ANNONCE_TERMINEE:
            case MESSAGE_LU:
            case MESSAGE_SUPPRIME:
            case MESSAGE_SIGNALE:
            case EVENEMENT_MODIFIE:
            case EVENEMENT_ANNULE:
            case EVENEMENT_INSCRIT:
            case EVENEMENT_DESINSCRIT:
            case TONTINE_CREE:
            case TONTINE_MODIFIEE:
            case TONTINE_SUPPRIMEE:
            case SIGNALEMENT_CREE:
            case SIGNALEMENT_TRAITE:
            case SIGNALEMENT_RESOLU:
            case SIGNALEMENT_ESCALADE:
            case EVALUATION_AJOUTEE:
            case EVALUATION_MODIFIEE:
            case EVALUATION_SUPPRIMEE:
            case EVALUATION_SIGNALEE:
            case UTILISATEUR_INSCRIT:
            case UTILISATEUR_MODIFIE:
            case UTILISATEUR_VALIDE:
            case UTILISATEUR_SUSPENDU:
                // Pour tous les autres cas, on affiche juste le type
                System.out.println("   ℹ Événement reçu : " + typeEvenement);
                break;
            default:
                System.out.println("    Événement non géré : " + typeEvenement);
                break;
        }
    }
    
    @Override
    public void afficherProfil() {
        System.out.println("\n=== PROFIL ÉTUDIANT ===");
        System.out.println("Nom : " + nom);
        System.out.println("Email : " + email);
        System.out.println("Numéro étudiant : " + numeroEtudiant);
        System.out.println("Campus : " + campus);
        System.out.println("Filière : " + filiere);
        System.out.println("Année : " + anneeEtude);
        System.out.println("Compétences : " + competences);
        System.out.println("Vérifié : " + (verifie ? "Oui" : "Non"));
    }
    
    // Méthodes métier
    public Annonce creerAnnonce(String titre, String description, Categorie categorie, TypeEchange typeEchange, Double prix) {
        Annonce annonce = new Annonce(titre, description, categorie, typeEchange, prix, this);
        System.out.println(" Annonce créée avec succès !");
        return annonce;
    }
    
    public void sabonnerAnnonces(Categorie categorie) {
        System.out.println(" Abonnement aux annonces de catégorie : " + categorie.getLibelle());
    }
    
    public Evenement creerEvenement(String titre, String description, Date date, String lieu, int capaciteMax) {
        Evenement evenement = new Evenement(titre, description, date, lieu, capaciteMax, this);
        System.out.println(" Événement créé avec succès !");
        return evenement;
    }
    
    public void sinscrireEvenement(Evenement evenement) {
        if (evenement != null) {
            evenement.ajouterParticipant(this);
            System.out.println(" Inscription à l'événement réussie !");
        }
    }
    
    public Tontine creerTontine(String nom, Double montantMensuel) {
        Tontine tontine = new Tontine(nom, montantMensuel, this);
        System.out.println(" Tontine créée avec succès !");
        return tontine;
    }
    
    public void rejoindreTontine(Tontine tontine) {
        if (tontine != null) {
            tontine.ajouterParticipant(this);
            System.out.println(" Participation à la tontine réussie !");
        }
    }
    
    public Message envoyerMessage(Etudiant destinataire, String contenu) {
        Message message = new Message(contenu, this, destinataire);
        message.envoyer();
        System.out.println(" Message envoyé à " + destinataire.getNom());
        return message;
    }
    
    public Evaluation noterUtilisateur(Etudiant evalue, Integer note, String commentaire) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        
        Evaluation evaluation = new Evaluation(note, commentaire, this, evalue);
        System.out.println(" Évaluation ajoutée pour " + evalue.getNom());
        return evaluation;
    }
    
    public Signalement signalerAnnonce(Annonce annonce, TypeSignalement type, String description) {
        Signalement signalement = new Signalement(type, description, this);
        signalement.setCibleAnnonce(annonce);
        System.out.println(" Annonce signalée avec succès");
        return signalement;
    }
    
    // Getters et setters
    public String getNumeroEtudiant() { return numeroEtudiant; }
    public void setNumeroEtudiant(String numeroEtudiant) { this.numeroEtudiant = numeroEtudiant; }
    
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    
    public Integer getAnneeEtude() { return anneeEtude; }
    public void setAnneeEtude(Integer anneeEtude) { this.anneeEtude = anneeEtude; }
    
    public List<String> getCompetences() { return new ArrayList<>(competences); }
    public void ajouterCompetence(String competence) { this.competences.add(competence); }
    
    public StrategieRecherche getStrategieRecherche() { return strategieRecherche; }
    public void setStrategieRecherche(StrategieRecherche strategieRecherche) { 
        this.strategieRecherche = strategieRecherche; 
    }
}