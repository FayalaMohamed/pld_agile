package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.view.Window;

/**
 * Etat initial de l'application à son démarrage.
 */
public class InitialState implements State {

	/**
	 * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
	 * 
	 * @param w
	 */
	public void entryAction(Window w) {
		w.hideButtons(this);
		w.afficherMessage("Chargez une carte pour créer vos requêtes");
	}

	/**
	 * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger
	 * une carte"
	 * 
	 * @param c
	 * @param w
	 */
	public void chargerCarte(Controller c, Window w) {
		c.setPreviousState(c.getInitialState());
		c.switchToState(c.getChargerCarte());
		c.entryAction();
	}
}
