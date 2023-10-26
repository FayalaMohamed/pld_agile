package com.hexa.controller;

import com.hexa.model.Livraison;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class EtatSauvegarderRequete implements State {
  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance().open(false);
      if (xmlFile == null) {
      } else {
        // TODO mettre l'appel a la sauvegarde (a decommenter)
        XMLParser.listeLivraisonsToXml("/tmp/listeLivraison.xml", (c.getTournee().getLivraisonsSet()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
  }
}
