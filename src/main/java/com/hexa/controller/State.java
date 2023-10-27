package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.view.Window;

public interface State {

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * @param c
   * @param w
   */
  public default void entryAction(Controller c, Window w) {

  }

  /**
   * Méthode appelée par le contrôleur après un clic gauche
   * @param c
   * @param w
   * @param Coordonnees
   */
  public default void clicGauche(Controller c, Window w, Coordonnees Coordonnees) {
  }

  /**
   * Méthode appelée par le contrôleur après un clic droit
   * @param c
   * @param w
   */
  public default void clicDroit(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger une carte"
   * @param c
   * @param w
   */
  public default void chargerCarte(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Créer une requête"
   * @param c
   * @param w
   */
  public default void creerRequete(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger des requêtes"
   * @param c
   * @param w
   */
  public default void chargerRequetes(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Supprimer des requêtes"
   * @param c
   * @param w
   */
  public default void supprimerRequete(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Sauvegarder les requêtes"
   * @param c
   * @param w
   */
  public default void sauvegarderRequetes(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Calculer les tournées"
   * @param c
   * @param w
   */
  public default void calculerTournee(Controller c, Window w) {
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Choisir le nombre de livreurs"
   * @param c
   * @param w
   * @param livreur
   */
  public default void choixLivreur(Controller c, Window w, int livreur) {
  }


}
