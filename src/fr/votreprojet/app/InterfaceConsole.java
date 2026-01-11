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
        effacerEcran();
        afficherEnTete("PLATEFORME COLLABORATIVE CAMPUS");
        System.out.println();
        afficherMessageInfo("Bienvenue sur la plateforme collaborative étudiante !");
        System.out.println();

        while (enExecution) {
            if (plateforme.getUtilisateurConnecte() == null) {
                afficherMenuConnexion();
            } else {
                afficherMenuPrincipal();
            }
        }

        scanner.close();
        afficherMessageSucces("\nMerci d'avoir utilisé notre plateforme ! À bientôt !");
    }

    private void afficherMenuConnexion() {
        System.out.println();
        afficherSousTitre("CONNEXION");
        System.out.println();

        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│                                        │");
        System.out.println("│  1.  Se connecter (utilisateurs existants)");
        System.out.println("│  2.  Créer un compte étudiant         │");
        System.out.println("│  3.  Mode démo (connexion automatique)│");
        System.out.println("│  4.  Quitter l'application            │");
        System.out.println("│                                        │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("\nVotre choix : ");

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

        System.out.println();
        afficherEnTete("MENU PRINCIPAL");
        afficherMessageInfo("Connecté en tant que : " + utilisateur.getNom() +
                " (" + utilisateur.getEmail() + ")");
        System.out.println();

        if (utilisateur instanceof Etudiant) {
            afficherMenuEtudiant();
        } else if (utilisateur instanceof Administrateur) {
            afficherMenuAdministrateur();
        }
    }

    private void afficherMenuEtudiant() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();

        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│          MENU ÉTUDIANT                 │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│                                        │");
        System.out.println("│  1.  Gérer mes annonces                │");
        System.out.println("│  2.  Rechercher des annonces           │");
        System.out.println("│  3.  Événements                       │");
        System.out.println("│  4.  Tontines                         │");
        System.out.println("│  5.  Messagerie                       │");
        System.out.println("│  6.  Évaluations                      │");
        System.out.println("│  7.  Signalements                     │");
        System.out.println("│  8.  Mon profil                       │");
        System.out.println("│  9.  Se déconnecter                   │");
        System.out.println("│  0.  Quitter l'application            │");
        System.out.println("│                                        │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("\nVotre choix : ");

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
                afficherMessageInfo("Déconnexion réussie.");
                pause();
                break;
            case 0:
                enExecution = false;
                break;
        }
    }

    private void afficherMenuAdministrateur() {
        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│        MENU ADMINISTRATEUR             │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│                                        │");
        System.out.println("│  1.  Gérer les utilisateurs            │");
        System.out.println("│  2.  Modérer les annonces              │");
        System.out.println("│  3.  Traiter les signalements          │");
        System.out.println("│  4.  Générer un rapport                │");
        System.out.println("│  5.  Surveillance                      │");
        System.out.println("│  6.  Mon profil                        │");
        System.out.println("│  7.  Se déconnecter                    │");
        System.out.println("│  0.  Quitter l'application             │");
        System.out.println("│                                        │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("\nVotre choix : ");

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
                afficherMessageInfo("Déconnexion réussie.");
                pause();
                break;
            case 0:
                enExecution = false;
                break;
        }
    }

    // ============ MÉTHODES DE CONNEXION ============

    private void seConnecter() {
        System.out.println();
        afficherSousTitre("CONNEXION");
        System.out.println();

        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Nom   : ");
        String nom = scanner.nextLine();

        if (plateforme.connecter(email, nom)) {
            afficherMessageSucces("Connexion réussie !");
        } else {
            afficherMessageErreur("Échec de connexion. Vérifiez vos identifiants.");
        }
        pause();
    }

    private void creerCompteEtudiant() {
        System.out.println();
        afficherSousTitre("CRÉATION DE COMPTE ÉTUDIANT");
        System.out.println();

        System.out.print("Email        : ");
        String email = scanner.nextLine();
        System.out.print("Nom          : ");
        String nom = scanner.nextLine();

        FabriqueEtudiant fabrique = new FabriqueEtudiant();
        Etudiant nouvelEtudiant = (Etudiant) fabrique.creerUtilisateur(email, nom);

        System.out.print("Campus       : ");
        nouvelEtudiant.setCampus(scanner.nextLine());
        System.out.print("Filière      : ");
        nouvelEtudiant.setFiliere(scanner.nextLine());
        System.out.print("Année d'étude : ");
        nouvelEtudiant.setAnneeEtude(lireEntier(1, 5));

        plateforme.ajouterUtilisateur(nouvelEtudiant);
        afficherMessageSucces("Compte créé avec succès !");

        // Se connecter automatiquement
        plateforme.connecter(email, nom);
        pause();
    }

    private void modeDemo() {
        System.out.println();
        afficherSousTitre("MODE DÉMO");
        System.out.println();

        System.out.println("1. Se connecter en tant qu'étudiant");
        System.out.println("2. Se connecter en tant qu'administrateur");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 2);

        if (choix == 1) {
            plateforme.connecter("jean@univ_smb.fr", "Jean Dupont");
        } else {
            plateforme.connecter("admin@univ_smb.fr", "Admin System");
        }
        afficherMessageInfo("Connexion en mode démo...");
        pause();
    }

    // ============ MÉTHODES ÉTUDIANT ============

    private void gererAnnoncesEtudiant(Etudiant etudiant) {
        System.out.println();
        afficherSousTitre("GESTION DES ANNONCES");
        System.out.println();

        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│                                        │");
        System.out.println("│  1.  Créer une nouvelle annonce        │");
        System.out.println("│  2.  Voir mes annonces                 │");
        System.out.println("│  3.  Modifier une annonce              │");
        System.out.println("│  4.  Supprimer une annonce             │");
        System.out.println("│  5.  Retour au menu principal          │");
        System.out.println("│                                        │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("\nVotre choix : ");

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

    private void modifierAnnonce(Etudiant etudiant) {
        System.out.println();
        afficherSousTitre("MODIFICATION D'ANNONCE");
        System.out.println();

        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);

        if (mesAnnonces.isEmpty()) {
            afficherMessageInfo("Vous n'avez pas d'annonces à modifier.");
            pause();
            return;
        }

        System.out.println("Vos annonces :");
        System.out.println("┌───┬────────────────────────────────────┐");
        for (int i = 0; i < mesAnnonces.size(); i++) {
            String statut = mesAnnonces.get(i).getStatut().getLibelle();
            System.out.printf("│ %d │ %-30s │ %-8s │\n",
                    i + 1,
                    mesAnnonces.get(i).getTitre().substring(0, Math.min(30, mesAnnonces.get(i).getTitre().length())),
                    statut);
        }
        System.out.println("└───┴────────────────────────────────────┘");

        System.out.print("\nChoisir une annonce à modifier (0 pour annuler) : ");
        int choix = lireEntier(0, mesAnnonces.size());

        if (choix > 0) {
            Annonce annonce = mesAnnonces.get(choix - 1);
            System.out.println("\nAnnonce sélectionnée : " + annonce.getTitre());
            System.out.println("────────────────────────────────────────────");

            System.out.print("Nouveau titre : ");
            String nouveauTitre = scanner.nextLine();
            System.out.print("Nouvelle description : ");
            String nouvelleDescription = scanner.nextLine();

            annonce.modifier(nouveauTitre, nouvelleDescription);
            afficherMessageSucces("Annonce modifiée avec succès !");
        }
        pause();
    }

    private void supprimerAnnonce(Etudiant etudiant) {
        System.out.println();
        afficherSousTitre("SUPPRESSION D'ANNONCE");
        System.out.println();

        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);

        if (mesAnnonces.isEmpty()) {
            afficherMessageInfo("Vous n'avez pas d'annonces à supprimer.");
            pause();
            return;
        }

        System.out.println("Vos annonces :");
        System.out.println("┌───┬────────────────────────────────────┐");
        for (int i = 0; i < mesAnnonces.size(); i++) {
            System.out.printf("│ %d │ %-34s │\n",
                    i + 1,
                    mesAnnonces.get(i).getTitre().substring(0, Math.min(34, mesAnnonces.get(i).getTitre().length())));
        }
        System.out.println("└───┴────────────────────────────────────┘");

        System.out.print("\nChoisir une annonce à supprimer (0 pour annuler) : ");
        int choix = lireEntier(0, mesAnnonces.size());

        if (choix > 0) {
            Annonce annonce = mesAnnonces.get(choix - 1);
            System.out.print("Êtes-vous sûr de vouloir supprimer l'annonce \"" + annonce.getTitre() + "\" ? (o/n) : ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("o")) {
                annonce.supprimer();
                plateforme.getAnnonces().remove(annonce.getIdLong());
                afficherMessageSucces("Annonce supprimée avec succès !");
            } else {
                afficherMessageInfo("Suppression annulée.");
            }
        }
        pause();
    }

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
        System.out.println();
        afficherSousTitre("CRÉATION D'ANNONCE");
        System.out.println();

        System.out.print("Titre        : ");
        String titre = scanner.nextLine();
        System.out.print("Description  : ");
        String description = scanner.nextLine();

        System.out.println("\nCatégories disponibles :");
        System.out.println("┌───┬────────────────────────────┐");
        for (int i = 0; i < Categorie.values().length; i++) {
            System.out.printf("│ %d │ %-26s │\n", i + 1, Categorie.values()[i].getLibelle());
        }
        System.out.println("└───┴────────────────────────────┘");
        System.out.print("Choisir une catégorie : ");
        int catChoix = lireEntier(1, Categorie.values().length);
        Categorie categorie = Categorie.values()[catChoix - 1];

        System.out.println("\nTypes d'échange :");
        System.out.println("┌───┬────────────────────────────┐");
        for (int i = 0; i < TypeEchange.values().length; i++) {
            System.out.printf("│ %d │ %-26s │\n", i + 1, TypeEchange.values()[i].getLibelle());
        }
        System.out.println("└───┴────────────────────────────┘");
        System.out.print("Choisir un type : ");
        int typeChoix = lireEntier(1, TypeEchange.values().length);
        TypeEchange typeEchange = TypeEchange.values()[typeChoix - 1];

        System.out.print("Prix (0 pour gratuit) : ");
        double prix = lireDouble(0, Double.MAX_VALUE);

        Annonce annonce = etudiant.creerAnnonce(titre, description, categorie, typeEchange, prix);
        annonce.publier();
        plateforme.ajouterAnnonce(annonce);

        afficherMessageSucces("Annonce créée et publiée avec succès !");
        pause();
    }

    private void afficherAnnoncesEtudiant(Etudiant etudiant) {
        System.out.println();
        afficherSousTitre("MES ANNONCES");
        System.out.println();

        List<Annonce> mesAnnonces = getAnnoncesEtudiant(etudiant);

        if (mesAnnonces.isEmpty()) {
            afficherMessageInfo("Vous n'avez pas encore créé d'annonces.");
        } else {
            System.out.println("Nombre d'annonces : " + mesAnnonces.size());
            System.out.println("┌───┬────────────────────────────────────┬────────────┐");
            System.out.println("│ # │ Titre                              │ Statut     │");
            System.out.println("├───┼────────────────────────────────────┼────────────┤");

            for (int i = 0; i < mesAnnonces.size(); i++) {
                Annonce annonce = mesAnnonces.get(i);
                String titre = annonce.getTitre().length() > 30 ? annonce.getTitre().substring(0, 27) + "..."
                        : annonce.getTitre();
                String statut = annonce.getStatut().getLibelle();

                System.out.printf("│ %d │ %-34s │ %-10s │\n",
                        i + 1, titre, statut);
            }
            System.out.println("└───┴────────────────────────────────────┴────────────┘");

            System.out.print("\nVoir les détails d'une annonce (0 pour retour) : ");
            int choix = lireEntier(0, mesAnnonces.size());

            if (choix > 0) {
                System.out.println("\n" + "─".repeat(50));
                mesAnnonces.get(choix - 1).afficherDetails();
                System.out.println("─".repeat(50));
            }
        }
        pause();
    }

    private void rechercherAnnonces() {
        System.out.println();
        afficherSousTitre("RECHERCHE D'ANNONCES");
        System.out.println();

        System.out.println("1.  Recherche simple");
        System.out.println("2.  Recherche avancée");
        System.out.println("3.  Recherche par proximité");
        System.out.println("4.  Retour");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 4);

        if (choix == 4)
            return;

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
            afficherMessageInfo("Aucune annonce trouvée.");
        } else {
            System.out.println("\n" + "─".repeat(60));
            System.out.println("RÉSULTATS DE RECHERCHE (" + resultats.size() + " annonce(s) trouvée(s))");
            System.out.println("─".repeat(60));

            System.out.println("\n┌───┬────────────────────────────────────┬────────────┬─────────┐");
            System.out.println("│ # │ Titre                              │ Localisation│ Prix    │");
            System.out.println("├───┼────────────────────────────────────┼────────────┼─────────┤");

            for (int i = 0; i < resultats.size(); i++) {
                Annonce annonce = resultats.get(i);
                String titre = annonce.getTitre().length() > 30 ? annonce.getTitre().substring(0, 27) + "..."
                        : annonce.getTitre();
                String localisation = annonce.getLocalisation();
                if (localisation.length() > 10) {
                    localisation = localisation.substring(0, 7) + "...";
                }
                String prix = annonce.getPrix() != null ? String.format("%.2f€", annonce.getPrix()) : "Gratuit";

                System.out.printf("│ %d │ %-34s │ %-10s │ %-7s │\n",
                        i + 1, titre, localisation, prix);
            }
            System.out.println("└───┴────────────────────────────────────┴────────────┴─────────┘");

            System.out.print("\nVoir les détails d'une annonce (0 pour retour) : ");
            int detailChoix = lireEntier(0, resultats.size());

            if (detailChoix > 0) {
                System.out.println("\n" + "═".repeat(60));
                resultats.get(detailChoix - 1).afficherDetails();
                System.out.println("═".repeat(60));

                if (plateforme.estEtudiantConnecte()) {
                    System.out.println("\nOptions disponibles :");
                    System.out.println("1.  Contacter le vendeur");
                    System.out.println("2.  Signaler l'annonce");
                    System.out.println("3.  Retour");
                    System.out.print("\nVotre choix : ");
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
        System.out.println();
        afficherSousTitre("GESTION DES ÉVÉNEMENTS");
        System.out.println();

        System.out.println("1.  Créer un événement");
        System.out.println("2.  Voir les événements à venir");
        System.out.println("3.  S'inscrire à un événement");
        System.out.println("4.  Retour");
        System.out.print("\nVotre choix : ");

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

        System.out.println();
        afficherSousTitre("CRÉATION D'ÉVÉNEMENT");
        System.out.println();

        System.out.print("Titre               : ");
        String titre = scanner.nextLine();
        System.out.print("Description         : ");
        String description = scanner.nextLine();
        System.out.print("Lieu                : ");
        String lieu = scanner.nextLine();
        System.out.print("Capacité maximale   : ");
        int capacite = lireEntier(1, 1000);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date date = cal.getTime();

        Evenement evenement = etudiant.creerEvenement(titre, description, date, lieu, capacite);
        evenement.creer();
        plateforme.ajouterEvenement(evenement);

        afficherMessageSucces("Événement créé avec succès !");
        pause();
    }

    private void afficherEvenements() {
        List<Evenement> evenements = plateforme.rechercherEvenements();

        if (evenements.isEmpty()) {
            afficherMessageInfo("Aucun événement à venir.");
        } else {
            System.out.println();
            afficherSousTitre("ÉVÉNEMENTS À VENIR");
            System.out.println("\nNombre d'événements : " + evenements.size());
            System.out.println("┌───┬────────────────────────────────────┬────────────┬─────────────┐");
            System.out.println("│ # │ Titre                              │ Lieu       │ Participants│");
            System.out.println("├───┼────────────────────────────────────┼────────────┼─────────────┤");

            for (int i = 0; i < evenements.size(); i++) {
                Evenement event = evenements.get(i);
                String titre = event.getTitre().length() > 30 ? event.getTitre().substring(0, 27) + "..."
                        : event.getTitre();
                String lieu = event.getLieu();
                if (lieu.length() > 10) {
                    lieu = lieu.substring(0, 7) + "...";
                }

                System.out.printf("│ %d │ %-34s │ %-10s │ %-4d/%4d │\n",
                        i + 1, titre, lieu, event.getParticipants().size(), event.getCapaciteMax());
            }
            System.out.println("└───┴────────────────────────────────────┴────────────┴─────────────┘");

            System.out.print("\nVoir les détails d'un événement (0 pour retour) : ");
            int choix = lireEntier(0, evenements.size());

            if (choix > 0) {
                System.out.println("\n" + "═".repeat(60));
                evenements.get(choix - 1).afficherDetails();
                System.out.println("═".repeat(60));
            }
        }
        pause();
    }

    // ============ MÉTHODES ADMINISTRATEUR ============

    private void gererUtilisateurs() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("GESTION DES UTILISATEURS");
        System.out.println();

        List<Utilisateur> utilisateurs = new ArrayList<>(plateforme.getUtilisateurs().values());

        System.out.println("Liste des utilisateurs (" + utilisateurs.size() + ") :");
        System.out.println("┌───┬────────────────────────────────────┬──────────────────────┬──────────┬─────────┐");
        System.out.println("│ # │ Nom                                │ Email                │ Type     │ Vérifié │");
        System.out.println("├───┼────────────────────────────────────┼──────────────────────┼──────────┼─────────┤");

        for (int i = 0; i < utilisateurs.size(); i++) {
            Utilisateur user = utilisateurs.get(i);
            String type = user instanceof Etudiant ? "Étudiant" : "Admin";
            String nom = user.getNom().length() > 30 ? user.getNom().substring(0, 27) + "..." : user.getNom();
            String email = user.getEmail().length() > 20 ? user.getEmail().substring(0, 17) + "..." : user.getEmail();

            System.out.printf("│ %d │ %-34s │ %-20s │ %-8s │   %s    │\n",
                    i + 1, nom, email, type, user.getVerifie() ? "✓" : "✗");
        }
        System.out.println("└───┴────────────────────────────────────┴──────────────────────┴──────────┴─────────┘");

        System.out.print("\nSélectionner un utilisateur (0 pour retour) : ");
        int choix = lireEntier(0, utilisateurs.size());

        if (choix > 0) {
            Utilisateur selected = utilisateurs.get(choix - 1);
            System.out.println("\n" + "─".repeat(50));
            System.out.println("Actions disponibles pour : " + selected.getNom());
            System.out.println("─".repeat(50));
            System.out.println("1.  Valider le compte");
            System.out.println("2.  Voir le profil détaillé");
            System.out.println("3.  Retour");
            System.out.print("\nVotre choix : ");

            int action = lireEntier(1, 3);

            if (action == 1) {
                admin.validerUtilisateur(selected);
                afficherMessageSucces("Compte validé avec succès !");
            } else if (action == 2) {
                System.out.println("\n" + "═".repeat(50));
                selected.afficherProfil();
                System.out.println("═".repeat(50));
            }
        }
        pause();
    }

    private void modererAnnonces() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("MODÉRATION DES ANNONCES");
        System.out.println();

        List<Annonce> annonces = new ArrayList<>(plateforme.getAnnonces().values());

        // Filtrer les annonces signalées ou à modérer
        List<Annonce> annoncesAModerer = new ArrayList<>();
        for (Annonce annonce : annonces) {
            if (annonce.getStatut() == StatutAnnonce.SIGNALÉE || annonce.getSignalements().size() > 0) {
                annoncesAModerer.add(annonce);
            }
        }

        if (annoncesAModerer.isEmpty()) {
            afficherMessageInfo("Aucune annonce à modérer pour le moment.");
        } else {
            System.out.println("Annonces à modérer (" + annoncesAModerer.size() + ") :");
            System.out.println(
                    "┌───┬────────────────────────────────────┬──────────────────────┬────────────┬──────────────┐");
            System.out.println(
                    "│ # │ Titre                              │ Créateur             │ Signalements │ Statut      │");
            System.out.println(
                    "├───┼────────────────────────────────────┼──────────────────────┼─────────────┼──────────────┤");

            for (int i = 0; i < annoncesAModerer.size(); i++) {
                Annonce annonce = annoncesAModerer.get(i);
                String titre = annonce.getTitre().length() > 30 ? annonce.getTitre().substring(0, 27) + "..."
                        : annonce.getTitre();
                String createur = annonce.getCreateur().getNom();
                if (createur.length() > 20) {
                    createur = createur.substring(0, 17) + "...";
                }

                System.out.printf("│ %d │ %-34s │ %-20s │     %-6d │ %-12s │\n",
                        i + 1, titre, createur, annonce.getSignalements().size(),
                        annonce.getStatut().getLibelle());
            }
            System.out.println(
                    "└───┴────────────────────────────────────┴──────────────────────┴─────────────┴──────────────┘");

            System.out.print("\nSélectionner une annonce à modérer (0 pour retour) : ");
            int choix = lireEntier(0, annoncesAModerer.size());

            if (choix > 0) {
                Annonce annonce = annoncesAModerer.get(choix - 1);
                System.out.println("\n" + "═".repeat(60));
                annonce.afficherDetails();
                System.out.println("═".repeat(60));
                admin.modererAnnonce(annonce);
            }
        }
        pause();
    }

    private void traiterSignalements() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("TRAITEMENT DES SIGNALEMENTS");
        System.out.println();

        List<Signalement> signalementsEnAttente = plateforme.getSignalementsEnAttente();

        if (signalementsEnAttente.isEmpty()) {
            afficherMessageInfo("Aucun signalement en attente de traitement.");
        } else {
            System.out.println("Signalements en attente (" + signalementsEnAttente.size() + ") :");
            System.out.println(
                    "┌───┬────────────┬────────────────────────────────────┬──────────────────────┬──────────────┐");
            System.out.println(
                    "│ # │ ID         │ Type                               │ Signaleur            │ Date         │");
            System.out.println(
                    "├───┼────────────┼────────────────────────────────────┼──────────────────────┼──────────────┤");

            for (int i = 0; i < signalementsEnAttente.size(); i++) {
                Signalement signalement = signalementsEnAttente.get(i);
                String type = signalement.getType().getLibelle();
                if (type.length() > 30) {
                    type = type.substring(0, 27) + "...";
                }
                String signaleur = signalement.getSignaleur().getNom();
                if (signaleur.length() > 20) {
                    signaleur = signaleur.substring(0, 17) + "...";
                }

                System.out.printf("│ %d │ %-10d │ %-34s │ %-20s │ %-12s │\n",
                        i + 1, signalement.getIdLong(), type, signaleur,
                        signalement.getDateSignalement());
            }
            System.out.println(
                    "└───┴────────────┴────────────────────────────────────┴──────────────────────┴──────────────┘");

            System.out.print("\nSélectionner un signalement à traiter (0 pour retour) : ");
            int choix = lireEntier(0, signalementsEnAttente.size());

            if (choix > 0) {
                Signalement signalement = signalementsEnAttente.get(choix - 1);
                System.out.println("\n" + "═".repeat(60));
                signalement.afficherDetails();
                System.out.println("═".repeat(60));
                admin.traiterSignalement(signalement);
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine(); 
            }
        }
        pause();
    }

    private void genererRapport() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("GÉNÉRATION DE RAPPORT");
        System.out.println();

        admin.genererRapport();

        // Statistiques supplémentaires
        System.out.println("\n" + "═".repeat(50));
        System.out.println("STATISTIQUES DE LA PLATEFORME");
        System.out.println("═".repeat(50));
        System.out.println("┌──────────────────────┬──────────────────────┐");
        System.out.println("│ Métrique             │ Valeur               │");
        System.out.println("├──────────────────────┼──────────────────────┤");
        System.out.printf("│ Utilisateurs         │ %-20d │\n", plateforme.getUtilisateurs().size());
        System.out.printf("│ Annonces             │ %-20d │\n", plateforme.getAnnonces().size());
        System.out.printf("│ Événements           │ %-20d │\n", plateforme.getEvenements().size());
        System.out.printf("│ Tontines             │ %-20d │\n", plateforme.getTontines().size());
        System.out.printf("│ Signalements         │ %-20d │\n", plateforme.getSignalements().size());
        System.out.println("└──────────────────────┴──────────────────────┘");

        pause();
    }

    // ============ MÉTHODES UTILITAIRES ============

    private void contacterVendeur(Annonce annonce) {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("CONTACTER LE VENDEUR");
        System.out.println();

        System.out.println("Destinataire : " + annonce.getCreateur().getNom());
        System.out.println("Sujet : À propos de votre annonce \"" + annonce.getTitre() + "\"");
        System.out.println("\nVotre message :");
        System.out.print("> ");
        String message = scanner.nextLine();

        Message msg = etudiant.envoyerMessage(annonce.getCreateur(),
                "À propos de votre annonce \"" + annonce.getTitre() + "\" : " + message);
        plateforme.ajouterMessage(msg);

        afficherMessageSucces("Message envoyé avec succès !");
        pause();
    }

    private void signalerAnnonce(Annonce annonce) {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("SIGNALER UNE ANNONCE");
        System.out.println();

        System.out.println("Annonce concernée : " + annonce.getTitre());
        System.out.println("Créateur : " + annonce.getCreateur().getNom());
        System.out.println("\nTypes de signalement disponibles :");
        System.out.println("┌───┬────────────────────────────────────┐");
        for (int i = 0; i < TypeSignalement.values().length; i++) {
            System.out.printf("│ %d │ %-34s │\n", i + 1, TypeSignalement.values()[i].getLibelle());
        }
        System.out.println("└───┴────────────────────────────────────┘");
        System.out.print("\nChoisir un type : ");
        int typeChoix = lireEntier(1, TypeSignalement.values().length);
        TypeSignalement type = TypeSignalement.values()[typeChoix - 1];

        System.out.print("\nDescription du problème : ");
        String description = scanner.nextLine();

        Signalement signalement = etudiant.signalerAnnonce(annonce, type, description);
        signalement.creer();
        plateforme.ajouterSignalement(signalement);

        afficherMessageSucces("Annonce signalée avec succès !");
        pause();
    }

    private void sinscrireEvenement() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();

        List<Evenement> evenements = plateforme.rechercherEvenements();
        if (evenements.isEmpty()) {
            afficherMessageInfo("Aucun événement disponible pour l'inscription.");
            pause();
            return;
        }

        System.out.println();
        afficherSousTitre("INSCRIPTION À UN ÉVÉNEMENT");
        System.out.println();

        System.out.println("Événements disponibles :");
        System.out.println("┌───┬────────────────────────────────────┬────────────┬─────────────┐");
        System.out.println("│ # │ Titre                              │ Lieu       │ Places dispo│");
        System.out.println("├───┼────────────────────────────────────┼────────────┼─────────────┤");

        for (int i = 0; i < evenements.size(); i++) {
            Evenement event = evenements.get(i);
            String titre = event.getTitre().length() > 30 ? event.getTitre().substring(0, 27) + "..."
                    : event.getTitre();
            String lieu = event.getLieu();
            if (lieu.length() > 10) {
                lieu = lieu.substring(0, 7) + "...";
            }
            int placesDispo = event.getCapaciteMax() - event.getParticipants().size();

            System.out.printf("│ %d │ %-34s │ %-10s │    %-7d │\n",
                    i + 1, titre, lieu, placesDispo);
        }
        System.out.println("└───┴────────────────────────────────────┴────────────┴─────────────┘");

        System.out.print("\nChoisir un événement (0 pour retour) : ");
        int choix = lireEntier(0, evenements.size());

        if (choix > 0) {
            Evenement selected = evenements.get(choix - 1);
            etudiant.sinscrireEvenement(selected);
            afficherMessageSucces("Inscription réussie à l'événement : " + selected.getTitre());
        }
        pause();
    }

    private void gererTontines() {
        System.out.println();
        afficherSousTitre("GESTION DES TONTINES");
        System.out.println();

        System.out.println("1.  Créer une tontine");
        System.out.println("2.  Voir les tontines disponibles");
        System.out.println("3.  Rejoindre une tontine");
        System.out.println("4.  Retour");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 4);

        if (choix == 1) {
            creerTontine();
        } else if (choix == 2 || choix == 3) {
            afficherTontines(choix == 3);
        }
    }

    private void creerTontine() {
        Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("CRÉATION DE TONTINE");
        System.out.println();

        System.out.print("Nom de la tontine   : ");
        String nom = scanner.nextLine();
        System.out.print("Montant mensuel (€) : ");
        double montant = lireDouble(1, 1000);

        Tontine tontine = etudiant.creerTontine(nom, montant);
        tontine.creer();
        plateforme.ajouterTontine(tontine);

        afficherMessageSucces("Tontine créée avec succès !");
        pause();
    }

    private void afficherTontines(boolean pourRejoindre) {
        List<Tontine> tontines = plateforme.rechercherTontines();

        if (tontines.isEmpty()) {
            afficherMessageInfo("Aucune tontine disponible.");
        } else {
            System.out.println();
            afficherSousTitre(pourRejoindre ? "REJOINDRE UNE TONTINE" : "TONTINES DISPONIBLES");
            System.out.println("\nNombre de tontines : " + tontines.size());
            System.out.println("┌───┬────────────────────────────────────┬────────────┬─────────────┬──────────┐");
            System.out.println("│ # │ Nom                                │ Montant/m  │ Participants│ Statut   │");
            System.out.println("├───┼────────────────────────────────────┼────────────┼─────────────┼──────────┤");

            for (int i = 0; i < tontines.size(); i++) {
                Tontine tontine = tontines.get(i);
                String nom = tontine.getNom().length() > 30 ? tontine.getNom().substring(0, 27) + "..."
                        : tontine.getNom();

                System.out.printf("│ %d │ %-34s │ %-10.2f │    %-6d │ %-8s │\n",
                        i + 1, nom, tontine.getMontantMensuel(),
                        tontine.getParticipants().size(), tontine.getStatut().getLibelle());
            }
            System.out.println("└───┴────────────────────────────────────┴────────────┴─────────────┴──────────┘");

            if (pourRejoindre) {
                System.out.print("\nRejoindre une tontine (0 pour retour) : ");
                int choix = lireEntier(0, tontines.size());

                if (choix > 0) {
                    Etudiant etudiant = (Etudiant) plateforme.getUtilisateurConnecte();
                    etudiant.rejoindreTontine(tontines.get(choix - 1));
                    afficherMessageSucces("Vous avez rejoint la tontine : " + tontines.get(choix - 1).getNom());
                }
            } else {
                System.out.print("\nVoir les détails d'une tontine (0 pour retour) : ");
                int choix = lireEntier(0, tontines.size());

                if (choix > 0) {
                    System.out.println("\n" + "═".repeat(60));
                    tontines.get(choix - 1).afficherDetails();
                    System.out.println("═".repeat(60));
                }
            }
        }
        pause();
    }

    private void gererMessagerie() {
        System.out.println();
        afficherSousTitre("MESSAGERIE");
        System.out.println();

        System.out.println("1.  Envoyer un message");
        System.out.println("2.  Voir mes messages");
        System.out.println("3.  Retour");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 3);

        // Cette méthode serait implémentée avec une vraie base de données de messages
        afficherMessageInfo("Fonctionnalité de messagerie en cours de développement...");
        pause();
    }

    private void gererEvaluations() {
        System.out.println();
        afficherSousTitre("ÉVALUATIONS");
        System.out.println();

        System.out.println("1.  Évaluer un utilisateur");
        System.out.println("2.  Voir mes évaluations");
        System.out.println("3.  Retour");
        System.out.print("\nVotre choix : ");

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
            afficherMessageInfo("Aucun autre utilisateur à évaluer.");
            pause();
            return;
        }

        System.out.println();
        afficherSousTitre("ÉVALUER UN UTILISATEUR");
        System.out.println();

        System.out.println("Utilisateurs disponibles :");
        System.out.println("┌───┬────────────────────────────────────┬──────────────────────┐");
        System.out.println("│ # │ Nom                                │ Email                │");
        System.out.println("├───┼────────────────────────────────────┼──────────────────────┤");

        for (int i = 0; i < autresUtilisateurs.size(); i++) {
            Utilisateur user = autresUtilisateurs.get(i);
            String nom = user.getNom().length() > 30 ? user.getNom().substring(0, 27) + "..." : user.getNom();
            String email = user.getEmail().length() > 20 ? user.getEmail().substring(0, 17) + "..." : user.getEmail();

            System.out.printf("│ %d │ %-34s │ %-20s │\n", i + 1, nom, email);
        }
        System.out.println("└───┴────────────────────────────────────┴──────────────────────┘");

        System.out.print("\nChoisir un utilisateur (0 pour retour) : ");
        int choix = lireEntier(0, autresUtilisateurs.size());

        if (choix > 0) {
            Etudiant evalue = (Etudiant) autresUtilisateurs.get(choix - 1);
            System.out.print("\nNote (1-5 étoiles) : ");
            int note = lireEntier(1, 5);
            System.out.print("Commentaire : ");
            String commentaire = scanner.nextLine();

            Evaluation evaluation = etudiant.noterUtilisateur(evalue, note, commentaire);
            evaluation.publier();
            plateforme.ajouterEvaluation(evaluation);

            afficherMessageSucces("Évaluation ajoutée avec succès !");
        }
        pause();
    }

    private void afficherEvaluations() {
        System.out.println();
        afficherSousTitre("MES ÉVALUATIONS");
        afficherMessageInfo("Fonctionnalité en cours de développement...");
        pause();
    }

    private void gererSignalementsEtudiant() {
        System.out.println();
        afficherSousTitre("GESTION DES SIGNALEMENTS");
        System.out.println();

        System.out.println("1.  Signaler une annonce");
        System.out.println("2.  Signaler un message");
        System.out.println("3.  Voir mes signalements");
        System.out.println("4.  Retour");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 4);

        switch (choix) {
            case 1:
                signalerAnnonceManuelle();
                break;
            case 2:
                afficherMessageInfo("Fonctionnalité en cours de développement...");
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
            afficherMessageInfo("Aucune annonce à signaler.");
            pause();
            return;
        }

        System.out.println();
        afficherSousTitre("SIGNALER UNE ANNONCE");
        System.out.println();

        System.out.println("Annonces disponibles :");
        System.out.println("┌───┬────────────────────────────────────┬──────────────────────┐");
        System.out.println("│ # │ Titre                              │ Créateur             │");
        System.out.println("├───┼────────────────────────────────────┼──────────────────────┤");

        for (int i = 0; i < annonces.size(); i++) {
            Annonce annonce = annonces.get(i);
            String titre = annonce.getTitre().length() > 30 ? annonce.getTitre().substring(0, 27) + "..."
                    : annonce.getTitre();
            String createur = annonce.getCreateur().getNom();
            if (createur.length() > 20) {
                createur = createur.substring(0, 17) + "...";
            }

            System.out.printf("│ %d │ %-34s │ %-20s │\n", i + 1, titre, createur);
        }
        System.out.println("└───┴────────────────────────────────────┴──────────────────────┘");

        System.out.print("\nChoisir une annonce (0 pour retour) : ");
        int choix = lireEntier(0, annonces.size());

        if (choix > 0) {
            Annonce annonce = annonces.get(choix - 1);

            System.out.println("\nTypes de signalement :");
            System.out.println("┌───┬────────────────────────────────────┐");
            for (int i = 0; i < TypeSignalement.values().length; i++) {
                System.out.printf("│ %d │ %-34s │\n", i + 1, TypeSignalement.values()[i].getLibelle());
            }
            System.out.println("└───┴────────────────────────────────────┘");
            System.out.print("\nChoisir un type : ");
            int typeChoix = lireEntier(1, TypeSignalement.values().length);
            TypeSignalement type = TypeSignalement.values()[typeChoix - 1];

            System.out.print("\nDescription du problème : ");
            String description = scanner.nextLine();

            Signalement signalement = etudiant.signalerAnnonce(annonce, type, description);
            signalement.creer();
            plateforme.ajouterSignalement(signalement);

            afficherMessageSucces("Signalement créé avec succès !");
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
            afficherMessageInfo("Vous n'avez créé aucun signalement.");
        } else {
            System.out.println();
            afficherSousTitre("MES SIGNALEMENTS");
            System.out.println("\nNombre de signalements : " + mesSignalements.size());
            System.out.println("┌───┬────────────┬────────────────────────────────────┬──────────────┬──────────┐");
            System.out.println("│ # │ ID         │ Type                               │ Date         │ Statut   │");
            System.out.println("├───┼────────────┼────────────────────────────────────┼──────────────┼──────────┤");

            for (int i = 0; i < mesSignalements.size(); i++) {
                Signalement s = mesSignalements.get(i);
                String type = s.getType().getLibelle();
                if (type.length() > 30) {
                    type = type.substring(0, 27) + "...";
                }

                System.out.printf("│ %d │ %-10d │ %-34s │ %-12s │ %-8s │\n",
                        i + 1, s.getIdLong(), type, s.getDateSignalement(), s.getStatut().getLibelle());
            }
            System.out.println("└───┴────────────┴────────────────────────────────────┴──────────────┴──────────┘");
        }
        pause();
    }

    private void surveillerPlateforme() {
        Administrateur admin = (Administrateur) plateforme.getUtilisateurConnecte();

        System.out.println();
        afficherSousTitre("SURVEILLANCE DE LA PLATEFORME");
        System.out.println();

        System.out.println("1.  Surveiller les annonces");
        System.out.println("2.  Surveiller les messages");
        System.out.println("3.  Surveiller les événements");
        System.out.println("4.  Retour");
        System.out.print("\nVotre choix : ");

        int choix = lireEntier(1, 4);

        switch (choix) {
            case 1:
                admin.surveillerAnnonces(new ArrayList<>(plateforme.getAnnonces().values()));
                break;
            case 2:
                afficherMessageInfo("Surveillance des messages en cours de développement...");
                break;
            case 3:
                afficherMessageInfo("Surveillance des événements en cours de développement...");
                break;
        }
        pause();
    }

    // ============ MÉTHODES UTILITAIRES POUR L'AFFICHAGE ============

    private void afficherEnTete(String titre) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-58s ║\n", titre);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private void afficherSousTitre(String sousTitre) {
        System.out.println("─".repeat(60));
        System.out.println(sousTitre);
        System.out.println("─".repeat(60));
    }

    private void afficherMessageInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    private void afficherMessageSucces(String message) {
        System.out.println("[SUCCÈS] " + message);
    }

    private void afficherMessageErreur(String message) {
        System.out.println("[ERREUR] " + message);
    }

    private void effacerEcran() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ============ MÉTHODES UTILITAIRES ============

    private int lireEntier(int min, int max) {
        while (true) {
            try {
                int valeur = Integer.parseInt(scanner.nextLine());
                if (valeur >= min && valeur <= max) {
                    return valeur;
                } else {
                    System.out.print("Valeur invalide. Entrez un nombre entre " + min + " et " + max + " : ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Entrez un nombre : ");
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
                    System.out.print("Valeur invalide. Entrez un nombre entre " + min + " et " + max + " : ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Entrez un nombre : ");
            }
        }
    }

    private void pause() {
        System.out.print("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}