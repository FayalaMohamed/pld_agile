package com.hexa.controller.state;

import com.hexa.model.Livraison;
import com.hexa.view.Window;

import java.util.List;

import com.hexa.controller.Controller;
import com.hexa.controller.command.SuppresionRequeteCommande;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;

/**
 * Etat de l'application permettant de supprimer des requêtes
 * --> clicDroit permet de retourner dans le mode etatCarteChargee ou
 * etatAuMoinsUneRequete
 * --> clicGauche permet de supprimer la livraison correspondant à
 * l'intersection cliquée par l'utilisateur
 */
public class EtatSupprimerRequete implements State {

	public void entryAction(Window w) {
		w.hideButtons(this);
	}

	public void clicGauche(Controller c, Window w, Livraison livraison)
			throws TourneeException {
		
		boolean toutesVide = true;
		
		for (Tournee tournee : c.getTournees()) {
			
			if (tournee.getLivraison(livraison.getLieu()) == null) {
				
				if (tournee.getNbLivraisons() > 0) {
					toutesVide = false;
				}
				
				continue;
			}
			

			if (tournee.estCalculee()) {
				tournee.supprimerLivraisonApresCalcul(livraison, c.getCarte());
			} else {
				c.getListOfCommands().add(new SuppresionRequeteCommande(tournee, livraison));
			}
			
			if (tournee.getNbLivraisons() > 0) {
				toutesVide = false;
			}

		}

		if (!toutesVide) {
			c.switchToState(c.getEtatAuMoinsUneRequete());
		}
		else {
			c.switchToState(c.getEtatCarteChargee());
		}
		

	}

	public void clicDroit(Controller c, Window w) {
		for (Tournee tournee : c.getTournees()) {
			if (tournee.getNbLivraisons() != 0) {
				c.switchToState(c.getEtatAuMoinsUneRequete());
				return;
			}
		}
		c.switchToState(c.getEtatCarteChargee());
	}

	public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris)
			throws TourneeException {

		
		List<Intersection> intersectionsSelectionnees = w.getIntersectionsSelectionnees(coordonneesSouris);
		
		for (Intersection inter : intersectionsSelectionnees) {
			for (Tournee tournee : c.getTournees()) {
				Livraison livraison = tournee.getLivraison(inter);
				if (livraison == null) {
					continue;
				}
				
				if (tournee.estCalculee()) {
					tournee.supprimerLivraisonApresCalcul(livraison, c.getCarte());
				} else {
					c.getListOfCommands().add(new SuppresionRequeteCommande(tournee, livraison));
				}
			}
		}
		
		
		for (Tournee tournee : c.getTournees()) {
			if (tournee.getNbLivraisons() > 0) {
				c.switchToState(c.getEtatAuMoinsUneRequete());
				return;
			}
		}

		c.switchToState(c.getEtatCarteChargee());

	}

}
