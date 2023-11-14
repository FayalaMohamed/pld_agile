package com.hexa.controller.state;

import com.hexa.model.Livraison;
import com.hexa.model.TourneeException;
import com.hexa.view.Window;
import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.controller.command.SuppresionRequeteCommande;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;

/**
 * Etat de l'application permettant de supprimer des requêtes
 * --> clicDroit permet de retourner dans le mode etatCarteChargee ou
 * etatAuMoinsUneRequete
 * --> clicGauche permet de supprimer la livraison correspondant à
 * l'intersection cliquée par l'utilisateur
 */
public class EtatSupprimerRequete implements State {

	public void clicDroit(Controller c, Window w) {
		System.out.println("Annuler Supprimer Requête");
		if (c.getTournee().getNbLivraisons() == 0) {
			c.setCurrentState(c.getEtatCarteChargee());
		} else {
			c.setCurrentState(c.getEtatAuMoinsUneRequete());
		}
		w.allow(true);
	}

	public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands listOfCommands) throws TourneeException {

		for (Intersection intersection : c.getCarte().getIntersections()) {
			Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
			if (coord.equals(coordonneesSouris)) {
				Livraison livraison = c.getTournee().getLivraison(intersection);
				if (livraison == null)
					continue;

				if (c.getTournee().estCalculee()) {
					c.getTournee().supprimerLivraisonApresCalcul(livraison, c.getCarte());
				} else {
					c.getTournee().supprimerLivraison(intersection);
				}
				listOfCommands.add(new SuppresionRequeteCommande(c.getTournee(), livraison));
			}
		}

		if (c.getTournee().getLivraisons().length == 0) {
			c.setCurrentState(c.getEtatCarteChargee());
		} else {
			c.setCurrentState(c.getEtatAuMoinsUneRequete());
		}

		w.allow(true);
	}

}
