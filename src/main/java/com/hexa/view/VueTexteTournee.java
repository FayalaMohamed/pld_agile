package com.hexa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

        this.width = tv.getWidth();
        this.height = tv.getHeight();

        setPreferredSize(new Dimension(400, 200));
        setBackground(Color.RED);
    }

    public VueTexteTournee dessinerVue() {

        JLabel testAfftournee = new JLabel("affichage d'un tournée");
        this.add(testAfftournee);

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
