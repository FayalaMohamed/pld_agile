package com.hexa.controller.state;

import com.hexa.model.Livraison;
import com.hexa.view.Window;
import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.controller.command.SuppresionRequeteCommande;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.view.Window;

/**
 * Etat de l'application permettant de supprimer des requêtes
 * --> clicDroit permet de retourner dans le mode etatCarteChargee ou
 * etatAuMoinsUneRequete
 * --> clicGauche permet de supprimer la livraison correspondant à
 * l'intersection cliquée par l'utilisateur
 */
public class EtatSupprimerRequete implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void clicGauche(Controller c, Window w, Livraison livraison, ListOfCommands listOfCommands) throws TourneeException {
    for (Tournee tournee : c.getTournees()) {
      if (!tournee.getLivraisonsSet().contains(livraison)) {
        continue;
      }
      System.out.println("Livraison supprimée");

      if (tournee.estCalculee()) {
        tournee.supprimerLivraisonApresCalcul(livraison, c.getCarte());
      } else {
        //Motournee.supprimerLivraison(intersection);
        listOfCommands.add(new SuppresionRequeteCommande(tournee, livraison));
      }

    }

    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        c.switchToState(c.getEtatAuMoinsUneRequete());
        return;
      }
    }

    c.switchToState(c.getEtatCarteChargee());

  }

  public void clicDroit(Controller c, Window w) {
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getNbLivraisons() != 0) {
        c.switchToState(c.getEtatAuMoinsUneRequete());
        return;
      }
    }
    c.switchToState(c.getEtatCarteChargee());
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands listOfCommands)
      throws TourneeException {

    for (Intersection intersection : c.getCarte().getIntersections()) {
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        for (Tournee tournee : c.getTournees()) {
          Livraison livraison = tournee.getLivraison(intersection);
          if (livraison == null) {
            continue;
          }
          System.out.println("Livraison supprimée");

          if (tournee.estCalculee()) {
            tournee.supprimerLivraisonApresCalcul(livraison, c.getCarte());
          } else {
            //Motournee.supprimerLivraison(intersection);
            listOfCommands.add(new SuppresionRequeteCommande(tournee, livraison));
          }

        }
      }
    }

    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        c.switchToState(c.getEtatAuMoinsUneRequete());
        return;
      }
    }

    c.switchToState(c.getEtatCarteChargee());

  }

}
