package fr.votreprojet.modele;

import fr.votreprojet.service.SujetAbstrait;
import fr.votreprojet.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Annonce extends SujetAbstrait {
    private static final AtomicLong counter = new AtomicLong(1);
    
    private Long id;
    private String titre;
    private String description;
    private Categorie categorie;
    private TypeEchange typeEchange;
    private Double prix;
    private Date datePublication;
    private Etudiant createur;
    private StatutAnnonce statut;
    private String localisation;
    private List<Signalement> signalements;
    
    public Annonce(String titre, String description, Categorie categorie, 
                   TypeEchange typeEchange, Double prix, Etudiant createur) {
        this.id = counter.getAndIncrement();
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.typeEchange = typeEchange;
        this.prix = prix;
        this.createur = createur;
        this.localisation = "Campus Polytech";
        this.statut = StatutAnnonce.SUPPRIMÉE;
        this.signalements = new ArrayList<>();
        this.datePublication = new Date();
    }
    
    public Annonce() {
        this.id = counter.getAndIncrement();
        this.signalements = new ArrayList<>();
        this.statut = StatutAnnonce.SUPPRIMÉE;
    }
    
    public void publier() {
        if (this.statut == StatutAnnonce.SUPPRIMÉE) {
            this.statut = StatutAnnonce.PUBLIEE;
            this.datePublication = new Date();
            notifier(TypeEvenement.NOUVELLE_ANNONCE);
            System.out.println(" Annonce publiée avec succès !");
        }
    }
    
    public void modifier(String nouveauTitre, String nouvelleDescription) {
        boolean changement = false;
        
        if (nouveauTitre != null && !nouveauTitre.equals(titre)) {
            this.titre = nouveauTitre;
            changement = true;
        }
        
        if (nouvelleDescription != null && !nouvelleDescription.equals(description)) {
            this.description = nouvelleDescription;
            changement = true;
        }
        
        if (changement) {
            notifier(TypeEvenement.ANNONCE_MODIFIEE);
            System.out.println(" Annonce modifiée avec succès");
        }
    }
    
    public void supprimer() {
        this.statut = StatutAnnonce.SUPPRIMÉE;
        notifier(TypeEvenement.ANNONCE_SUPPRIMEE);
        viderObservateurs();
        System.out.println(" Annonce supprimée");
    }
    
    public Signalement signaler(TypeSignalement type, String description) {
        Signalement signalement = new Signalement(type, description, this.createur);
        signalement.setCibleAnnonce(this);
        signalements.add(signalement);
        this.statut = StatutAnnonce.SIGNALÉE;
        notifier(TypeEvenement.ANNONCE_SIGNALEE);
        return signalement;
    }
    
    public void archiver() {
        this.statut = StatutAnnonce.TERMINÉE;
        System.out.println(" Annonce archivée");
    }
    
    public void reserver() {
        this.statut = StatutAnnonce.RÉSERVÉE;
        notifier(TypeEvenement.ANNONCE_RESERVEE);
        System.out.println(" Annonce réservée");
    }
    
    @Override
    public Object getId() {
        return this.id;
    }
    
    public void afficherDetails() {
        System.out.println("\n ANNONCE #" + id);
        System.out.println("════════════════════════════════");
        System.out.println("Titre : " + titre);
        System.out.println("Description : " + description);
        System.out.println("Catégorie : " + categorie.getLibelle());
        System.out.println("Type : " + typeEchange.getLibelle());
        System.out.println("Prix : " + (prix != null ? prix + "€" : "Gratuit"));
        System.out.println("Localisation : " + localisation);
        System.out.println("Statut : " + statut.getLibelle());
        System.out.println("Créateur : " + createur.getNom());
        System.out.println("Date : " + datePublication);
        System.out.println("Signalements : " + signalements.size());
        System.out.println("Observateurs : " + countObservateurs());
        System.out.println("════════════════════════════════");
    }
    
    // Getters et setters
    public Long getIdLong() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    
    public TypeEchange getTypeEchange() { return typeEchange; }
    public void setTypeEchange(TypeEchange typeEchange) { this.typeEchange = typeEchange; }
    
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    
    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }
    
    public Etudiant getCreateur() { return createur; }
    public void setCreateur(Etudiant createur) { this.createur = createur; }
    
    public StatutAnnonce getStatut() { return statut; }
    public void setStatut(StatutAnnonce statut) { this.statut = statut; }
    
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    
    public List<Signalement> getSignalements() { return new ArrayList<>(signalements); }
}