package fr.votreprojet.modele;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Utilisateur {
    private static final AtomicLong counter = new AtomicLong(1);
    
    protected Long id;
    protected String email;
    protected String nom;
    protected Date dateInscription;
    protected Boolean verifie;
    
	// Constructeur
    public Utilisateur(String email, String nom) {
        this.id = counter.getAndIncrement();
        this.email = email;
        this.nom = nom;
        this.dateInscription = new Date();
        this.verifie = false;
    }
    
    public abstract void afficherProfil();
    
    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Date getDateInscription() { return dateInscription; }
    public void setDateInscription(Date date) { this.dateInscription = date; }
    
    public Boolean getVerifie() { return verifie; }
    public void setVerifie(Boolean verifie) { this.verifie = verifie; }
    
    @Override
    public String toString() {
        return nom + " (" + email + ")";
    }
}