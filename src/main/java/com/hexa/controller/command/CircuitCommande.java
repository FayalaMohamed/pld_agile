package com.hexa.controller.command;

import com.hexa.model.Circuit;
import com.hexa.model.Tournee;

public class CircuitCommande implements Command {

	private Tournee tournee;

	private Circuit circuit;

	// ---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Cree une commande qui conserve le circuit calculé
	 * 
	 * @param tournee correspond à la tournée sans le circuit calculé
	 * @param circuit correspond au circuit calculé
	 */
	public CircuitCommande(Tournee tournee, Circuit circuit) {
		this.tournee = tournee;
		this.circuit = circuit;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void doCommand() {
		tournee.setCircuit(circuit);
	}

	@Override
	public void undoCommand() {
		tournee.setCircuit(null);
	}
}
