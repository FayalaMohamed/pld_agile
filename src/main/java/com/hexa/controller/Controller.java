package com.hexa.controller;

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

    public void chargerCarte() {
        currentState.chargerCarte(this, window);
    }
}
