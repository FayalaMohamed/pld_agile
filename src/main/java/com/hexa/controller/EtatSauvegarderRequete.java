package com.hexa.controller;

import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

public class EtatSauvegarderRequete implements State {
  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance().open(false);
      if (xmlFile == null) {
      } else {
        // TODO mettre l'appel a la sauvegarde
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
  }
}
