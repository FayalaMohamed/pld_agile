package com.hexa.controller;

import com.hexa.view.Window;

public class Controller {

    private State currentState;

    private Window window;

    private InitialState initialState;
    
    public Controller(Window w) {
        
        initialState = new InitialState();
        
        window = w;
        currentState = initialState;
    }

    public void setCurrentState(State s) {
        currentState = s;
    }

    public void chargerCarte() {
        currentState.chargerCarte(this, window);
    }
}
