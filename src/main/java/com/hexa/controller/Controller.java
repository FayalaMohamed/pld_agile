package com.hexa.controller;


import java.util.ArrayList;

import com.hexa.controller.command.ListOfCommands;
import com.hexa.controller.state.ChargerCarte;
import com.hexa.controller.state.EtatAuMoinsUneRequete;
import com.hexa.controller.state.EtatCarteChargee;
import com.hexa.controller.state.EtatChargerRequete;
import com.hexa.controller.state.EtatCreerRequete1;
import com.hexa.controller.state.EtatCreerRequete2;
import com.hexa.controller.state.EtatCreerRequete3;
import com.hexa.controller.state.EtatSauvegarderRequete;
import com.hexa.controller.state.EtatSupprimerRequete;
import com.hexa.controller.state.InitialState;
import com.hexa.controller.state.State;
import com.hexa.model.*;
import com.hexa.view.Window;

public class Controller {

  // -----------------------------------------------------------------------------------------------------

  // Pattern State
  private State currentState;
  private State previousState;
  // Instances associées avec chacuns des états possibles pour le controlleur
  private final InitialState initialState = new InitialState();
  private final EtatCreerRequete1 etatCreerRequete1 = new EtatCreerRequete1();
  private final EtatCreerRequete2 etatCreerRequete2 = new EtatCreerRequete2();
  private final EtatCreerRequete3 etatCreerRequete3 = new EtatCreerRequete3();
  private final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
  private final EtatAuMoinsUneRequete etatAuMoinsUneRequete = new EtatAuMoinsUneRequete();
  private final ChargerCarte chargerCarte = new ChargerCarte();
  private final EtatSupprimerRequete etatSupprimerRequete = new EtatSupprimerRequete();
  private final EtatChargerRequete etatChargerRequete = new EtatChargerRequete();
  private final EtatSauvegarderRequete etatSauvegarderRequete = new EtatSauvegarderRequete();

  // Model
  private ArrayList<Tournee> tournees;
  private Graphe carte;

  // Vue
  private Window window;

  // Undo - Redo
  private ListOfCommands listOfCommands;

  int nbLivreurs;

  /**
   * Crée le controlleur de l'application
   */
  public Controller() {
    // WARNING: The number of "livreurs" is currently hard coded
    nbLivreurs = 3;
    tournees = new ArrayList<Tournee>();
    listOfCommands = new ListOfCommands();
  }


  /** Initialise la window du Controller
   * @param window
   */
  public void initialiser(Window window) {
    this.window = window;
    
    previousState = initialState;
    switchToState(initialState);
  }


  /**
   * @return int
   */
  public int getNbLivreurs() {
    return nbLivreurs;
  }


  /**
   * @return ArrayList<Tournee>
   */
  public ArrayList<Tournee> getTournees() {
    return tournees;
  }



  /** Ajouter une nouvelle tournée
   * @param tournee
   */
  public void addTournee(Tournee tournee) {
    tournee.addObserver(window.getGraphicalView());
    tournee.notifyObservers(window.getGraphicalView());

    tournee.addObserver(window.getTextualView());
    //tournee.notifyObservers(window.getTextualView());
    tournees.add(tournee);
  }

    public void addTourneeChargement(Tournee tournee) {
        tournee.addObserver(window.getGraphicalView());
        tournee.notifyObservers(window.getGraphicalView());

        tournee.addObserver(window.getTextualView());
        tournee.notifyObservers(window.getTextualView());
        tournees.add(tournee);
    }



  /**
   * @return ListOfCommands
   */
  public ListOfCommands getListOfCommands() {
    return listOfCommands;
  }


  /**
   * @return Graphe
   */
  public Graphe getCarte() {
    return carte;
  }


  /**
   * @return State
   */
  public State getPreviousState() {
    return previousState;
  }


  /**
   * @return InitialState
   */
  public InitialState getInitialState() {
    return initialState;
  }


  /**
   * @return EtatCreerRequete1
   */
  public EtatCreerRequete1 getEtatCreerRequete1() {
    return etatCreerRequete1;
  }


  /**
   * @return EtatCreerRequete2
   */
  public EtatCreerRequete2 getEtatCreerRequete2() {
    return etatCreerRequete2;
  }


  /**
   * @return EtatCreerRequete3
   */
  public EtatCreerRequete3 getEtatCreerRequete3() {
    return etatCreerRequete3;
  }


  /**
   * @return EtatCarteChargee
   */
  public EtatCarteChargee getEtatCarteChargee() {
    return etatCarteChargee;
  }


  /**
   * @return EtatAuMoinsUneRequete
   */
  public EtatAuMoinsUneRequete getEtatAuMoinsUneRequete() {
    return etatAuMoinsUneRequete;
  }


  /**
   * @return ChargerCarte
   */
  public ChargerCarte getChargerCarte() {
    return chargerCarte;
  }


  /**
   * @return EtatSupprimerRequete
   */
  public EtatSupprimerRequete getEtatSupprimerRequete() {
    return etatSupprimerRequete;
  }


  /**
   * @return EtatChargerRequete
   */
  public EtatChargerRequete getEtatChargerRequete() {
    return etatChargerRequete;
  }


  /**
   * @return EtatSauvegarderRequete
   */
  public EtatSauvegarderRequete getEtatSauvegarderRequete() {
    return etatSauvegarderRequete;
  }


  /**
   * @param s
   */
  public void setPreviousState(State s) {
    previousState = s;
  }


  /**
   * @param carte
   */
  public void setCarte(Graphe carte) {
    this.carte = carte;
  }

  /** Suprime toutes les tournees
   */
  public void supprimerTournees() {
    this.tournees.clear();
    this.window.clearTournees();
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
   * 
   * @param coordonnees les coordonnées du clic gauche
   * @throws TourneeException
   */
  public void clicGauche(Coordonnees coordonnees) throws TourneeException {
    currentState.clicGauche(this, window, coordonnees);
  }

  /**
   * Méthode appelée par la fenêtre après un clic droit sur la vue graphique
   */
  public void clicDroit() {
    System.out.println("Current state : " + currentState);
    currentState.clicDroit(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Charger une
   * carte"
   */
  public void chargerCarte() {
    currentState.chargerCarte(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Charger des
   * requêtes"
   */
  public void chargerRequetes() {
    currentState.chargerRequetes(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après une sélection dans le JComboBox "Nombre
   * de livreurs"
   * 
   * @throws TourneeException
   * @throws GrapheException
   */
  public void choixLivreur(int livreur) {
    currentState.choixLivreur(this, window, livreur);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Supprimer une
   * requête"
   */
  public void supprimerRequete() {
    currentState.supprimerRequete(this, window);
  }


  /** Méthode appelée quand on clique sur une livraison avec un clic gauche
   * @param livraison
   * @throws TourneeException
   */
  public void clicGauche(Livraison livraison) throws TourneeException {
    currentState.clicGauche(this, window, livraison);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Calculer la
   * tournée"
   */
  public void calculerTournee() {
    currentState.calculerTournee(this, window, listOfCommands);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Sauvegarder les
   * requêtes"
   */
  public void sauvegarderRequetes() {
    currentState.sauvegarderRequetes(this, window);
  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Créer une
   * requête"
   */
  public void creerRequete() {
    currentState.creerRequete(this, window);
  }

  /**
   * Méthode appelée par un état a lorsqu'il passe à un autre état b (après être
   * passé dans l'état b) Correspond à la première action qui doit être effectuée
   * quand on entre dans l'état
   */
  public void entryAction() {
    currentState.entryAction(this, window);
  }

  /**
   * Method called by window after a click on the button "Undo"
   */
  public void undo() {
    currentState.undo(listOfCommands, this);

  }

  /**
   * Méthode appelée par la fenêtre après un clic sur le bouton "Générer la
   * feuille de route"
   */
  public void genererFeuilleDeRoute() {
    currentState.genererFeuilleDeRoute(this, window);
  }

  /**
   * Méthode appelée par un état a lorsqu'il passe à un autre état b
   * (après être passé dans l'état b)
   * Correspond à la première action qui doit être effectuée quand on entre dans
   * l'état
   */
  public void redo() {
    currentState.redo(listOfCommands, this);
  }

  /**
   * Switches to the state newState and executes the entry action of the new state
   */
  public void switchToState(State newState) {
    currentState = newState;
    System.out.println("[[|- NEW STATE : " + currentState);
    currentState.entryAction(window);
  }


  /**
   * @param tournees
   */
  public void setTournees(ArrayList<Tournee> tournees) {
    this.supprimerTournees();
    for(Tournee tournee : tournees){
      this.addTourneeChargement(tournee);
    }
  }

}
