package com.hexa.view.object;

import java.awt.Color;

import com.hexa.model.Coordonnees;
import com.hexa.model.Entrepot;
import com.hexa.model.Intersection;
import com.hexa.view.GraphicalView;

/**
 * Classe encapsulant l'objet entrepôt et permettant son affichage dans la vue graphique
 */
public class VueEntrepot {

    private GraphicalView gv;

    private Entrepot entrepot;

    private Color color = Color.GREEN;
    private int rayon = 6;

    private Coordonnees coord;

    public VueEntrepot(Entrepot entrepot, GraphicalView gv) {
        this.gv = gv;
        this.entrepot = entrepot;
    }

    /**
     * Dessine l'entrepot dans la vue graphique
     */
    public void dessinerVue() {

        coord = this.gv.CoordGPSToViewPos(entrepot);

        gv.getGraphics2().setColor(color);
        gv.getGraphics2().fillOval(coord.getX() - rayon, coord.getY() - rayon, 2 * rayon, 2 * rayon);
    }

    public boolean estCliquee(Coordonnees coordonneesSouris) {
        return coord.equals(coordonneesSouris);
    }

    public Intersection getIntersection() {
        return entrepot;
    }
}
