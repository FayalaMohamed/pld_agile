package com.hexa.controller.command;

import com.hexa.controller.Controller;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

public class ChargementRequetesCommande implements Command {

    Controller controller;

    private ArrayList<Tournee> tourneesPrécedente;

    private ArrayList<Tournee> tournees2;

    /**
     * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
     *
     * @param controller   correspond à la tourné où on charge les livraisons
     * @param tournees correspond aux livraisons chargées
     */
    public ChargementRequetesCommande(Controller controller, ArrayList<Tournee> tournees) {
        this.controller = controller;
        this.tournees2 = new ArrayList<Tournee>();
        this.tourneesPrécedente = new ArrayList<Tournee>();
        this.tourneesPrécedente.addAll(controller.getTournees());
        this.tournees2.addAll(tournees);
    }

    @Override
    public void doCommand() {
        this.controller.setTournee(tournees2);
    }

    @Override
    public void undoCommand() {
        this.controller.setTournee(tourneesPrécedente);

    }
}
