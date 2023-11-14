package com.hexa.controller;

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
import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.view.Window;

public class Controller {

//-----------------------------------------------------------------------------------------------------

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
	private final Tournee tournee;
	private Graphe carte;

	// Vue
	private final Window window;

	// Undo - Redo
	private ListOfCommands listOfCommands;

	private int nbLivreurs;

//-----------------------------------------------------------------------------------------------------

	/**
	 * Crée le controlleur de l'application
	 */
	public Controller() {
		// WARNING: The number of "livreurs" is currently hard coded
		nbLivreurs = 3;
		tournee = new Tournee();

		currentState = initialState;
		previousState = initialState;

		listOfCommands = new ListOfCommands();

		window = new Window(this, tournee);
		window.afficherMessage("Choisissez une carte à afficher");
	}

//-----------------------------------------------------------------------------------------------------

	public int getNbLivreurs() {
		return nbLivreurs;
	}

	public Tournee getTournee() {
		return tournee;
	}

	public Graphe getCarte() {
		return carte;
	}

	public ListOfCommands getListOfCommands() {
		return listOfCommands;
	}

	public State getPreviousState() {
		return previousState;
	}

	public InitialState getInitialState() {
		return initialState;
	}

	public EtatCreerRequete1 getEtatCreerRequete1() {
		return etatCreerRequete1;
	}

	public EtatCreerRequete2 getEtatCreerRequete2() {
		return etatCreerRequete2;
	}

	public EtatCreerRequete3 getEtatCreerRequete3() {
		return etatCreerRequete3;
	}

	public EtatCarteChargee getEtatCarteChargee() {
		return etatCarteChargee;
	}

	public EtatAuMoinsUneRequete getEtatAuMoinsUneRequete() {
		return etatAuMoinsUneRequete;
	}

	public ChargerCarte getChargerCarte() {
		return chargerCarte;
	}

	public EtatSupprimerRequete getEtatSupprimerRequete() {
		return etatSupprimerRequete;
	}

	public EtatChargerRequete getEtatChargerRequete() {
		return etatChargerRequete;
	}

	public EtatSauvegarderRequete getEtatSauvegarderRequete() {
		return etatSauvegarderRequete;
	}
	

//-----------------------------------------------------------------------------------------------------	
	

	public void setCurrentState(State s) {
		currentState = s;
	}

	public void setPreviousState(State s) {
		previousState = s;
	}

	public void setCarte(Graphe carte) {
		this.carte = carte;
	}

// -----------------------------------------------------------------------------------------------------

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
		currentState.clicGauche(this, window, coordonnees, listOfCommands);
	}

	/**
	 * Méthode appelée par la fenêtre après un clic droit sur la vue graphique
	 */
	public void clicDroit() {
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
	 */
	public void choixLivreur(int livreur) throws TourneeException, GrapheException {
		currentState.choixLivreur(this, window, livreur, listOfCommands);
	}

	/**
	 * Méthode appelée par la fenêtre après un clic sur le bouton "Calculer la
	 * tournée"
	 */
	public void calculerTournee() {
		currentState.calculerTournee(this, window, listOfCommands);
	}

	/**
	 * Méthode appelée par la fenêtre après un clic sur le bouton "Créer une
	 * requête"
	 */
	public void creerRequete() {
		currentState.creerRequete(this, window);
	}

	/**
	 * Méthode appelée par la fenêtre après un clic sur le bouton "Supprimer une
	 * requête"
	 */
	public void supprimerRequete() {
		currentState.supprimerRequete(this, window);
	}

	/**
	 * Méthode appelée par la fenêtre après un clic sur le bouton "Sauvegarder les
	 * requêtes"
	 */
	public void sauvegarderRequetes() {
		currentState.sauvegarderRequetes(this, window);
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
	 * Method called by window after a click on the button "Redo"
	 */
	public void redo() {
		currentState.redo(listOfCommands, this);
	}

}
