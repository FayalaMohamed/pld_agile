package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.view.Window;

public interface State {

    public Graphe chargerCarte(Controller c, Window w, String file);
    
}
