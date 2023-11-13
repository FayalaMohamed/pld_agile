package com.hexa.view;

import java.awt.Color;
import java.util.ArrayList;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;

public class VueTournee {

    private GraphicalView gv;

    private Tournee tournee;
    private ArrayList<VueLivraison> vuesLivraisons = new ArrayList<VueLivraison>();
    private VueCircuit vueCircuit;

    private Color color;

    public VueTournee(Tournee tournee, GraphicalView gv, Color color) {
        this.tournee = tournee;
        this.gv = gv;
        this.color = color;

        for (Livraison l : this.tournee.getLivraisons()) {
            vuesLivraisons.add(new VueLivraison(l, this.gv, this.color));
        }
    }

    public void dessinerVue() {

        for (VueLivraison vl : vuesLivraisons) {
            vl.dessinerVue();
        }
        if (tournee.estCalculee()) {
            try {
                vueCircuit = new VueCircuit(tournee.getCircuit(), tournee.getLivraisons(), gv, color);
                vueCircuit.dessinerVue();
            } catch (TourneeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
