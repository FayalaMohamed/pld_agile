package com.hexa.controller;

import java.util.ArrayList;
import java.util.Arrays;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;

public class EtatCreerRequete3 implements State {
    private Intersection intersectionAjouter;

    /**
     * Rentrer dans l'état EtatCreerRequete3 et donner en paramètre l'intersection à
     * ajouter à la tournée après calcul du plus court chemin
     * 
     * @param intersection
     */
    public void entryAction(Intersection intersection) {
        this.intersectionAjouter = intersection;
    }

    /**
     * Parcourt les intersections de la carte du controlleur et si ça correspond à
     * une intersection qui appartient à la tournée (ou à l'entrepôt) passage à
     * l'état EtatCreerRequete2
     * 
     * @param c
     * @param w
     * @param coordonneesSouris
     */
    public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands l) {
        ArrayList<Intersection> intersectionsEtEntrepot = new ArrayList<>(Arrays
                .asList(c.getCarte().getIntersections()));
        Intersection entrepot = c.getCarte().getEntrepot();
        intersectionsEtEntrepot.add(entrepot);
        System.out.println("etat requete 3");
        for (Intersection intersection : intersectionsEtEntrepot) {
            // TODO: When doing graphical view, refactor the method to compute coordinates
            // not to duplicate code
            Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
            if (coord.equals(coordonneesSouris)
                    && (c.getTournee().hasLivraison(intersection) || entrepot.equals(intersection))) {
                c.etatCreerRequete2.entryAction(this.intersectionAjouter, intersection);
                c.setCurrentState(c.etatCreerRequete2);
                w.afficherMessage("Intersection sélectionnée pour la livraison : "
                        + intersectionAjouter.toString()
                        + "\nSélectionnez un livreur");
            }
        }
    }

    /** Reset le state du controlleur au previousState
     * @param c
     * @param w
     */
    public void clicDroit(Controller c, Window w) {
        w.afficherMessage("Création de requête annulée");
        c.setCurrentState(c.previousState);
        w.allow(true);
    }
}
