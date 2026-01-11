package fr.votreprojet.service;

import fr.votreprojet.modele.Utilisateur;

public interface FabriqueUtilisateur {
    Utilisateur creerUtilisateur(String email, String nom);
}