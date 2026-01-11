package fr.votreprojet.service;

import fr.votreprojet.util.*;

public interface Observateur {

    //Methode appelée lorsque le sujet observé change d'état
    void actualiser(Sujet sujet, TypeEvenement typeEvenement);

    //Permet à l'observateur de s'abonner
    default void sabonner(Sujet sujet){
        if (sujet != null){
            sujet.attacher(this);
        }
    }

    //Permet à l'observateur de se désabonner d'un sujet
    default void seDesabonner(Sujet sujet){
        if (sujet != null){
            sujet.detacher(this);
        }
    }

}
