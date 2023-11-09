package com.hexa.controller;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class RequeteCommande implements Command{

    private Tournee tournee;
    private Livraison livraison;

    public RequeteCommande(Tournee tournee, Livraison livraison) {
        this.tournee = tournee;
        this.livraison = livraison;
    }

    @Override
    public void doCommand() {
      tournee.ajouterLivraison(livraison);
    }

    @Override
    public void undoCommand() {
        tournee.supprimerLivraison(livraison.getLieu());
    }
}
