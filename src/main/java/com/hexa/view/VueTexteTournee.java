package com.hexa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;

public class VueTexteTournee extends JPanel {

    private TextualView tv;
    private int width;
    private int height;

    private Tournee tournee;
    private ArrayList<VueTexteLivraison> vuesLivraisons = new ArrayList<VueTexteLivraison>();

    public VueTexteTournee(Tournee tournee, TextualView tv) {
        this.tournee = tournee;
        this.tv = tv;

        this.width = tv.getWidth()-50;
        this.height = tv.getHeight();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //setPreferredSize(new Dimension(width, 200));
        setBackground(Color.RED);
    }

    public VueTexteTournee dessinerVue(boolean redessine) {
        if (!redessine) {
            return this;
        }

        this.removeAll();
        this.add(new JLabel("Tournée livreur "+tournee.getLivreur().getId()));
        for (Livraison l : this.tournee.getLivraisons()) {
            JLabel labelLivraison = new JLabel(l.toString());
            this.add(labelLivraison);
        }
        revalidate();
        return this;
    }

    public void setTournee(Tournee tournee) {
        System.out.println("tournée màj");
        this.tournee = tournee;
    }

    public Tournee getTournee() {
        return tournee;
    }
    
}
