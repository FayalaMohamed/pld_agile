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
	 * Cree une commande qui ajoute une livraison à la tournée après le calcul du circuit Le circuit est
	 * modifié de sorte que la livraisonAjouter se fasse après la
	 * livraisonPrecedente.
	 *
	 * @param carte
	 * @param tournee
	 * @param livraisonAjouter
	 * @param intersectionPrecedente correspond à la livraison qui va préceder la livraisonAjouter
	 *
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
	}
	/**
	 * Cette méthode va remettre l'ancien circuit calculé sans la livraison ajouté
	 * et supprimer la livraison de la tournée
	 */
	@Override
	public void undoCommand() {
		tournee.setCircuit(circuitPrecedent);
		tournee.supprimerLivraison(livraison.getLieu());
	}
}
