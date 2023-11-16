package com.hexa.controller.command;

import com.hexa.model.*;

public class AjoutRequeteApresCircuit implements Command{

    private Tournee tournee;

    private Circuit circuitPrécedent;

    private Intersection intersection;

    private Graphe carte;

    private Livraison livraison;

    /**
     * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
     * @param tournee correspond à la tourné où on ajoute une livraison
     */
    public AjoutRequeteApresCircuit(Tournee tournee, Graphe carte, Livraison livraison, Intersection intersection) throws TourneeException {
        this.tournee = tournee;
        this.carte = carte;
        this.circuitPrécedent = tournee.getCircuit();
        this.livraison = livraison;
        this.intersection = intersection;


    }

    @Override
    public void doCommand() {
        try {
            tournee.ajouterLivraisonApresCalcul(carte, livraison, new Livraison(intersection));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //tournee.setCircuitCalculer(true);
    }

    @Override
    public void undoCommand() {
        tournee.setCircuit(circuitPrécedent);
        tournee.supprimerLivraison(livraison.getLieu());
        //tournee.setCircuitCalculer(false);
    }
}

