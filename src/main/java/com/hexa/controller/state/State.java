package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Coordonnees;
import com.hexa.model.Livraison;
import com.hexa.model.TourneeException;
import com.hexa.view.Window;

public interface State {

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * 
   * @param c
   * @param w
   */
  public default void entryAction(Controller c, Window w) {

  }

  public default void entryAction(Window w) {
    System.out.println("Merci d'implémenter la fonction entryAction pour l'état " + this);
  }

  public default void clicGauche(Controller c, Window w, Livraison livraison) throws TourneeException {

  }

  /**
   * Méthode appelée par le contrôleur après un clic gauche
   * 
   * @param c
   * @param w
   * @param Coordonnees
 * @throws TourneeException
   */
  public default void clicGauche(Controller c, Window w, Coordonnees Coordonnees) throws TourneeException {
  }

  /**
   * Méthode appelée par le contrôleur après un clic droit
   * 
   * @param c
   * @param w
   */
  public default void clicDroit(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger
   * une carte"
   * 
   * @param c
   * @param w
   */
  public default void chargerCarte(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Créer une
   * requête"
   * 
   * @param c
   * @param w
   */
  public default void creerRequete(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après un défilement de la molette de souris
   */
  public default void zoom() {
  }

  /**
   * Méthode appelée par le contrôleur après un glissement fait par la souris
   */
  public default void glissement() {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger
   * des requêtes"
   * 
   * @param c
   * @param w
   */
  public default void chargerRequetes(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Supprimer
   * des requêtes"
   * 
   * @param c
   * @param w
   */
  public default void supprimerRequete(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton
   * "Sauvegarder les requêtes"
   * 
   * @param c
   * @param w
   */
  public default void sauvegarderRequetes(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Calculer
   * les tournées"
   * 
   * @param c
   * @param w
   */
  public default void calculerTournee(Controller c, Window w, ListOfCommands listOfCdes) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Choisir
   * le nombre de livreurs"
   * 
   * @param c
   * @param w
   * @param livreur
   */
  public default void choixLivreur(Controller c, Window w, int livreur) {
  }

  /**
   * Method called by the controller after a click on the button "Undo"
   * 
   * @param l the current list of commands
   */
  public default void undo(ListOfCommands l, Controller c) {
  };

  /**
   * Method called by the controller after a click on the button "Redo"
   * 
   * @param l the current list of commands
   */
  public default void redo(ListOfCommands l, Controller c) {
  };
  
  /*
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Générer la feuille de route"
   * @param c
   * @param w
   */
  public default void genererFeuilleDeRoute(Controller c) {
  }


}
