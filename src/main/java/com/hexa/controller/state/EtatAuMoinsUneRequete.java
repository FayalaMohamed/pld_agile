package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.CircuitCommande;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.view.Window;

/**
 * Etat correspondant au cas où une carte est chargée et au moins une requête
 * existe
 * --> creerRequete ajoute une nouvelle requête à la liste de requêtes
 * --> supprimerRequete supprimer une requête de la liste de requêtes
 * --> chargerCarte rentre dans l'état EtatChargerCarte
 * --> sauvegarderRequete rentre dans l'état EtatSauvegarderRequetes
 * --> calculerTournee génère le circuit optimal pour la tournée en cours
 */
public class EtatAuMoinsUneRequete implements State {

	public void creerRequete(Controller c, Window w) {
		w.allow(false);
		w.afficherMessage("Cliquez sur une intersection pour créer la requête");
		c.setCurrentState(c.getEtatCreerRequete1());
		c.setPreviousState(c.getEtatAuMoinsUneRequete());
	}

	public void chargerRequetes(Controller c, Window w) {
		w.allow(false);
		c.setCurrentState(c.getEtatChargerRequete());
		c.entryAction();
	}

	public void supprimerRequete(Controller c, Window w) {
		w.allow(false);
		if (c.getTournee().getLivraisons().length == 0) {
			c.setCurrentState(c.getEtatCarteChargee());
			return;
		}
		c.setCurrentState(c.getEtatSupprimerRequete());
	}

	public void chargerCarte(Controller c, Window w) {
		w.allow(false);
		c.setCurrentState(c.getChargerCarte());
		c.setPreviousState(c.getEtatAuMoinsUneRequete());
		c.getChargerCarte().entryAction(c, w);
	}

	public void sauvegarderRequetes(Controller c, Window w) {
		w.allow(false);
		c.setCurrentState(c.getEtatSauvegarderRequete());
		c.entryAction();
	}

	public void calculerTournee(Controller c, Window w, ListOfCommands listOfCdes) {
		try {
			c.getTournee().construireCircuit(c.getCarte());
			w.afficherMessage("Tournée calculée");
			listOfCdes.add(new CircuitCommande(c.getTournee(), c.getTournee().getCircuit()));
		} catch (Exception e) {
			e.printStackTrace();
			w.afficherMessage(e.getMessage());
		}
	}

	@Override
	public void undo(ListOfCommands listOfCdes, Controller c) {
		listOfCdes.undo();
		if (c.getTournee().getLivraisons().length == 0) {
			c.setCurrentState(c.getEtatCarteChargee());
		}

	}

	@Override
	public void redo(ListOfCommands listOfCdes, Controller c) {
		listOfCdes.redo();
		if (c.getTournee().getLivraisons().length == 0) {
			c.setCurrentState(c.getEtatCarteChargee());
		}
	}
}
