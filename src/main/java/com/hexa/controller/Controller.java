package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Tournee;
import com.hexa.view.Window;

public class Controller {

  private State currentState;
  protected State previousState;
  private final Window window;
  private Graphe carte;

  private ListOfCommands listOfCommands;

  private final Tournee tournee;
  int nbLivreurs;

  //Instances associées avec chacuns des états possibles pour le controlleur
  protected final InitialState initialState = new InitialState();
  protected final EtatCreerRequete1 etatCreerRequete1 = new EtatCreerRequete1();
  protected final EtatCreerRequete2 etatCreerRequete2 = new EtatCreerRequete2();
  protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
  protected final EtatAuMoinsUneRequete etatAuMoinsUneRequete = new EtatAuMoinsUneRequete();
  protected final ChargerCarte chargerCarte = new ChargerCarte();
  protected final EtatSupprimerRequete etatSupprimerRequete = new EtatSupprimerRequete();
  protected final EtatChargerRequete etatChargerRequete = new EtatChargerRequete();
  protected final EtatSauvegarderRequete etatSauvegarderRequete = new EtatSauvegarderRequete();

  /**
   * Crée le controlleur de l'application
   */
  public Controller() {
    // WARNING: The number of "livreurs" is currently hard coded
    nbLivreurs = 3;
    currentState = initialState;
    listOfCommands = new ListOfCommands();
    previousState = initialState;
    tournee = new Tournee();
    window = new Window(this, tournee);
    window.afficherMessage("Choisissez une carte à afficher");
  }

  public int getNbLivreurs() {
    return nbLivreurs;
  }

  public Tournee getTournee() {
    return tournee;
  }

  public Graphe getCarte() {
    return carte;
  }

  public void setCurrentState(State s) {
    currentState = s;
  }

  public void setPreviousState(State s) {
    previousState = s;
  }

  public void setCarte(Graphe carte) {
    this.carte = carte;
  }

   /**
   * Méthode appelée par la fenêtre après un défilement de la molette de souris
   */
  public void zoom() {
    currentState.zoom();
  }

   /**
   * Méthode appelée par la fenêtre après un glissement dépot de la souris
   */
  public void glissement() {
    currentState.glissement();
  }

  /**
   * Méthode appelée par la fenêtre après un clic gauche sur la vue graphique
   * @param coordonnees les coordonnées du clic gauche
   */
  public void clicGauche(Coordonnees coordonnees) {
    currentState.clicGauche(this, window, coordonnees, listOfCommands);
  }

  /**
   * Méthode appelée par la fenêtre après un clic droit sur la vue graphique
   */
  public void clicDroit() {
    System.out.println("Performing the right click on state : " + currentState);
    currentState.clicDroit(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Charger une carte"
   */
  public void chargerCarte() {
    currentState.chargerCarte(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Charger des requêtes"
   */
  public void chargerRequetes() {
    currentState.chargerRequetes(this,window);
  }

  /**
   * Méthode appelée par la fenêtre après une sélection dans le JComboBox "Nombre de livreurs"
   */
  public void choixLivreur(int livreur) {
    currentState.choixLivreur(this, window, livreur, listOfCommands);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Calculer la tournée"
   */
  public void calculerTournee() {
    currentState.calculerTournee(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Créer une requête"
   */
  public void creerRequete() {
    currentState.creerRequete(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Supprimer une requête"
   */
  public void supprimerRequete() {
    currentState.supprimerRequete(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Sauvegarder les requêtes"
   */
  public void sauvegarderRequetes() {
    currentState.sauvegarderRequetes(this,window);
  }

  /**
   * Méthode appelée par un état a lorsqu'il passe à un autre état b
   * (après être passé dans l'état b)
   * Correspond à la première action qui doit être effectuée quand on entre dans l'état
   */
  public void entryAction() {
    currentState.entryAction(this, window);
  }

  /**
   * Method called by window after a click on the button "Undo"
   */
  public void undo(){
    currentState.undo(listOfCommands,this);

  }

  /**
   * Method called by window after a click on the button "Redo"
   */
  public void redo(){
    currentState.redo(listOfCommands,this);
  }

}
