package com.hexa.controller;

import com.hexa.view.Window;

public class EtatTourneeCalculee implements State {

    public void creerRequete(Controller c, Window w) {
        w.allow(false);
        w.afficherMessage("Cliquez sur une intersection pour créer la requête");
        c.setCurrentState(c.etatCreerRequete1);
        c.setPreviousState(c.etatAuMoinsUneRequete);
    }
  
    public void chargerRequetes(Controller c, Window w) {
        w.allow(false);
        c.setCurrentState(c.etatChargerRequete);
        c.entryAction();
    }

    public void supprimerRequete(Controller c, Window w) {
        w.allow(false);
        if (c.getTournee().getLivraisons().length > 0) {
            c.setCurrentState(c.etatSupprimerRequete);
        }
    }

    public void chargerCarte(Controller c, Window w) {
        w.allow(false);
        c.setCurrentState(c.chargerCarte);
        c.setPreviousState(c.etatAuMoinsUneRequete);
        c.chargerCarte.entryAction(c, w);
    }

    public void sauvegarderRequetes(Controller c, Window w) {
        w.allow(false);
        c.setCurrentState(c.etatSauvegarderRequete);
        c.entryAction();
    }

    public void genererFeuilleDeRoute(Controller c, Window w) {
        c.getTournee().genererFeuilleDeRoute(c.getCarte());
    }

}
