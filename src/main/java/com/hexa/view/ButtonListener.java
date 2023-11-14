package com.hexa.view;

import com.hexa.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

  private Controller controller;

  public ButtonListener(Controller controller) {
    this.controller = controller;
  }

  /**
   * Méthode appelée par le ButtonListener chaque fois qu'un bouton est cliqué
   * Contient un switch case pour déterminer quel bouton est cliqué
   * Puis délègue au contrôleur
   * @param e the event to be processed
   */
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

        case Window.GENERER_FEUILLE_DE_ROUTE:
         System.out.println("Bouton Générer feuille de route");
           controller.genererFeuilleDeRoute();
           break;
    }
  }
}
