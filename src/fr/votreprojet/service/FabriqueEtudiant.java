package fr.votreprojet.service;

import fr.votreprojet.modele.Etudiant;
import fr.votreprojet.modele.Utilisateur;

public class FabriqueEtudiant implements FabriqueUtilisateur {
    @Override
    public Utilisateur creerUtilisateur(String email, String nom) {
        System.out.println("🎓 Création d'un nouvel étudiant : " + nom);
        return new Etudiant(email, nom);
    }
    
    public Etudiant creerEtudiantComplet(String email, String nom, String campus, String filiere, int annee) {
        Etudiant etudiant = new Etudiant(email, nom);
        etudiant.setCampus(campus);
        etudiant.setFiliere(filiere);
        etudiant.setAnneeEtude(annee);
        return etudiant;
    }
}