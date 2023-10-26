package com.hexa.controller;

import com.hexa.model.Coordonnees;
import java.io.File;
import com.hexa.model.Graphe;
import com.hexa.model.Tournee;
import com.hexa.view.Window;
import com.hexa.model.XMLParser;

public class Controller {

  private State currentState;
  private Window window;
  private Graphe carte;

  private Tournee tournee;
  int nbLivreurs;

  protected final InitialState initialState = new InitialState();
  protected final EtatCreerRequete1 etatCreerRequete1 = new EtatCreerRequete1();
  protected final EtatCreerRequete2 etatCreerRequete2 = new EtatCreerRequete2();
  protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
  protected final EtatAuMoinsUneRequete etatAuMoinsUneRequete = new EtatAuMoinsUneRequete();
  protected final ChargerCarte chargerCarte = new ChargerCarte();
  protected final EtatSupprimerRequete etatSupprimerRequete = new EtatSupprimerRequete();
  protected final EtatChargerRequete etatChargerRequete = new EtatChargerRequete();
  protected final EtatSauvegarderRequete etatSauvegarderRequete = new EtatSauvegarderRequete();

  public Controller() {
    // WARNING: The number of "livreurs" is currently hard coded
    nbLivreurs = 3;
    currentState = initialState;
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

  public void initController(Window w) {
    window = w;
  }

  public void setCurrentState(State s) {
    currentState = s;
  }

  public void setCarte(Graphe carte) {
    this.carte = carte;
  }

  public void clicGauche(Coordonnees coordonnees) {
    currentState.clicGauche(this, window, coordonnees);
  }

  public void clicDroit() {
    System.out.println("Performing the right click on state : " + currentState);
    currentState.clicDroit(this, window);
  }

  public void chargerCarte() {
    currentState.chargerCarte(this, window);
  }

  public void chargerRequetes() {
    currentState.chargerRequetes(this,window);
  }

  public void choixLivreur(int livreur) {
    currentState.choixLivreur(this, window, livreur);
  }

  public void calculerTournee() {
    currentState.calculerTournee(this, window);
  }

  public void creerRequete() {
    currentState.creerRequete(this, window);
  }

  public void supprimerRequete() {
    currentState.supprimerRequete(this, window);
  }

  public void sauvegarderRequetes() {
    currentState.sauvegarderRequetes(this,window);
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
}
