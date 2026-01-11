package fr.votreprojet.service;

import fr.votreprojet.util.TypeEvenement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SujetAbstrait implements Sujet {
    private final List<Observateur> observateurs = new CopyOnWriteArrayList<>();
    private boolean notificationsSuspended = false;
    private final Object lock = new Object();
    
    @Override
    public void attacher(Observateur observateur) {
        synchronized (lock) {
            if (observateur != null && !observateurs.contains(observateur)) {
                observateurs.add(observateur);
                System.out.println(getClass().getSimpleName() + " : Observateur attaché");
            }
        }
    }
    
    @Override
    public void detacher(Observateur observateur) {
        synchronized (lock) {
            observateurs.remove(observateur);
            System.out.println(getClass().getSimpleName() + " : Observateur détaché");
        }
    }
    
    @Override
    public void notifier(TypeEvenement typeEvenement) {
        if (notificationsSuspended || observateurs.isEmpty()) {
            return;
        }
        
        synchronized (lock) {
            System.out.println(getClass().getSimpleName() + " : Notification '" + 
                             typeEvenement + "' à " + observateurs.size() + " observateur(s)");
            
            for (Observateur observateur : observateurs) {
                try {
                    observateur.actualiser(this, typeEvenement);
                } catch (Exception e) {
                    System.err.println(" Erreur notification : " + e.getMessage());
                }
            }
        }
    }
    
    @Override
    public List<Observateur> getObservateurs() {
        return new ArrayList<>(observateurs);
    }
    
    public void suspendreNotifications() {
        this.notificationsSuspended = true;
    }
    
    public void reprendreNotifications() {
        this.notificationsSuspended = false;
    }
    
    public void notifierFiltre(TypeEvenement typeEvenement, Class<? extends Observateur> classeObservateur) {
        if (notificationsSuspended) return;
        
        synchronized (lock) {
            for (Observateur observateur : observateurs) {
                if (classeObservateur.isInstance(observateur)) {
                    observateur.actualiser(this, typeEvenement);
                }
            }
        }
    }
    
    public void viderObservateurs() {
        synchronized (lock) {
            observateurs.clear();
        }
    }
    
    public boolean contientObservateur(Observateur observateur) {
        return observateurs.contains(observateur);
    }
    
    public int countObservateurs() {
        return observateurs.size();
    }
    
    public abstract Object getId();
}