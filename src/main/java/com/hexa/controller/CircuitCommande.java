package com.hexa.controller;

import com.hexa.model.Circuit;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class CircuitCommande implements Command{

    private Tournee tournee;
    private Circuit circuit;

    /**
     * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
     * @param tournee correspond à la tourné où on ajoute une livraison
     * @param circuit correspond à la livraison ajouté
     */
    public CircuitCommande(Tournee tournee, Circuit circuit) {
        this.tournee = tournee;
        this.circuit = circuit;
    }

    @Override
    public void doCommand() {
        tournee.setCircuit(circuit);
    }

    @Override
    public void undoCommand() {
        tournee.setCircuit(null);
    }
}

