package fr.votreprojet.service;

import fr.votreprojet.util.*;
import java.util.List;

public interface Sujet {

    //Attacher un observateur à ce sujet
    void attacher(Observateur observateur);

    //Détacher un observateur de ce sujet
    void detacher(Observateur observateur);

    //Notifier tous les observateurs d'un événement
    void notifier(TypeEvenement typeEvenement);

    //Récuperer la liste des Observateur
    List<Observateur> getObservateurs();

    //Notifier un observateur spécifique
    default void notifierObservateur(Observateur observateur, TypeEvenement typeEvenement){
        if (observateur != null){
            observateur.actualiser(this, typeEvenement);
        }
    }
}
