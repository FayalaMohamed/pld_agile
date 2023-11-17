package com.hexa.view.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.hexa.model.*;
import com.hexa.view.TextualView;

/**
 * Objet encapsulant l'objet tournee et permettant son affichage dans la vue textuelle.
 */
public class VueTexteTournee extends JPanel {

    private static final long serialVersionUID = 1L;
	private TextualView tv;
    private int width;
    private int height;
    private Font font;

    private Tournee tournee;
    private ArrayList<VueTexteLivraison> vuesLivraisons = new ArrayList<VueTexteLivraison>();

    /**
     * Initialise une VueTexteTournee pour une tournée particulière, avec une textualView et une police.
     * @param tournee
     * @param tv
     * @param font
     */
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

    /**
     * Remplit le panel contenu dans la classe avec les caractéristiques de la Tournee
     * @param carte
     * @param redessine
     * @return
     */
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