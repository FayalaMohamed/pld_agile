package com.hexa.controller.command;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class RequeteCommande implements Command {

  private Tournee tournee;
  private Livraison livraison;

  /**
   * Cree une commande qui ajoute une Livraison livraison dans une Tournee tournee
   * 
   * @param tournee   correspond à la tourné où on ajoute une livraison
   * @param livraison correspond à la livraison ajouté
   */
  public RequeteCommande(Tournee tournee, Livraison livraison) {
    this.tournee = tournee;
    this.livraison = livraison;
  }

  @Override
  public void doCommand() {
    tournee.ajouterLivraison(livraison);
  }

  @Override
  public void undoCommand() {
    tournee.supprimerLivraison(livraison.getLieu());
  }
}
