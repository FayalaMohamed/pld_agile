package com.hexa.controller.command;

import com.hexa.controller.Controller;
import com.hexa.model.Tournee;

import java.util.ArrayList;

public class ChargementRequetesCommande implements Command {

	private Controller controller;

	private ArrayList<Tournee> tourneesPrecedente;

	private ArrayList<Tournee> tournees;

	// ---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Cree une commande qui charge une liste de tournée depuis un xml
	 *
	 * @param controller permet de récuperer l'ancienne liste de tournée avant le chargement
	 * @param tournees   correspond à la liste de tournées chargées
	 */
	public ChargementRequetesCommande(Controller controller, ArrayList<Tournee> tournees) {
		this.controller = controller;
		this.tournees = new ArrayList<Tournee>();
		this.tourneesPrecedente = new ArrayList<Tournee>();
		this.tourneesPrecedente.addAll(controller.getTournees());
		this.tournees.addAll(tournees);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void doCommand() {
		this.controller.setTournees(tournees);
	}

	@Override
	public void undoCommand() {
		this.controller.setTournees(tourneesPrecedente);

	}
}
