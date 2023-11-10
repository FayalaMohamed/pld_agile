package com.hexa.controller;

import com.hexa.model.Circuit;
import com.hexa.model.Tournee;

public class CircuitRequete {

    private Tournee tournee;
    private Circuit circuit;


    public CircuitRequete(Tournee tournee, Circuit circuit) {
        this.tournee = tournee;
        this.circuit = circuit;
    }


}
