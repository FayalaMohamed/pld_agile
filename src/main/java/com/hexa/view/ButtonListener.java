package com.hexa.view;

import com.hexa.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

  private Controller controller;

  public ButtonListener(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case Window.CHARGER_CARTE:
        System.out.println("Bouton Charger Carte");
        controller.chargerCarte();
        break;
      
      case Window.CREER_REQUETE:
        System.out.println("Bouton Créer Requête");
        controller.creerRequete();
        break;

      case Window.SUPPRIMER_REQUETES:
        System.out.println("Bouton Supprimer Requête");
        controller.supprimerRequete();
        break;

      case Window.CALCULER_TOURNEE:
        System.out.println("Bouton Calculer tournée");
        controller.calculerTournee();
        break;
       case Window.CHARGER_REQUETES:
         System.out.println("Bouton charger requetes");
           controller.chargerRequetes();
           break;
       case Window.SAUVEGARDER_REQUETES:
         System.out.println("Bouton sauvegarder requetes");
           controller.sauvegarderRequetes();
           break;
    }
  }
}
