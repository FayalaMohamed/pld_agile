package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.view.Window;

public class InitialState implements State {
    
    public Graphe chargerCarte(Controller c, Window w, String file) {
        
        System.out.println("Making a graph from the file : " + file);

        Graphe map = new Graphe();
        try {
            map = XMLParser.xmlToGraphe(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        w.afficherCarte(map);

        return map;
    }
}
