package com.hexa.controller.command;

import com.hexa.model.Circuit;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class AjoutRequeteApresCircuit implements Command{

    private Tournee tournee;

    private Circuit circuit;

    private Livraison livraison;

    /**
     * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
     * @param tournee correspond à la tourné où on ajoute une livraison
     */
    public AjoutRequeteApresCircuit(Tournee tournee, Circuit circuit, Livraison livraison) {
        this.tournee = tournee;
        this.circuit = circuit;
    }

    @Override
    public void doCommand() {
        tournee.setCircuit(circuit);
        //tournee.setCircuitCalculer(true);
    }

    @Override
    public void undoCommand() {
        tournee.setCircuit(null);
        //tournee.setCircuitCalculer(false);
    }
}

