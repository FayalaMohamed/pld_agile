package com.hexa.controller.command;

import com.hexa.model.*;

public class AjoutRequeteApresCircuit implements Command {

	private Tournee tournee;

	private Circuit circuitPrecedent;

	private Intersection intersection;

	private Graphe carte;

	private Livraison livraison;

	// --------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
	 * 
	 * @param tournee correspond à la tourné où on ajoute une livraison
	 */
	public AjoutRequeteApresCircuit(Tournee tournee, Graphe carte, Livraison livraisonAjouter, Intersection intersectionPrecedente)
			throws TourneeException {
		this.tournee = tournee;
		this.carte = carte;
		this.circuitPrecedent = tournee.getCircuit();
		this.livraison = livraisonAjouter;
		this.intersection = intersectionPrecedente;

	}

	// ---------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void doCommand() {
		try {
			tournee.ajouterLivraisonApresCalcul(carte, livraison, new Livraison(intersection));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// tournee.setCircuitCalculer(true);
	}

	@Override
	public void undoCommand() {
		tournee.setCircuit(circuitPrecedent);
		tournee.supprimerLivraison(livraison.getLieu());
	}
}
