package com.hexa.controller.command;

import com.hexa.controller.Controller;
import com.hexa.model.Tournee;

import java.util.ArrayList;

public class ChargementRequetesCommande implements Command {

	private Controller controller;

	private ArrayList<Tournee> tourneesPrecedente;

	private ArrayList<Tournee> tournees2;

	// ---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Cree une commande qui ajoute une Livraison dans une Tournee
	 *
	 * @param controller correspond à la tourné où on charge les livraisons
	 * @param tournees   correspond aux livraisons chargées
	 */
	public ChargementRequetesCommande(Controller controller, ArrayList<Tournee> tournees) {
		this.controller = controller;
		this.tournees2 = new ArrayList<Tournee>();
		this.tourneesPrecedente = new ArrayList<Tournee>();
		this.tourneesPrecedente.addAll(controller.getTournees());
		this.tournees2.addAll(tournees);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void doCommand() {
		this.controller.setTournees(tournees2);
	}

	@Override
	public void undoCommand() {
		this.controller.setTournees(tourneesPrecedente);

	}
}
