package com.hexa.controller.state;

import java.io.File;
import java.util.ArrayList;

import com.hexa.controller.command.ChargementRequetesCommande;
import com.hexa.controller.Controller;
import com.hexa.model.Tournee;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

import static com.hexa.model.XMLParser.*;

/**
 * Etat dans lequel se trouve l'application quand le chargement d'un ensemble de
 * requêtes est en cours --> entryAction charge un fichier de requêtes dans le
 * controller
 */
public class EtatChargerRequete implements State {

	public void entryAction(Window w) {
		w.hideButtons(this);
	}

	public void entryAction(Controller c, Window w) {
		try {
			File xmlFile = XMLfileOpener.getInstance("requete").open(true);

			if (xmlFile == null) {
				boolean auMoinsUneLivraison = false;
				for (Tournee tournee : c.getTournees()) {
					if (tournee.getLivraisons().length != 0) {
						auMoinsUneLivraison = true;
						break;
					}
				}
				if (auMoinsUneLivraison) {
					c.switchToState(c.getEtatAuMoinsUneRequete());
				} else {
					c.switchToState(c.getEtatCarteChargee());				}
			} else {
				ArrayList<Tournee> tournees = xmlToListeLivraison2(xmlFile.getAbsolutePath());
				c.getListOfCommands().add(new ChargementRequetesCommande(c, tournees));
				c.switchToState(c.getEtatAuMoinsUneRequete());
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (Tournee tournee : c.getTournees()) {
				if (tournee.getNbLivraisons() != 0) {
					c.switchToState(c.getEtatAuMoinsUneRequete());
					return;
				}
			}
			c.switchToState(c.getEtatCarteChargee());
		}
	}
}
