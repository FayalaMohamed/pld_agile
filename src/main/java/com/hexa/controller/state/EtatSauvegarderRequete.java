package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

/**
 * Etat de l'application permettant la sauvegarde des requêtes
 * --> entryAction sauvegarde la liste de livraisons actuelle du contrôleur dans
 * le fichier choisi
 */
public class EtatSauvegarderRequete implements State {

	/**
	 * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
	 * 
	 * @param w
	 */
	public void entryAction(Window w) {
		w.hideButtons(this);
	}

	/**
	 * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
	 * 
	 * @param c
	 * @param w
	 */
	public void entryAction(Controller c, Window w) {
		try {
			File xmlFile = XMLfileOpener.getInstance("requete").open(false);
			if (xmlFile != null) {
				XMLParser.listeLivraisonsToXml(xmlFile.getAbsolutePath(), c.getTournees());
			}
		} catch (Exception e) {
			e.printStackTrace();
			w.afficherMessage("Erreur lors de la sauvegarde");
		} finally {
			c.switchToState(c.getEtatAuMoinsUneRequete());
		}
	}
}
