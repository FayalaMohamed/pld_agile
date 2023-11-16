package com.hexa.view.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.hexa.model.*;
import com.hexa.view.TextualView;

public class VueTexteTournee extends JPanel {

    private static final long serialVersionUID = 1L;
	private TextualView tv;
    private int width;
    private int height;
    private Font font;

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
    }

    public VueTexteTournee dessinerVue(Graphe carte, boolean redessine) {
        if (!redessine) {
            return this;
        }

        this.removeAll();
        JLabel labelTournee = new JLabel("Tournée livreur " + tournee.getLivreur().getId());
        labelTournee.setFont(font);
        this.add(labelTournee);

        for (Livraison l : this.tournee.getLivraisons()) {
            VueTexteLivraison vtl = new VueTexteLivraison(tv, l, carte, font);
            vuesLivraisons.add(vtl);
            this.add(vtl);
        }

        this.add(new JLabel("<html><br></html>"));
        revalidate();
        repaint();
        return this;
    }

    /**
     * Highlight ou enlève le highlight de la livraison l
     * @param l
     * @param highlight
     */
    public void highlightLivraison(Livraison l, boolean highlight) {
        for (VueTexteLivraison vtl : this.vuesLivraisons) {
            if (vtl.getLivraison().equals(l)) {
                vtl.highlight(highlight);
            }
        }
    }

    /**
     * Retourne la VueTexteLivraison correspondant à la livraison passée en paramètre
     * @param l
     * @return
     */
    public VueTexteLivraison getVueLivraisonCorrespondante(Livraison l) {
        for (VueTexteLivraison vtl : this.vuesLivraisons) {
            if (vtl.getLivraison().equals(l)) {
                return vtl;
            }
        }
        return null;
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    public Tournee getTournee() {
        return tournee;
    }

    public ArrayList<VueTexteLivraison> getVuesLivraisons() {
        return vuesLivraisons;
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