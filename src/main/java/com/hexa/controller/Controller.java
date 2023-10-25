package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.view.Window;

public class Controller {

    private State currentState;

    private Window window;

    private InitialState initialState;
    
    public Controller() {
        
        initialState = new InitialState();
        
        currentState = initialState;
    }

    public void initController(Window w) {
        window = w;
    }

    public void setCurrentState(State s) {
        currentState = s;
    }

    public void leftClick() {
        currentState.leftClick();
    }

    public void rightClick() {
        currentState.rightClick();
    }

    public Graphe chargerCarte(String file) {

        Graphe map = currentState.chargerCarte(this, window, file);

        if (map != null) {
            System.out.println("appel charger carte");
            // window.afficherCarte(map);
        } else {
            System.out.println("erreur fichier invalide");
        }

        return map;
    }
}
