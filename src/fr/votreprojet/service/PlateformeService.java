package fr.votreprojet.service;

import fr.votreprojet.modele.*;
import fr.votreprojet.util.*;
import java.util.*;

public class PlateformeService {
    private static PlateformeService instance;
    
    // TOUTES les variables nécessaires
    private Map<Long, Utilisateur> utilisateurs;
    private Map<Long, Annonce> annonces;
    private Map<Long, Message> messages;
    private Map<Long, Evenement> evenements;
    private Map<Long, Tontine> tontines;
    private Map<Long, Signalement> signalements;
    private Map<Long, Evaluation> evaluations;
    
    private Utilisateur utilisateurConnecte;
    
    private PlateformeService() {
        // INITIALISER TOUTES les Map
        this.utilisateurs = new HashMap<>();
        this.annonces = new HashMap<>();
        this.messages = new HashMap<>();
        this.evenements = new HashMap<>();
        this.tontines = new HashMap<>();
        this.signalements = new HashMap<>();
        this.evaluations = new HashMap<>();
        
        initialiserDonneesTest();
    }
    
    public static PlateformeService getInstance() {
        if (instance == null) {
            instance = new PlateformeService();
        }
        return instance;
    }
    
    private void initialiserDonneesTest() {
        System.out.println(" Initialisation des données de test...");
        
        // 1. Créer des utilisateurs de test
        FabriqueEtudiant fabriqueEtudiant = new FabriqueEtudiant();
        FabriqueAdministrateur fabriqueAdmin = new FabriqueAdministrateur();
        
        // Étudiant 1
        Etudiant etudiant1 = (Etudiant) fabriqueEtudiant.creerUtilisateur("steve@univ_smb.fr", "Steve Mboda");
        etudiant1.setCampus("Annecy");
        etudiant1.setFiliere("IDU");
        etudiant1.setAnneeEtude(4);
        etudiant1.setVerifie(true);
        ajouterUtilisateur(etudiant1);
        
        // Étudiant 2
        Etudiant etudiant2 = (Etudiant) fabriqueEtudiant.creerUtilisateur("kelly@univ_smb.fr", "kelly Anne");
        etudiant2.setCampus("Annecy");
        etudiant2.setFiliere("SNI");
        etudiant2.setAnneeEtude(3);
        etudiant2.setVerifie(true);
        ajouterUtilisateur(etudiant2);
        
        // Administrateur
        Administrateur admin = (Administrateur) fabriqueAdmin.creerUtilisateur("admin@univ_smb.fr", "Admin System");
        admin.setNiveauAcces(3);
        ajouterUtilisateur(admin);
        
        // 2. Créer des annonces de test
        Annonce annonce1 = etudiant1.creerAnnonce(
            "Livre Java Avancé",
            "Livre sur les design patterns en Java, comme neuf",
            Categorie.MATERIEL_SCOLAIRE,
            TypeEchange.VENTE,
            25.0
        );
        annonce1.publier();
        ajouterAnnonce(annonce1);
        
        Annonce annonce2 = etudiant2.creerAnnonce(
            "Cours de Maths",
            "Cours particuliers de mathématiques niveau licence",
            Categorie.SERVICES,
            TypeEchange.SERVICE,
            15.0
        );
        annonce2.publier();
        ajouterAnnonce(annonce2);
        
        // 3. Créer des messages de test (IMPORTANT pour la messagerie)
        Message message1 = etudiant1.envoyerMessage(
            etudiant2,
            "Bonjour Kelly, ton cours de maths m'intéresse !"
        );
        ajouterMessage(message1);
        
        Message message2 = etudiant2.envoyerMessage(
            etudiant1,
            "Salut Steve ! Oui, je suis disponible mercredi. Ton livre est toujours dispo ?"
        );
        ajouterMessage(message2);
        
        // Marquer le premier message comme lu pour le test
        message1.marquerCommeLu();
        
        // 4. Créer un événement de test
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Evenement evenement = etudiant1.creerEvenement(
            "Soirée de rentrée",
            "Soirée pour faire connaissance entre étudiants",
            cal.getTime(),
            "BDE Polytech, Annecy",
            50
        );
        evenement.creer();
        ajouterEvenement(evenement);
        
        // 5. Créer une tontine de test
        Tontine tontine = etudiant2.creerTontine(
            "Tontine Étudiants Annecy",
            50.0
        );
        tontine.creer();
        ajouterTontine(tontine);
        
        // 6. Créer un signalement de test
        Signalement signalement = etudiant1.signalerAnnonce(
            annonce2,
            TypeSignalement.CONTENU_OFFENSANT,
            "Prix trop élevé pour un service"
        );
        signalement.creer();
        ajouterSignalement(signalement);
        
        System.out.println(" Données de test initialisées avec succès !");
        System.out.println("   • Utilisateurs: " + utilisateurs.size());
        System.out.println("   • Annonces: " + annonces.size());
        System.out.println("   • Messages: " + messages.size());
        System.out.println("   • Événements: " + evenements.size());
        System.out.println("   • Tontines: " + tontines.size());
        System.out.println("   • Signalements: " + signalements.size());
    }
    
    // ============ MÉTHODES D'AJOUT ============
    
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null && utilisateur.getId() != null) {
            utilisateurs.put(utilisateur.getId(), utilisateur);
        }
    }
    
    public void ajouterAnnonce(Annonce annonce) {
        if (annonce != null && annonce.getIdLong() != null) {
            annonces.put(annonce.getIdLong(), annonce);
        }
    }
    
    public void ajouterMessage(Message message) {
        if (message != null && message.getIdLong() != null) {
            messages.put(message.getIdLong(), message);
        }
    }
    
    public void ajouterEvenement(Evenement evenement) {
        if (evenement != null && evenement.getIdLong() != null) {
            evenements.put(evenement.getIdLong(), evenement);
        }
    }
    
    public void ajouterTontine(Tontine tontine) {
        if (tontine != null && tontine.getIdLong() != null) {
            tontines.put(tontine.getIdLong(), tontine);
        }
    }
    
    public void ajouterSignalement(Signalement signalement) {
        if (signalement != null && signalement.getIdLong() != null) {
            signalements.put(signalement.getIdLong(), signalement);
        }
    }
    
    public void ajouterEvaluation(Evaluation evaluation) {
        if (evaluation != null && evaluation.getIdLong() != null) {
            evaluations.put(evaluation.getIdLong(), evaluation);
        }
    }
    
    // ============ MÉTHODES DE RECHERCHE ============
    
    public List<Annonce> rechercherAnnonces(Map<String, Object> criteres) {
        List<Annonce> resultats = new ArrayList<>();
        for (Annonce annonce : annonces.values()) {
            if (annonce.getStatut() == StatutAnnonce.PUBLIEE) {
                resultats.add(annonce);
            }
        }
        return resultats;
    }
    
    public List<Evenement> rechercherEvenements() {
        return new ArrayList<>(evenements.values());
    }
    
    public List<Tontine> rechercherTontines() {
        return new ArrayList<>(tontines.values());
    }
    
    public List<Signalement> getSignalementsEnAttente() {
        List<Signalement> enAttente = new ArrayList<>();
        for (Signalement signalement : signalements.values()) {
            if (signalement.getStatut() == StatutSignalement.EN_ATTENTE) {
                enAttente.add(signalement);
            }
        }
        return enAttente;
    }
    
    // ============ MÉTHODES D'AUTHENTIFICATION ============
    
    public boolean connecter(String email, String nom) {
        for (Utilisateur user : utilisateurs.values()) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.getNom().equalsIgnoreCase(nom.trim())) {
                utilisateurConnecte = user;
                System.out.println(" Connexion réussie : " + user.getNom() + " (" + 
                                 (user instanceof Etudiant ? "Étudiant" : "Administrateur") + ")");
                return true;
            }
        }
        System.out.println(" Échec de connexion : email ou nom incorrect");
        return false;
    }
    
    public void deconnecter() {
        if (utilisateurConnecte != null) {
            System.out.println(" Déconnexion de : " + utilisateurConnecte.getNom());
            utilisateurConnecte = null;
        }
    }
    
    // ============ GETTERS ============
    
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    public Map<Long, Utilisateur> getUtilisateurs() {
        return new HashMap<>(utilisateurs);
    }
    
    public Map<Long, Annonce> getAnnonces() {
        return new HashMap<>(annonces);
    }
    
    public Map<Long, Message> getMessages() {
        return new HashMap<>(messages);
    }
    
    public Map<Long, Evenement> getEvenements() {
        return new HashMap<>(evenements);
    }
    
    public Map<Long, Tontine> getTontines() {
        return new HashMap<>(tontines);
    }
    
    public Map<Long, Signalement> getSignalements() {
        return new HashMap<>(signalements);
    }
    
    public Map<Long, Evaluation> getEvaluations() {
        return new HashMap<>(evaluations);
    }
    
    // ============ MÉTHODES DE VÉRIFICATION ============
    
    public boolean estAdministrateurConnecte() {
        return utilisateurConnecte instanceof Administrateur;
    }
    
    public boolean estEtudiantConnecte() {
        return utilisateurConnecte instanceof Etudiant;
    }
    
    // ============ MÉTHODES UTILITAIRES ============
    
    public Etudiant getEtudiantParId(Long id) {
        Utilisateur user = utilisateurs.get(id);
        return (user instanceof Etudiant) ? (Etudiant) user : null;
    }
    
    public List<Etudiant> getTousLesEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        for (Utilisateur user : utilisateurs.values()) {
            if (user instanceof Etudiant) {
                etudiants.add((Etudiant) user);
            }
        }
        return etudiants;
    }
    
    public List<Message> getMessagesPourUtilisateur(Utilisateur user) {
        List<Message> messagesUtilisateur = new ArrayList<>();
        for (Message message : messages.values()) {
            if (message.getExpediteur().equals(user) || message.getDestinataire().equals(user)) {
                messagesUtilisateur.add(message);
            }
        }
        return messagesUtilisateur;
    }
    
    public List<Message> getConversation(Etudiant user1, Etudiant user2) {
        List<Message> conversation = new ArrayList<>();
        for (Message message : messages.values()) {
            if ((message.getExpediteur().equals(user1) && message.getDestinataire().equals(user2)) ||
                (message.getExpediteur().equals(user2) && message.getDestinataire().equals(user1))) {
                conversation.add(message);
            }
        }
        // Trier par date
        conversation.sort(Comparator.comparing(Message::getDateEnvoi));
        return conversation;
    }
    
    // ============ STATISTIQUES ============
    
    public void afficherStatistiques() {
        System.out.println("\n STATISTIQUES DE LA PLATEFORME");
        System.out.println("════════════════════════════════");
        System.out.println(" Utilisateurs : " + utilisateurs.size());
        System.out.println("   • Étudiants : " + getTousLesEtudiants().size());
        System.out.println("   • Administrateurs : " + (utilisateurs.size() - getTousLesEtudiants().size()));
        System.out.println(" Annonces : " + annonces.size());
        System.out.println(" Messages : " + messages.size());
        System.out.println(" Événements : " + evenements.size());
        System.out.println(" Tontines : " + tontines.size());
        System.out.println(" Signalements : " + signalements.size());
        System.out.println(" Évaluations : " + evaluations.size());
        System.out.println("════════════════════════════════");
    }
    
    // ============ RECHERCHE AVANCÉE ============
    
    public List<Annonce> rechercherAnnoncesParCategorie(Categorie categorie) {
        List<Annonce> resultats = new ArrayList<>();
        for (Annonce annonce : annonces.values()) {
            if (annonce.getCategorie() == categorie && 
                annonce.getStatut() == StatutAnnonce.PUBLIEE) {
                resultats.add(annonce);
            }
        }
        return resultats;
    }
    
    public List<Annonce> rechercherAnnoncesParCreateur(Etudiant createur) {
        List<Annonce> resultats = new ArrayList<>();
        for (Annonce annonce : annonces.values()) {
            if (annonce.getCreateur().equals(createur)) {
                resultats.add(annonce);
            }
        }
        return resultats;
    }
    
    // ============ TEST DU PATTERN OBSERVER ============
    
    public void testerPatternObserver() {
        System.out.println("\n TEST DU PATTERN OBSERVER");
        System.out.println("══════════════════════════════");
        
        // Récupérer les utilisateurs de test
        Etudiant steve = null;
        Etudiant kelly = null;
        
        for (Utilisateur user : utilisateurs.values()) {
            if (user.getNom().equals("Steve Mboda")) steve = (Etudiant) user;
            if (user.getNom().equals("Kelly Anne")) kelly = (Etudiant) user;
        }
        
        if (steve == null || kelly == null) {
            System.out.println(" Utilisateurs de test non trouvés");
            return;
        }
        
        // Créer une nouvelle annonce pour le test
        Annonce annonceTest = steve.creerAnnonce(
            "TEST Observer Pattern",
            "Annonce créée pour tester le pattern Observer",
            Categorie.MATERIEL_SCOLAIRE,
            TypeEchange.DON,
            0.0
        );
        
        System.out.println("\n1. Steve crée une annonce (s'abonne automatiquement)");
        annonceTest.attacher(steve);
        
        System.out.println("2. Kelly s'abonne à l'annonce");
        annonceTest.attacher(kelly);
        
        System.out.println("3. Steve publie l'annonce → Notifications envoyées");
        annonceTest.publier();
        
        System.out.println("\n Test du pattern Observer terminé !");
        System.out.println("   Observateurs sur l'annonce : " + annonceTest.countObservateurs());
        
        // Nettoyer
        annonces.remove(annonceTest.getIdLong());
    }
}