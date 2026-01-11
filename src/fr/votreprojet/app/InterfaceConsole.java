package fr.votreprojet.app;

import fr.votreprojet.service.PlateformeService;
import fr.votreprojet.modele.*;
import fr.votreprojet.service.FabriqueEtudiant;
import fr.votreprojet.service.StrategieRecherche;
import fr.votreprojet.service.RechercheSimple;
import fr.votreprojet.service.RechercheAvancee;
import fr.votreprojet.service.RechercheProximite;
import fr.votreprojet.util.*;
import java.util.*;

public class InterfaceConsole {
    private PlateformeService plateforme;
    private Scanner scanner;
    private boolean enExecution;
    
    public InterfaceConsole() {
        this.plateforme = PlateformeService.getInstance();
        this.scanner = new Scanner(System.in);
        this.enExecution = true;
    }
    
    public void demarrer() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       PLATEFORME COLLABORATIVE           ║");
        System.out.println("║                                          ║");
        System.out.println("╚══════════════════════════════════════════╝");
        
        while (enExecution) {
            if (plateforme.getUtilisateurConnecte() == null) {
                afficherMenuConnexion();
            } else {
                afficherMenuPrincipal();
            }
        }
        
        scanner.close();
        System.out.println("\n Merci d'avoir utilisé notre plateforme !");
    }
    
    private void afficherMenuConnexion() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("              CONNEXION");
        System.out.println("══════════════════════════════════════════");
        System.out.println("1. Se connecter (utilisateurs existants)");
        System.out.println("2. Créer un compte étudiant");
        System.out.println("3. Mode démo (connexion automatique)");
        System.out.println("4. Quitter");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        switch (choix) {
            case 1:
                seConnecter();
                break;
            case 2:
                creerCompteEtudiant();
                break;
            case 3:
                modeDemo();
                break;
            case 4:
                enExecution = false;
                break;
        }
    }
    
    private void afficherMenuPrincipal() {
        Utilisateur utilisateur = plateforme.getUtilisateurConnecte();
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  Bienvenue, " + utilisateur.getNom() + "!");
        System.out.println("══════════════════════════════════════════");
        
        if (utilisateur instanceof Etudiant) {
            afficherMenuEtudiant();
        } else if (utilisateur instanceof Administrateur) {
            afficherMenuAdministrateur();
        }
    }
    
    private void afficherMenuEtudiant() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n MENU ÉTUDIANT");
        System.out.println("1.  Gérer mes annonces");
        System.out.println("2.  Rechercher des annonces");
        System.out.println("3.  Événements");
        System.out.println("4.  Tontines");
        System.out.println("5.  Messagerie");
        System.out.println("6.  Évaluations");
        System.out.println("7.  Signalements");
        System.out.println("8.  Mon profil");
        System.out.println("9.  Se déconnecter");
        System.out.println("0.  Quitter l'application");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(0, 9);
        
        switch (choix) {
            case 1:
                gererAnnoncesEtudiant(etudiant);
                break;
            case 2:
                rechercherAnnonces();
                break;
            case 3:
                gererEvenements();
                break;
            case 4:
                gererTontines();
                break;
            case 5:
                gererMessagerie();
                break;
            case 6:
                gererEvaluations();
                break;
            case 7:
                gererSignalementsEtudiant();
                break;
            case 8:
                etudiant.afficherProfil();
                pause();
                break;
            case 9:
                plateforme.deconnecter();
                break;
            case 0:
                enExecution = false;
                break;
        }
    }
    
    private void afficherMenuAdministrateur() {
        System.out.println("\n MENU ADMINISTRATEUR");
        System.out.println("1.  Gérer les utilisateurs");
        System.out.println("2.  Modérer les annonces");
        System.out.println("3.  Traiter les signalements");
        System.out.println("4.  Générer un rapport");
        System.out.println("5.  Surveillance");
        System.out.println("6.  Mon profil");
        System.out.println("7.  Se déconnecter");
        System.out.println("0.  Quitter l'application");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(0, 7);
        
        switch (choix) {
            case 1:
                gererUtilisateurs();
                break;
            case 2:
                modererAnnonces();
                break;
            case 3:
                traiterSignalements();
                break;
            case 4:
                genererRapport();
                break;
            case 5:
                surveillerPlateforme();
                break;
            case 6:
                ((Administrateur) plateforme.getUtilisateurConnecte()).afficherProfil();
                pause();
                break;
            case 7:
                plateforme.deconnecter();
                break;
            case 0:
                enExecution = false;
                break;
        }
    }
    
    // ============ MÉTHODES DE CONNEXION ============
    
    private void seConnecter() {
        System.out.println("\n CONNEXION");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        if (plateforme.connecter(email, nom)) {
            System.out.println(" Connexion réussie !");
        } else {
            System.out.println(" Échec de connexion. Vérifiez vos identifiants.");
        }
        pause();
    }
    
    private void creerCompteEtudiant() {
        System.out.println("\n CRÉATION DE COMPTE ÉTUDIANT");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        FabriqueEtudiant fabrique = new FabriqueEtudiant();
        Etudiant nouvelEtudiant = (Etudiant) fabrique.creerUtilisateur(email, nom);
        
        System.out.print("Campus : ");
        nouvelEtudiant.setCampus(scanner.nextLine());
        System.out.print("Filière : ");
        nouvelEtudiant.setFiliere(scanner.nextLine());
        System.out.print("Année d'étude : ");
        nouvelEtudiant.setAnneeEtude(lireEntier(1, 5));
        
        plateforme.ajouterUtilisateur(nouvelEtudiant);
        System.out.println(" Compte créé avec succès !");
        
        // Se connecter automatiquement
        plateforme.connecter(email, nom);
        pause();
    }
    
    private void modeDemo() {
        System.out.println("\n MODE DÉMO");
        System.out.println("1. Se connecter en tant qu'étudiant");
        System.out.println("2. Se connecter en tant qu'administrateur");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 2);
        
        if (choix == 1) {
            plateforme.connecter("jean@univ_smb.fr", "Jean Dupont");
        } else {
            plateforme.connecter("admin@univ_smb.fr", "Admin System");
        }
        pause();
    }
    
    // ============ MÉTHODES ÉTUDIANT ============
    
    private void gererAnnoncesEtudiant(Etudiant etudiant) {
        System.out.println("\n MES ANNONCES");
        System.out.println("1.  Créer une nouvelle annonce");
        System.out.println("2.  Voir mes annonces");
        System.out.println("3.  Modifier une annonce");
        System.out.println("4.  Supprimer une annonce");
        System.out.println("5.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 5);
        
        switch (choix) {
            case 1:
                creerAnnonce(etudiant);
                break;
            case 2:
                afficherAnnoncesEtudiant(etudiant);
                break;
            case 3:
                modifierAnnonce(etudiant);
                break;
            case 4:
                supprimerAnnonce(etudiant);
                break;
        }
    }
    
    // NOUVELLE MÉTHODE : Modifier annonce
    private void modifierAnnonce(Etudiant etudiant) {
        System.out.println("\n MODIFICATION D'ANNONCE");
        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);
        
        if (mesAnnonces.isEmpty()) {
            System.out.println(" Vous n'avez pas d'annonces à modifier.");
            pause();
            return;
        }
        
        for (int i = 0; i < mesAnnonces.size(); i++) {
            System.out.println((i + 1) + ". " + mesAnnonces.get(i).getTitre() + 
                             " (" + mesAnnonces.get(i).getStatut().getLibelle() + ")");
        }
        
        System.out.print("Choisir une annonce à modifier (0 pour annuler) : ");
        int choix = lireEntier(0, mesAnnonces.size());
        
        if (choix > 0) {
            Annonce annonce = mesAnnonces.get(choix - 1);
            System.out.print("Nouveau titre : ");
            String nouveauTitre = scanner.nextLine();
            System.out.print("Nouvelle description : ");
            String nouvelleDescription = scanner.nextLine();
            
            annonce.modifier(nouveauTitre, nouvelleDescription);
            System.out.println(" Annonce modifiée avec succès !");
        }
        pause();
    }
    
    // NOUVELLE MÉTHODE : Supprimer annonce
    private void supprimerAnnonce(Etudiant etudiant) {
        System.out.println("\n SUPPRESSION D'ANNONCE");
        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);
        
        if (mesAnnonces.isEmpty()) {
            System.out.println(" Vous n'avez pas d'annonces à supprimer.");
            pause();
            return;
        }
        
        for (int i = 0; i < mesAnnonces.size(); i++) {
            System.out.println((i + 1) + ". " + mesAnnonces.get(i).getTitre());
        }
        
        System.out.print("Choisir une annonce à supprimer (0 pour annuler) : ");
        int choix = lireEntier(0, mesAnnonces.size());
        
        if (choix > 0) {
            Annonce annonce = mesAnnonces.get(choix - 1);
            annonce.supprimer();
            plateforme.getAnnonces().remove(annonce.getIdLong());
            System.out.println(" Annonce supprimée avec succès !");
        }
        pause();
    }
    
    // MÉTHODE UTILITAIRE : Récupérer les annonces d'un étudiant
    private List<Annonce> getAnnoncesEtudiant(Etudiant etudiant) {
        List<Annonce> mesAnnonces = new ArrayList<>();
        for (Annonce annonce : plateforme.getAnnonces().values()) {
            if (annonce.getCreateur().equals(etudiant)) {
                mesAnnonces.add(annonce);
            }
        }
        return mesAnnonces;
    }
    
    private void creerAnnonce(Etudiant etudiant) {
        System.out.println("\n CRÉATION D'ANNONCE");
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        
        System.out.println("\nCatégories disponibles :");
        for (int i = 0; i < Categorie.values().length; i++) {
            System.out.println((i + 1) + ". " + Categorie.values()[i].getLibelle());
        }
        System.out.print("Choisir une catégorie : ");
        int catChoix = lireEntier(1, Categorie.values().length);
        Categorie categorie = Categorie.values()[catChoix - 1];
        
        System.out.println("\nTypes d'échange :");
        for (int i = 0; i < TypeEchange.values().length; i++) {
            System.out.println((i + 1) + ". " + TypeEchange.values()[i].getLibelle());
        }
        System.out.print("Choisir un type : ");
        int typeChoix = lireEntier(1, TypeEchange.values().length);
        TypeEchange typeEchange = TypeEchange.values()[typeChoix - 1];
        
        System.out.print("Prix (0 pour gratuit) : ");
        double prix = lireDouble(0, Double.MAX_VALUE);
        
        Annonce annonce = etudiant.creerAnnonce(titre, description, categorie, typeEchange, prix);
        annonce.publier();
        plateforme.ajouterAnnonce(annonce);
        
        System.out.println("\n Annonce créée et publiée avec succès !");
        pause();
    }
    
    private void afficherAnnoncesEtudiant(Etudiant etudiant) {
        System.out.println("\n MES ANNONCES");
        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);
        
        if (mesAnnonces.isEmpty()) {
            System.out.println(" Vous n'avez pas encore créé d'annonces.");
        } else {
            for (int i = 0; i < mesAnnonces.size(); i++) {
                System.out.println("\n" + (i + 1) + ". " + mesAnnonces.get(i).getTitre() + 
                                 " (" + mesAnnonces.get(i).getStatut().getLibelle() + ")");
            }
            
            System.out.print("\n Voir les détails d'une annonce (0 pour retour) : ");
            int choix = lireEntier(0, mesAnnonces.size());
            
            if (choix > 0) {
                mesAnnonces.get(choix - 1).afficherDetails();
            }
        }
        pause();
    }
    
    private void rechercherAnnonces() {
        System.out.println("\n RECHERCHE D'ANNONCES");
        System.out.println("1.  Recherche simple");
        System.out.println("2.  Recherche avancée");
        System.out.println("3.  Recherche par proximité");
        System.out.println("4.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        if (choix == 4) return;
        
        System.out.print("Mot-clé (titre) : ");
        String motCle = scanner.nextLine();
        
        Map<String, Object> criteres = new HashMap<>();
        criteres.put("titre", motCle);
        
        StrategieRecherche strategie;
        switch (choix) {
            case 1:
                strategie = new RechercheSimple();
                break;
            case 2:
                strategie = new RechercheAvancee();
                break;
            case 3:
                strategie = new RechercheProximite();
                break;
            default:
                strategie = new RechercheSimple();
        }
        
        List<Annonce> resultats = strategie.rechercher(criteres);
        
        if (resultats.isEmpty()) {
            System.out.println(" Aucune annonce trouvée.");
        } else {
            System.out.println("\n RÉSULTATS (" + resultats.size() + ")");
            for (int i = 0; i < resultats.size(); i++) {
                Annonce annonce = resultats.get(i);
                System.out.println("\n" + (i + 1) + ". " + annonce.getTitre());
                System.out.println("    " + annonce.getLocalisation());
                System.out.println("    " + (annonce.getPrix() != null ? annonce.getPrix() + "€" : "Gratuit"));
                System.out.println("    " + annonce.getDescription().substring(0, Math.min(50, annonce.getDescription().length())) + "...");
            }
            
            System.out.print("\n Voir les détails d'une annonce (0 pour retour) : ");
            int detailChoix = lireEntier(0, resultats.size());
            
            if (detailChoix > 0) {
                resultats.get(detailChoix - 1).afficherDetails();
                
                if (plateforme.estEtudiantConnecte()) {
                    System.out.print("\n1.  Contacter le vendeur\n2.  Signaler l'annonce\n3.  Retour\n Votre choix : ");
                    int action = lireEntier(1, 3);
                    
                    if (action == 1) {
                        contacterVendeur(resultats.get(detailChoix - 1));
                    } else if (action == 2) {
                        signalerAnnonce(resultats.get(detailChoix - 1));
                    }
                }
            }
        }
        pause();
    }
    
    private void gererEvenements() {
        System.out.println("\n ÉVÉNEMENTS");
        System.out.println("1.  Créer un événement");
        System.out.println("2.  Voir les événements à venir");
        System.out.println("3.  S'inscrire à un événement");
        System.out.println("4.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        switch (choix) {
            case 1:
                creerEvenement();
                break;
            case 2:
                afficherEvenements();
                break;
            case 3:
                sinscrireEvenement();
                break;
        }
    }
    
    private void creerEvenement() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n CRÉATION D'ÉVÉNEMENT");
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Lieu : ");
        String lieu = scanner.nextLine();
        System.out.print("Capacité maximale : ");
        int capacite = lireEntier(1, 1000);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date date = cal.getTime();
        
        Evenement evenement = etudiant.creerEvenement(titre, description, date, lieu, capacite);
        evenement.creer();
        plateforme.ajouterEvenement(evenement);
        
        System.out.println(" Événement créé avec succès !");
        pause();
    }
    
    private void afficherEvenements() {
        List<Evenement> evenements = plateforme.rechercherEvenements();
        
        if (evenements.isEmpty()) {
            System.out.println(" Aucun événement à venir.");
        } else {
            System.out.println("\n ÉVÉNEMENTS À VENIR (" + evenements.size() + ")");
            for (int i = 0; i < evenements.size(); i++) {
                Evenement event = evenements.get(i);
                System.out.println("\n" + (i + 1) + ". " + event.getTitre());
                System.out.println("    " + event.getLieu());
                System.out.println("    " + event.getDate());
                System.out.println("    " + event.getParticipants().size() + "/" + event.getCapaciteMax() + " participants");
            }
            
            System.out.print("\n Voir les détails d'un événement (0 pour retour) : ");
            int choix = lireEntier(0, evenements.size());
            
            if (choix > 0) {
                evenements.get(choix - 1).afficherDetails();
            }
        }
        pause();
    }
    
    // ============ MÉTHODES ADMINISTRATEUR ============
    
    private void gererUtilisateurs() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n GESTION DES UTILISATEURS");
        List<Utilisateur> utilisateurs = new ArrayList<>(plateforme.getUtilisateurs().values());
        
        System.out.println("\n LISTE DES UTILISATEURS");
        for (int i = 0; i < utilisateurs.size(); i++) {
            Utilisateur user = utilisateurs.get(i);
            String type = user instanceof Etudiant ? "Étudiant" : "Administrateur";
            System.out.println((i + 1) + ". " + user.getNom() + " (" + user.getEmail() + ") - " + type + 
                             " - Vérifié: " + (user.getVerifie() ? "✅" : "❌"));
        }
        
        System.out.print("\n Sélectionner un utilisateur (0 pour retour) : ");
        int choix = lireEntier(0, utilisateurs.size());
        
        if (choix > 0) {
            Utilisateur selected = utilisateurs.get(choix - 1);
            System.out.println("\n ACTIONS POUR " + selected.getNom());
            System.out.println("1.  Valider le compte");
            System.out.println("2.  Voir le profil");
            System.out.println("3.  Retour");
            System.out.print(" Votre choix : ");
            
            int action = lireEntier(1, 3);
            
            if (action == 1) {
                admin.validerUtilisateur(selected);
                System.out.println(" Compte validé avec succès !");
            } else if (action == 2) {
                selected.afficherProfil();
            }
        }
        pause();
    }
    
    private void modererAnnonces() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n MODÉRATION DES ANNONCES");
        List<Annonce> annonces = new ArrayList<>(plateforme.getAnnonces().values());
        
        // Filtrer les annonces signalées ou à modérer
        List<Annonce> annoncesAModerer = new ArrayList<>();
        for (Annonce annonce : annonces) {
            if (annonce.getStatut() == StatutAnnonce.SIGNALÉE || annonce.getSignalements().size() > 0) {
                annoncesAModerer.add(annonce);
            }
        }
        
        if (annoncesAModerer.isEmpty()) {
            System.out.println(" Aucune annonce à modérer pour le moment.");
        } else {
            System.out.println(" ANNONCES À MODÉRER (" + annoncesAModerer.size() + ")");
            for (int i = 0; i < annoncesAModerer.size(); i++) {
                Annonce annonce = annoncesAModerer.get(i);
                System.out.println("\n" + (i + 1) + ". " + annonce.getTitre());
                System.out.println("    Créateur: " + annonce.getCreateur().getNom());
                System.out.println("    Signalements: " + annonce.getSignalements().size());
                System.out.println("    Statut: " + annonce.getStatut().getLibelle());
            }
            
            System.out.print("\n Sélectionner une annonce à modérer (0 pour retour) : ");
            int choix = lireEntier(0, annoncesAModerer.size());
            
            if (choix > 0) {
                Annonce annonce = annoncesAModerer.get(choix - 1);
                annonce.afficherDetails();
                admin.modererAnnonce(annonce);
            }
        }
        pause();
    }
    
    private void traiterSignalements() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n TRAITEMENT DES SIGNALEMENTS");
        List<Signalement> signalementsEnAttente = plateforme.getSignalementsEnAttente();
        
        if (signalementsEnAttente.isEmpty()) {
            System.out.println(" Aucun signalement en attente de traitement.");
        } else {
            System.out.println(" SIGNALEMENTS EN ATTENTE (" + signalementsEnAttente.size() + ")");
            for (int i = 0; i < signalementsEnAttente.size(); i++) {
                Signalement signalement = signalementsEnAttente.get(i);
                System.out.println("\n" + (i + 1) + ". Signalement #" + signalement.getIdLong());
                System.out.println("    Type: " + signalement.getType().getLibelle());
                System.out.println("    Signaleur: " + signalement.getSignaleur().getNom());
                System.out.println("    Date: " + signalement.getDateSignalement());
            }
            
            System.out.print("\n Sélectionner un signalement à traiter (0 pour retour) : ");
            int choix = lireEntier(0, signalementsEnAttente.size());
            
            if (choix > 0) {
                Signalement signalement = signalementsEnAttente.get(choix - 1);
                signalement.afficherDetails();
                admin.traiterSignalement(signalement);
            }
        }
        pause();
    }
    
    private void genererRapport() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n GÉNÉRATION DE RAPPORT");
        admin.genererRapport();
        
        // Statistiques supplémentaires
        System.out.println("\n STATISTIQUES DE LA PLATEFORME");
        System.out.println(" Utilisateurs: " + plateforme.getUtilisateurs().size());
        System.out.println(" Annonces: " + plateforme.getAnnonces().size());
        System.out.println(" Événements: " + plateforme.getEvenements().size());
        System.out.println(" Tontines: " + plateforme.getTontines().size());
        System.out.println(" Signalements: " + plateforme.getSignalements().size());
        
        pause();
    }
    
    // ============ MÉTHODES UTILITAIRES ============
    
    private void contacterVendeur(Annonce annonce) {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n CONTACTER LE VENDEUR");
        System.out.print("Votre message : ");
        String message = scanner.nextLine();
        
        Message msg = etudiant.envoyerMessage(annonce.getCreateur(), 
            "À propos de votre annonce \"" + annonce.getTitre() + "\" : " + message);
        plateforme.ajouterMessage(msg);
        
        System.out.println(" Message envoyé avec succès !");
        pause();
    }
    
    private void signalerAnnonce(Annonce annonce) {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n SIGNALER UNE ANNONCE");
        System.out.println("Types de signalement :");
        for (int i = 0; i < TypeSignalement.values().length; i++) {
            System.out.println((i + 1) + ". " + TypeSignalement.values()[i].getLibelle());
        }
        System.out.print("Choisir un type : ");
        int typeChoix = lireEntier(1, TypeSignalement.values().length);
        TypeSignalement type = TypeSignalement.values()[typeChoix - 1];
        
        System.out.print("Description du problème : ");
        String description = scanner.nextLine();
        
        Signalement signalement = etudiant.signalerAnnonce(annonce, type, description);
        signalement.creer();
        plateforme.ajouterSignalement(signalement);
        
        System.out.println(" Annonce signalée avec succès !");
        pause();
    }
    
    private void sinscrireEvenement() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        List<Evenement> evenements = plateforme.rechercherEvenements();
        if (evenements.isEmpty()) {
            System.out.println(" Aucun événement disponible pour l'inscription.");
            pause();
            return;
        }
        
        System.out.println("\n S'INSCRIRE À UN ÉVÉNEMENT");
        for (int i = 0; i < evenements.size(); i++) {
            Evenement event = evenements.get(i);
            System.out.println((i + 1) + ". " + event.getTitre() + 
                             " (" + event.getParticipants().size() + "/" + event.getCapaciteMax() + ")");
        }
        
        System.out.print("\nChoisir un événement (0 pour retour) : ");
        int choix = lireEntier(0, evenements.size());
        
        if (choix > 0) {
            Evenement selected = evenements.get(choix - 1);
            etudiant.sinscrireEvenement(selected);
        }
        pause();
    }
    
    private void gererTontines() {
        System.out.println("\n TONTINES");
        System.out.println("1.  Créer une tontine");
        System.out.println("2.  Voir les tontines disponibles");
        System.out.println("3.  Rejoindre une tontine");
        System.out.println("4.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        if (choix == 1) {
            creerTontine();
        } else if (choix == 2 || choix == 3) {
            afficherTontines(choix == 3);
        }
    }
    
    private void creerTontine() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n CRÉATION DE TONTINE");
        System.out.print("Nom de la tontine : ");
        String nom = scanner.nextLine();
        System.out.print("Montant mensuel (€) : ");
        double montant = lireDouble(1, 1000);
        
        Tontine tontine = etudiant.creerTontine(nom, montant);
        tontine.creer();
        plateforme.ajouterTontine(tontine);
        
        System.out.println(" Tontine créée avec succès !");
        pause();
    }
    
    private void afficherTontines(boolean pourRejoindre) {
        List<Tontine> tontines = plateforme.rechercherTontines();
        
        if (tontines.isEmpty()) {
            System.out.println(" Aucune tontine disponible.");
        } else {
            System.out.println("\n TONTINES DISPONIBLES (" + tontines.size() + ")");
            for (int i = 0; i < tontines.size(); i++) {
                Tontine tontine = tontines.get(i);
                System.out.println("\n" + (i + 1) + ". " + tontine.getNom());
                System.out.println("    Montant: " + tontine.getMontantMensuel() + "€/mois");
                System.out.println("    Participants: " + tontine.getParticipants().size());
                System.out.println("    Créateur: " + tontine.getCreateur().getNom());
                System.out.println("    Statut: " + tontine.getStatut().getLibelle());
            }
            
            if (pourRejoindre) {
                System.out.print("\n Rejoindre une tontine (0 pour retour) : ");
                int choix = lireEntier(0, tontines.size());
                
                if (choix > 0) {
                    Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
                    etudiant.rejoindreTontine(tontines.get(choix - 1));
                }
            } else {
                System.out.print("\n Voir les détails d'une tontine (0 pour retour) : ");
                int choix = lireEntier(0, tontines.size());
                
                if (choix > 0) {
                    tontines.get(choix - 1).afficherDetails();
                }
            }
        }
        pause();
    }
    
    private void gererMessagerie() {
        System.out.println("\n MESSAGERIE");
        System.out.println("1.  Envoyer un message");
        System.out.println("2.  Voir mes messages");
        System.out.println("3.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 3);
        
        // Cette méthode serait implémentée avec une vraie base de données de messages
        System.out.println("\n Fonctionnalité de messagerie en cours de développement...");
        pause();
    }
    
    private void gererEvaluations() {
        System.out.println("\n ÉVALUATIONS");
        System.out.println("1.  Évaluer un utilisateur");
        System.out.println("2.  Voir mes évaluations");
        System.out.println("3.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 3);
        
        if (choix == 1) {
            evaluerUtilisateur();
        } else if (choix == 2) {
            afficherEvaluations();
        }
    }
    
    private void evaluerUtilisateur() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        // Récupérer les autres utilisateurs
        List<Utilisateur> autresUtilisateurs = new ArrayList<>();
        for (Utilisateur user : plateforme.getUtilisateurs().values()) {
            if (user instanceof Etudiant && !user.equals(etudiant)) {
                autresUtilisateurs.add(user);
            }
        }
        
        if (autresUtilisateurs.isEmpty()) {
            System.out.println(" Aucun autre utilisateur à évaluer.");
            pause();
            return;
        }
        
        System.out.println("\n ÉVALUER UN UTILISATEUR");
        for (int i = 0; i < autresUtilisateurs.size(); i++) {
            System.out.println((i + 1) + ". " + autresUtilisateurs.get(i).getNom());
        }
        
        System.out.print("\nChoisir un utilisateur (0 pour retour) : ");
        int choix = lireEntier(0, autresUtilisateurs.size());
        
        if (choix > 0) {
            Etudiant evalue = (Etudiant) autresUtilisateurs.get(choix - 1);
            System.out.print("Note (1-5) : ");
            int note = lireEntier(1, 5);
            System.out.print("Commentaire : ");
            String commentaire = scanner.nextLine();
            
            Evaluation evaluation = etudiant.noterUtilisateur(evalue, note, commentaire);
            evaluation.publier();
            plateforme.ajouterEvaluation(evaluation);
            
            System.out.println(" Évaluation ajoutée avec succès !");
        }
        pause();
    }
    
    private void afficherEvaluations() {
        System.out.println("\n MES ÉVALUATIONS");
        // Cette méthode nécessiterait une vraie base de données pour récupérer les évaluations
        System.out.println(" Fonctionnalité en cours de développement...");
        pause();
    }
    
    private void gererSignalementsEtudiant() {
        System.out.println("\n MES SIGNALEMENTS");
        System.out.println("1.  Signaler une annonce");
        System.out.println("2.  Signaler un message");
        System.out.println("3.  Voir mes signalements");
        System.out.println("4.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        switch (choix) {
            case 1:
                signalerAnnonceManuelle();
                break;
            case 2:
                System.out.println("\n Fonctionnalité en cours de développement...");
                break;
            case 3:
                afficherMesSignalements();
                break;
            default:
                break;
        }
        pause();
    }
    
    private void signalerAnnonceManuelle() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        List<Annonce> annonces = new ArrayList<>(plateforme.getAnnonces().values());
        annonces.removeIf(a -> a.getCreateur().equals(etudiant)); // Ne pas signaler ses propres annonces
        
        if (annonces.isEmpty()) {
            System.out.println(" Aucune annonce à signaler.");
            pause();
            return;
        }
        
        System.out.println("\n SIGNALER UNE ANNONCE");
        for (int i = 0; i < annonces.size(); i++) {
            System.out.println((i + 1) + ". " + annonces.get(i).getTitre() + 
                             " (" + annonces.get(i).getCreateur().getNom() + ")");
        }
        
        System.out.print("\nChoisir une annonce (0 pour retour) : ");
        int choix = lireEntier(0, annonces.size());
        
        if (choix > 0) {
            Annonce annonce = annonces.get(choix - 1);
            
            System.out.println("\nTypes de signalement :");
            for (int i = 0; i < TypeSignalement.values().length; i++) {
                System.out.println((i + 1) + ". " + TypeSignalement.values()[i].getLibelle());
            }
            System.out.print("Choisir un type : ");
            int typeChoix = lireEntier(1, TypeSignalement.values().length);
            TypeSignalement type = TypeSignalement.values()[typeChoix - 1];
            
            System.out.print("Description du problème : ");
            String description = scanner.nextLine();
            
            Signalement signalement = etudiant.signalerAnnonce(annonce, type, description);
            signalement.creer();
            plateforme.ajouterSignalement(signalement);
            
            System.out.println(" Signalement créé avec succès !");
        }
        pause();
    }
    
    private void afficherMesSignalements() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
        
        List<Signalement> mesSignalements = new ArrayList<>();
        for (Signalement signalement : plateforme.getSignalements().values()) {
            if (signalement.getSignaleur().equals(etudiant)) {
                mesSignalements.add(signalement);
            }
        }
        
        if (mesSignalements.isEmpty()) {
            System.out.println(" Vous n'avez créé aucun signalement.");
        } else {
            System.out.println("\n MES SIGNALEMENTS (" + mesSignalements.size() + ")");
            for (int i = 0; i < mesSignalements.size(); i++) {
                Signalement s = mesSignalements.get(i);
                System.out.println("\n" + (i + 1) + ". Signalement #" + s.getIdLong());
                System.out.println("    Type: " + s.getType().getLibelle());
                System.out.println("    Date: " + s.getDateSignalement());
                System.out.println("    Statut: " + s.getStatut().getLibelle());
                
                if (s.getCibleAnnonce() != null) {
                    System.out.println("    Cible: Annonce - " + s.getCibleAnnonce().getTitre());
                }
            }
        }
        pause();
    }
    
    private void surveillerPlateforme() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();
        
        System.out.println("\n SURVEILLANCE DE LA PLATEFORME");
        System.out.println("1.  Surveiller les annonces");
        System.out.println("2.  Surveiller les messages");
        System.out.println("3.  Surveiller les événements");
        System.out.println("4.  Retour");
        System.out.print(" Votre choix : ");
        
        int choix = lireEntier(1, 4);
        
        switch (choix) {
            case 1:
                admin.surveillerAnnonces(new ArrayList<>(plateforme.getAnnonces().values()));
                break;
            case 2:
                System.out.println(" Surveillance des messages en cours de développement...");
                break;
            case 3:
                System.out.println(" Surveillance des événements en cours de développement...");
                break;
        }
        pause();
    }
    
    // ============ MÉTHODES UTILITAIRES ============
    
    private int lireEntier(int min, int max) {
        while (true) {
            try {
                int valeur = Integer.parseInt(scanner.nextLine());
                if (valeur >= min && valeur <= max) {
                    return valeur;
                } else {
                    System.out.print(" Valeur invalide. Entrez un nombre entre " + min + " et " + max + " : ");
                }
            } catch (NumberFormatException e) {
                System.out.print(" Entrée invalide. Entrez un nombre : ");
            }
        }
    }
    
    private double lireDouble(double min, double max) {
        while (true) {
            try {
                double valeur = Double.parseDouble(scanner.nextLine());
                if (valeur >= min && valeur <= max) {
                    return valeur;
                } else {
                    System.out.print(" Valeur invalide. Entrez un nombre entre " + min + " et " + max + " : ");
                }
            } catch (NumberFormatException e) {
                System.out.print(" Entrée invalide. Entrez un nombre : ");
            }
        }
    }
    
    private void pause() {
        System.out.print("\n⏎ Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}