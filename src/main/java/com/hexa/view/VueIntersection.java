package com.hexa.view;

import java.awt.Color;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.observer.Observable;

public class VueIntersection extends Observable {

    private GraphicalView gv;

    private Intersection intersection;

    private Color color = Color.BLUE;
    private int rayon = 6;
    private Coordonnees coord;

    public VueIntersection(Intersection i, GraphicalView gv){
        this.gv = gv;
        this.intersection = i;
    }

    public void dessinerVue() {

        coord = this.gv.CoordGPSToViewPos(intersection);

        gv.getGraphics().setColor(color);
        gv.getGraphics().fillOval(coord.getX() - rayon, coord.getY() - rayon, 2 * rayon, 2 * rayon);
    }

    public boolean estCliquee(Coordonnees coordonneesSouris) {
        return coord.equals(coordonneesSouris);
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void afficherSelectionnee() {
        color = Color.ORANGE;
        dessinerVue();
    }

    public void afficherNonSelectionnee() {
        color = Color.BLUE;
        dessinerVue();
    }
}
