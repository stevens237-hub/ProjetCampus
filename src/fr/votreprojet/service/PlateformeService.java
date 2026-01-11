package fr.votreprojet.service;

import fr.votreprojet.modele.*;
import fr.votreprojet.util.*;
import java.util.*;

public class PlateformeService {
    private static PlateformeService instance;
    private Map<Long, Utilisateur> utilisateurs;
    private Map<Long, Annonce> annonces;
    private Map<Long, Message> messages;
    private Map<Long, Evenement> evenements;
    private Map<Long, Tontine> tontines;
    private Map<Long, Signalement> signalements;
    private Map<Long, Evaluation> evaluations;
    
    private Utilisateur utilisateurConnecte;
    
    private PlateformeService() {
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
        // Créer quelques utilisateurs de test
        FabriqueEtudiant fabriqueEtudiant = new FabriqueEtudiant();
        FabriqueAdministrateur fabriqueAdmin = new FabriqueAdministrateur();
        
        Etudiant etudiant1 = (Etudiant) fabriqueEtudiant.creerUtilisateur("jean@esisar.fr", "Jean Dupont");
        Etudiant etudiant2 = (Etudiant) fabriqueEtudiant.creerUtilisateur("marie@esisar.fr", "Marie Curie");
        Administrateur admin = (Administrateur) fabriqueAdmin.creerUtilisateur("admin@esisar.fr", "Admin System");
        
        ajouterUtilisateur(etudiant1);
        ajouterUtilisateur(etudiant2);
        ajouterUtilisateur(admin);
        
        // Créer quelques annonces de test
        Annonce annonce1 = etudiant1.creerAnnonce("Livre Java", "Livre sur la programmation Java", 
                                                 Categorie.MATERIEL_SCOLAIRE, TypeEchange.VENTE, 25.0);
        annonce1.publier();
        ajouterAnnonce(annonce1);
        
        Annonce annonce2 = etudiant2.creerAnnonce("Cours Maths", "Cours particuliers de mathématiques", 
                                                 Categorie.SERVICES, TypeEchange.SERVICE, 15.0);
        annonce2.publier();
        ajouterAnnonce(annonce2);
        
        // Créer un événement de test
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Evenement evenement = etudiant1.creerEvenement("Soirée ESISAR", "Soirée entre étudiants", 
                                                      cal.getTime(), "BDE ESISAR", 50);
        evenement.creer();
        ajouterEvenement(evenement);
        
        System.out.println("✅ Données de test initialisées");
    }
    
    // Méthodes d'ajout
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.put(utilisateur.getId(), utilisateur);
    }
    
    public void ajouterAnnonce(Annonce annonce) {
        annonces.put(annonce.getIdLong(), annonce);
    }
    
    public void ajouterMessage(Message message) {
        messages.put(message.getIdLong(), message);
    }
    
    public void ajouterEvenement(Evenement evenement) {
        evenements.put(evenement.getIdLong(), evenement);
    }
    
    public void ajouterTontine(Tontine tontine) {
        tontines.put(tontine.getIdLong(), tontine);
    }
    
    public void ajouterSignalement(Signalement signalement) {
        signalements.put(signalement.getIdLong(), signalement);
    }
    
    public void ajouterEvaluation(Evaluation evaluation) {
        evaluations.put(evaluation.getIdLong(), evaluation);
    }
    
    // Méthodes de recherche
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
    
    // Authentification
    public boolean connecter(String email, String nom) {
        for (Utilisateur user : utilisateurs.values()) {
            if (user.getEmail().equals(email) && user.getNom().equals(nom)) {
                utilisateurConnecte = user;
                System.out.println("✅ Connexion réussie : " + user.getNom());
                return true;
            }
        }
        System.out.println("❌ Échec de connexion");
        return false;
    }
    
    public void deconnecter() {
        System.out.println("👋 Déconnexion de : " + (utilisateurConnecte != null ? utilisateurConnecte.getNom() : "Personne"));
        utilisateurConnecte = null;
    }
    
    // Getters
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    public Map<Long, Utilisateur> getUtilisateurs() {
        return new HashMap<>(utilisateurs);
    }
    
    public Map<Long, Annonce> getAnnonces() {
        return new HashMap<>(annonces);
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
    
    public boolean estAdministrateurConnecte() {
        return utilisateurConnecte instanceof Administrateur;
    }
    
    public boolean estEtudiantConnecte() {
        return utilisateurConnecte instanceof Etudiant;
    }
}