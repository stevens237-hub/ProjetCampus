package fr.votreprojet.app;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Lancement de la plateforme collaborative ESISAR...");
        
        try {
            InterfaceConsole interfaceConsole = new InterfaceConsole();
            interfaceConsole.demarrer();
            
        } catch (Exception e) {
            System.err.println(" Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}