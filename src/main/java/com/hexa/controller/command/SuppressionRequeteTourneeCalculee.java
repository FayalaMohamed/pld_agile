package com.hexa.controller.command;

import com.hexa.model.Circuit;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;

/**
 * Commande permettant de supprimer une requête d'une tournée déjà calculée
 */
public class SuppressionRequeteTourneeCalculee implements Command {
  private Graphe map;
  private Tournee tournee;
  private Livraison livraison;
  private Livraison livraisonPrecedente;

  public SuppressionRequeteTourneeCalculee(Graphe map, Tournee tournee, Livraison livraison) {
    assert (tournee.estCalculee());
    this.map = map;
    this.tournee = tournee;
    this.livraison = livraison;
    try {
      Circuit circuit = tournee.getCircuit();
      circuit.reset();
      while (circuit.hasNext()) {
        Segment segment = circuit.next();
        if (tournee.estLieuLivraison(segment.getOrigine())) {
          livraisonPrecedente = tournee.getLivraison(segment.getOrigine());
        }
        if (segment.getDestination().equals(livraison.getLieu())) {
          return;
        }
      }
      livraisonPrecedente = null;
    } catch (TourneeException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void doCommand() {
    try {
      tournee.supprimerLivraisonApresCalcul(livraison, map);
    } catch (TourneeException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undoCommand() {
    try {
      tournee.ajouterLivraisonApresCalcul(map, livraison, livraisonPrecedente);
    } catch (TourneeException e) {
      e.printStackTrace();
    } catch (GrapheException e) {
      e.printStackTrace();
    }
  }

}
