package com.hexa.view;

import javax.swing.JLabel;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;

import java.awt.Font;

public class VueTexteLivraison extends JLabel {
    
    private Livraison livraison;
    private TextualView tv;
    private Font font;

    VueTexteLivraison(TextualView tv, Livraison livraison, Graphe carte, Font font) {

        this.livraison = livraison;

        boolean affPlageHoraire = livraison.getPlageHoraire()[0] == 0 ? false : true;

        setFont(font);
        dessinerVue(carte, affPlageHoraire);
    }

    private void dessinerVue(Graphe carte, boolean affPlageHoraire) {
        String desc = "<html><ul><li>";
        desc += buildDescription(carte, desc, livraison.getLieu());
        if (affPlageHoraire){
            desc +="<br/> Plage horaire : " + livraison.getPlageHoraire()[0] + "h - "+livraison.getPlageHoraire()[1]+"h.";
        }
        desc += "</li></ul></html>";

        this.setText(desc);
    }

    private String buildDescription(Graphe carte, String desc, Intersection intersection) {

        Intersection[] successeurs = carte.getSuccesseur(intersection);
        Intersection[] predecesseurs = carte.getPredecesseur(intersection);

        desc = "";
        boolean first = true;
        for (Intersection succ : successeurs) {

            desc = addToDesc(desc, intersection, succ, carte, first);
            first = false;

        }

        for (Intersection pred : predecesseurs) {

            desc = addToDesc(desc, pred, intersection, carte, first);
            first = false;

        }

        desc += ".";
        return desc;
    }

    private String addToDesc(String desc, Intersection origine, Intersection destination, Graphe carte, boolean first) {
        String nom = carte.getNomSegment(new Segment(origine, destination));

        if (!desc.contains(nom) || nom.isEmpty()) {
            if (!first) {
                desc += ", ";
            }
            desc += (nom.isEmpty() ? "Rue sans nom" : nom);
        }
        return desc;
    }

    public Livraison getLivraison() {
        return livraison;
    }
}
