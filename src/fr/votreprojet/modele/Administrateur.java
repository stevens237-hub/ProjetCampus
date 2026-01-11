package fr.votreprojet.modele;

import fr.votreprojet.service.Observateur;
import fr.votreprojet.service.Sujet;
import fr.votreprojet.util.*;
import java.util.*;

public class Administrateur extends Utilisateur implements Observateur {
    private Integer niveauAcces;
    private List<Signalement> signalementsTraites;
    
    public Administrateur(String email, String nom) {
        super(email, nom);
        this.niveauAcces = 1;
        this.signalementsTraites = new ArrayList<>();
        this.verifie = true; // Les admins sont automatiquement vérifiés
    }
    
    public Administrateur(String email, String nom, Integer niveauAcces) {
        super(email, nom);
        this.niveauAcces = niveauAcces;
        this.signalementsTraites = new ArrayList<>();
        this.verifie = true;
    }
    
    @Override
    public void actualiser(Sujet sujet, TypeEvenement typeEvenement) {
        System.out.println("🛡️ Admin " + nom + " notifié : " + typeEvenement);
        
        switch (typeEvenement) {
            case SIGNALEMENT_CREE:
            case ANNONCE_SIGNALEE:
            case MESSAGE_SIGNALE:
                System.out.println("   ⚠️ Action de modération requise !");
                break;
            case EVENEMENT_CREE:
                System.out.println("   🔍 Nouvel événement à surveiller");
                break;
            case NOUVELLE_ANNONCE:
            case ANNONCE_MODIFIEE:
            case ANNONCE_SUPPRIMEE:
            case ANNONCE_RESERVEE:
            case ANNONCE_TERMINEE:
            case NOUVEAU_MESSAGE:
            case MESSAGE_LU:
            case MESSAGE_SUPPRIME:
            case EVENEMENT_MODIFIE:
            case EVENEMENT_ANNULE:
            case EVENEMENT_INSCRIT:
            case EVENEMENT_DESINSCRIT:
            case TONTINE_CREE:
            case TONTINE_MODIFIEE:
            case TONTINE_SUPPRIMEE:
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
                System.out.println("   ℹ️ Notification reçue : " + typeEvenement);
                break;
            default:
                System.out.println("   ⚠️ Événement non géré : " + typeEvenement);
                break;
        }
    }
    
    @Override
    public void afficherProfil() {
        System.out.println("\n=== PROFIL ADMINISTRATEUR ===");
        System.out.println("Nom : " + nom);
        System.out.println("Email : " + email);
        System.out.println("Niveau d'accès : " + niveauAcces);
        System.out.println("Signalements traités : " + signalementsTraites.size());
        System.out.println("Vérifié : Oui (Administrateur)");
    }
    
    // Méthodes de modération
    public void validerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setVerifie(true);
        System.out.println("✅ Utilisateur " + utilisateur.getEmail() + " validé avec succès");
    }
    
    public void modererAnnonce(Annonce annonce) {
        if (annonce == null) return;
        
        System.out.println("\n🔍 Modération de l'annonce : " + annonce.getTitre());
        
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("1. Approuver l'annonce");
            System.out.println("2. Signaler l'annonce");
            System.out.println("3. Supprimer l'annonce");
            System.out.print("Choix : ");
            
            int choix = scanner.nextInt();
            
            switch (choix) {
                case 1:
                    annonce.setStatut(StatutAnnonce.PUBLIEE);
                    System.out.println("✅ Annonce approuvée");
                    break;
                case 2:
                    annonce.setStatut(StatutAnnonce.SIGNALÉE);
                    System.out.println("⚠️ Annonce signalée");
                    break;
                case 3:
                    annonce.setStatut(StatutAnnonce.SUPPRIMÉE);
                    annonce.notifier(TypeEvenement.ANNONCE_SUPPRIMEE);
                    System.out.println("🗑️ Annonce supprimée");
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        } finally {
            scanner.close();
        }
    }
    
    public void traiterSignalement(Signalement signalement) {
        if (signalement == null) return;
        
        System.out.println("\n⚖️ Traitement du signalement #" + signalement.getId());
        System.out.println("Type : " + signalement.getType().getLibelle());
        System.out.println("Description : " + signalement.getDescription());
        
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nOptions :");
            System.out.println("1. Marquer comme résolu");
            System.out.println("2. Escalader");
            System.out.println("3. Rejeter");
            System.out.print("Choix : ");
            
            int choix = scanner.nextInt();
            
            switch (choix) {
                case 1:
                    signalement.setStatut(StatutSignalement.RÉSOLU);
                    System.out.println("✅ Signalement résolu");
                    break;
                case 2:
                    signalement.setStatut(StatutSignalement.TRAITEMENT);
                    System.out.println("📈 Signalement escaladé");
                    break;
                case 3:
                    signalement.setStatut(StatutSignalement.REJETÉ);
                    System.out.println("❌ Signalement rejeté");
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        } finally {
            scanner.close();
        }
        
        signalementsTraites.add(signalement);
        signalement.notifier(TypeEvenement.SIGNALEMENT_TRAITE);
    }
    
    public void surveillerAnnonces(List<Annonce> annonces) {
        System.out.println("\n🔍 Surveillance des annonces");
        for (Annonce annonce : annonces) {
            if (annonce.getStatut() == StatutAnnonce.SIGNALÉE) {
                System.out.println("⚠️ Annonce signalée : " + annonce.getTitre());
            }
        }
    }
    
    public void genererRapport() {
        System.out.println("\n📊 RAPPORT D'ACTIVITÉ");
        System.out.println("=========================");
        System.out.println("Administrateur : " + nom);
        System.out.println("Date : " + new Date());
        System.out.println("Signalements traités : " + signalementsTraites.size());
        System.out.println("Niveau d'accès : " + niveauAcces);
        System.out.println("=========================");
    }
    
    // Getters et setters
    public Integer getNiveauAcces() { return niveauAcces; }
    public void setNiveauAcces(Integer niveauAcces) { this.niveauAcces = niveauAcces; }
    
    public List<Signalement> getSignalementsTraites() { return new ArrayList<>(signalementsTraites); }
}