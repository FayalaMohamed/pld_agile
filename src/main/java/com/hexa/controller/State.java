package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.view.Window;

public interface State {

    public default void leftClick() {}
    public default void rightClick() {}
    public default Graphe chargerCarte(Controller c, Window w, String file) { return null; } //!!!!! il faut pas la alisser s'exécuter en défaut
}
