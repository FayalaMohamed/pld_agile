package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.view.Window;

public class EtatTourneeCalculee implements State {

    public void creerRequete(Controller c, Window w) {
        w.afficherMessage("Cliquez sur une intersection pour créer la requête");
        c.switchToState(c.getEtatCreerRequete1());
        c.setPreviousState(c.getEtatAuMoinsUneRequete());
    }
  
    public void chargerRequetes(Controller c, Window w) {
        c.switchToState(c.getEtatChargerRequete());
        c.entryAction();
    }

    public void supprimerRequete(Controller c, Window w) {
    	//TODO : à adapter à plusieurs tournées
    	
//        w.allow(false);
//        if (c.getTournee().getLivraisons().length > 0) {
//            c.setCurrentState(c.etatSupprimerRequete);
//        }
    }

    public void chargerCarte(Controller c, Window w) {
        c.switchToState(c.getChargerCarte());
        c.setPreviousState(c.getEtatAuMoinsUneRequete());
        c.getChargerCarte().entryAction(c, w);
    }

    public void sauvegarderRequetes(Controller c, Window w) {
        c.switchToState(c.getEtatSauvegarderRequete());
        c.entryAction();
    }

    public void genererFeuilleDeRoute(Controller c, Window w) {
    	//TODO : à adapter à plusieurs tournées
        //c.getTournee().genererFeuilleDeRoute(c.getCarte());
    }

}
