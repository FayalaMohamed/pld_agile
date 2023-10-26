package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.view.Window;
import com.hexa.model.XMLParser;

public class Controller {

  private State currentState;
  private Window window;
  private Graphe carte;

  protected final InitialState initialState = new InitialState();
  protected final EtatCreerRequete1 etatCreerRequete1 = new EtatCreerRequete1();
  protected final EtatCreerRequete2 etatCreerRequete2 = new EtatCreerRequete2();
  protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
  protected final EtatAuMoinsUneRequete etatAuMoinsUneRequete = new EtatAuMoinsUneRequete();
  protected final ChargerCarte chargerCarte = new ChargerCarte();
  protected final EtatChargerRequete etatChargerRequete = new EtatChargerRequete();
  protected final EtatSauvegarderRequete etatSauvegarderRequete = new EtatSauvegarderRequete();

  public Controller() {
    currentState = initialState;
    window = new Window(this);
  }

  public void initController(Window w) {
    window = w;
  }

  public void setCurrentState(State s) {
    currentState = s;
  }

  public void setCarte(Graphe carte) {
    this.carte = carte;
  }

  public void clicGauche() {
    currentState.clicGauche();
  }

  public void clicDroit() {
    currentState.clicDroit();
  }

  public void chargerCarte() {
    currentState.chargerCarte(this, window);
  }

  public void chargerRequetes() {
  }

  public void entryAction() {
    currentState.entryAction(this, window);
  }

  // UNIQUEMENT DEDIE AUX TESTS, A SUPPRIMER PLUS TARD
  public void chargerCarteTest(String file) {
    System.out.println("Making a graph from the file : " + file);
    Graphe carte = null;
    try {
      carte = XMLParser.xmlToGraphe(file);
      // TODO implémenter la fonctionnalité d'annuler ICI
      setCarte(carte);
      window.afficherCarte(carte);
      setCurrentState(etatCarteChargee);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public State getCurrentState() {
    return currentState;
  }
}
