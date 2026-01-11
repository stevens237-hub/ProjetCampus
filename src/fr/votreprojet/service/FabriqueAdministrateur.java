package fr.votreprojet.service;

import fr.votreprojet.modele.Administrateur;
import fr.votreprojet.modele.Utilisateur;

public class FabriqueAdministrateur implements FabriqueUtilisateur {
    @Override
    public Utilisateur creerUtilisateur(String email, String nom) {
        System.out.println(" Création d'un nouvel administrateur : " + nom);
        return new Administrateur(email, nom);
    }
    
    public Administrateur creerAdministrateurNiveau(String email, String nom, int niveauAcces) {
        System.out.println(" Création d'un administrateur niveau " + niveauAcces + " : " + nom);
        return new Administrateur(email, nom, niveauAcces);
    }
}