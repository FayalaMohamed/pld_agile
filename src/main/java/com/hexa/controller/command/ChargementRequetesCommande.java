package com.hexa.controller.command;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

import java.util.HashSet;
import java.util.Set;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

public class ChargementRequetesCommande implements Command {

    private Tournee tournee;
    private Set<Livraison> livraisons;


    /**
     * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
     *
     * @param tournee   correspond à la tourné où on charge les livraisons
     * @param livraisons correspond aux livraisons chargées
     */
    public ChargementRequetesCommande(Tournee tournee, Set<Livraison> livraisons) {
        this.tournee = tournee;
        this.livraisons = new HashSet<Livraison>();
        this.livraisons.addAll(livraisons);
    }

    @Override
    public void doCommand() {
        tournee.setLivraisons(this.livraisons);
    }

    @Override
    public void undoCommand() {
        for (Livraison l : this.livraisons)
            tournee.supprimerLivraison(l.getLieu());
    }
}
