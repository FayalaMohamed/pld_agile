package com.hexa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hexa.model.Coordonnees;
import com.hexa.model.Livraison;
import com.hexa.observer.Observable;

public class VueLivraison extends Observable {

    private GraphicalView gv;

    private Livraison livraison;
    private boolean dansTourneeCalculee;
    private int numero;

    private Color color;
    private int rayon = 6;
    private Coordonnees coord;

    public VueLivraison(Livraison l, GraphicalView gv, Color color){
        this.gv = gv;
        this.livraison = l;
        this.color = color;
        this.dansTourneeCalculee = false;
    }

    public VueLivraison(Livraison l, GraphicalView gv, Color color, int numero){
        this.gv = gv;
        this.livraison = l;
        this.color = color;
        this.dansTourneeCalculee = true;
        this.numero = numero;
    }

    public void dessinerVue() {

        coord = this.gv.CoordGPSToViewPos(livraison.getLieu());

        Graphics g = gv.getGraphics();
        g.setColor(color);
        g.fillOval(coord.getX() - rayon, coord.getY() - rayon, 2 * rayon, 2 * rayon);

        if (dansTourneeCalculee) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.BOLD, (int) (25 / gv.getZoomFactor() + 1)));
            g.drawString(String.valueOf(numero), coord.getX() + rayon, coord.getY() + rayon);
        }
    }

    public boolean estCliquee(Coordonnees coordonneesSouris) {
        return coord.equals(coordonneesSouris);
    }

    public Livraison getLivraison() {
        return livraison;
    }
}
