package com.hexa.controller.command;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class SuppresionRequeteCommande implements Command {

	/**
	 * Cree une commande qui supprime une livraison dans une tournee
	 *
	 * @param tournee   correspond à la tournée manipulée
	 * @param livraison correspond à la livraison supprimé qu'on va conserver pour le undo
	 */

	private Tournee tournee;
	private Livraison livraison;
	
	//---------------------------------------------------------------------------------------------------------------------------------

	public SuppresionRequeteCommande(Tournee tournee, Livraison livraison) {
		this.tournee = tournee;
		this.livraison = livraison;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void doCommand() {
		tournee.supprimerLivraison(livraison.getLieu());
	}

	@Override
	public void undoCommand() {
		tournee.ajouterLivraison(livraison);
	}
}
