package com.hexa.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.hexa.model.*;
import com.hexa.view.listener.MouseListenerTextualView;

public class VueTexteTournee extends JPanel {

    private TextualView tv;
    private int width;
    private int height;
    Font font;

    private Tournee tournee;
    private ArrayList<VueTexteLivraison> vuesLivraisons = new ArrayList<VueTexteLivraison>();

    public VueTexteTournee(Tournee tournee, TextualView tv, Font font) {
        this.tournee = tournee;
        this.tv = tv;
        this.font = font;

        this.width = tv.getWidth()-50;
        this.height = tv.getHeight();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.WHITE);
        setFont(font);

        //setPreferredSize(new Dimension(width, 200));
        //setBackground(Color.RED);
    }

    public VueTexteTournee dessinerVue(Graphe carte, boolean redessine) {
        if (!redessine) {
            return this;
        }

        this.removeAll();
        JLabel labelTournee = new JLabel("Tournée livreur "+tournee.getLivreur().getId());
        labelTournee.setFont(font);
        this.add(labelTournee);
        for (Livraison l : this.tournee.getLivraisons()) {
            String desc = "<html><ul><li>";
            desc += buildDescription(carte, desc, l.getLieu());
            if (tournee.estCalculee()){
                desc +="<br/> Plage horaire : " + l.getPlageHoraire()[0] + "h - "+l.getPlageHoraire()[1]+"h.";
            }
            desc += "</li></ul></html>";

            JLabel labelLivraison = new JLabel(desc);
            labelLivraison.setFont(font);
            this.add(labelLivraison);
        }
        this.add(new JLabel("<html><br></html>"));
        revalidate();
        repaint();
        return this;
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    public Tournee getTournee() {
        return tournee;
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



}

/*
TODO REMARQUES
- Menu de sélection des livreurs : changer pour qu'une fois sélectionné, le livreur s'applique à la création des requêtes
  et que le choix par défaut soit aucun livreur sélectionné
- Augmenter la taille des polices
- Augmenter la taille des numéros des livraisons sur la graphicalView
- Bordures à rendre plus importantes
 */