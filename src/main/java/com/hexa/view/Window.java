package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;

public class Window extends JFrame{

    protected static final String CHARGER_CARTE = "test";

    private Controller controller;

    private GraphicalView graphicalView;

    private JPanel panelGauche;
    private JPanel panelDroit;

    private int width;
    private int height;

    public Window() {

        super();
        
        setTitle("AGILE");

        width = 1200;
        height = 700;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        panelGauche = new JPanel(); //remplacer par initPanelGauche(), qui le remplit
        panelGauche.setBackground(Color.blue);
        getContentPane().add(panelGauche);

        graphicalView = new GraphicalView(this);

        panelDroit = new JPanel(); //idem
        panelDroit.setBackground(Color.red);
        getContentPane().add(panelDroit);

        setWindowSize();

        setVisible(true);
    }

    public void initWindow(Controller c) {
        controller = c;
    }

    private void setWindowSize() {
        setSize(width, height);
        panelGauche.setSize(width/5, height);
        panelDroit.setSize(width/5, height);
        panelGauche.setLocation(0, 0);
        graphicalView.setLocation(panelGauche.getWidth(), 0);
        panelDroit.setLocation(panelGauche.getWidth()+graphicalView.getViewWidth(), 0);

    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton boutonTest = new JButton("Test");
        boutonTest.addActionListener(new ButtonListener(controller));
        panel.add(boutonTest);

        return panel;
    }

    public void afficherCarte(Graphe carte) {
        graphicalView.ajouterCarte(carte);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
