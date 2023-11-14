package com.hexa.controller.state;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

import java.io.File;
import java.util.Set;

import com.hexa.model.Livreur;
import com.hexa.controller.Controller;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

/**
 * Etat dans lequel se trouve l'application quand le chargement d'un ensemble de
 * requêtes est en cours --> entryAction charge un fichier de requêtes dans le
 * controller
 */
public class EtatChargerRequete implements State {
	public void entryAction(Controller c, Window w) {
		try {
			File xmlFile = XMLfileOpener.getInstance("requete").open(true);

			if (xmlFile == null) {
				for (Tournee tournee : c.getTournees()) {
					if (tournee.getLivraisons().length != 0) {
						c.setCurrentState(c.getEtatAuMoinsUneRequete());
						break;
					}
					c.setCurrentState(c.getEtatCarteChargee());
				}
			} else {
				// TODO c.getTournee().setCircuitCalculer(true);
				int livreur = -1;
				Set<Livraison> livraisons = xmlToListeLivraison(xmlFile.getAbsolutePath());
				for (Livraison livraison : livraisons) {
					livreur = livraison.getLivreur().getId();
					break;
				}

				boolean livreurFound = false;
				for (Tournee tournee : c.getTournees()) {
					if (tournee.getLivreur().getId() == livreur) {
						livreurFound = true;
						tournee.setLivraisons(livraisons);
						if (tournee.getNbLivraisons() == 0) {
							c.setCurrentState(c.getEtatCarteChargee());
						} else {
							c.setCurrentState(c.getEtatAuMoinsUneRequete());
						}
						break;
					}
				}
				if (!livreurFound) {
					System.out.println("TOTO");
					Tournee tournee = new Tournee();
					tournee.setLivreur(new Livreur(livreur));
					c.addTournee(tournee);
					tournee.setLivraisons(livraisons);
					if (tournee.getNbLivraisons() == 0) {
						c.setCurrentState(c.getEtatCarteChargee());
					} else {
						c.setCurrentState(c.getEtatAuMoinsUneRequete());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (Tournee tournee : c.getTournees()) {
				if (tournee.getNbLivraisons() != 0) {
					c.setCurrentState(c.getEtatAuMoinsUneRequete());
					w.allow(true);
					return;
				}
			}
			c.setCurrentState(c.getEtatCarteChargee());
		}
		w.allow(true);
	}
}
