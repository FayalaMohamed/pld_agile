package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Window extends JFrame{

    private ActionEvent CHARGER_CARTE;

    private Controller controller;

    private GraphicalView graphicalView;

    private int width;
    private int height;

    public Window() {

        super();
        
        setTitle("AGILE");

        width = 1200;
        height = 700;
        setSize(width, height);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(initFenetre());

        setVisible(true);
    }

    public void initWindow(Controller c) {
        controller = c;
    }

    private JPanel initFenetre() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        panel.setPreferredSize(new Dimension(width, height));

        JPanel panneauGauche = new JPanel(new GridBagLayout());
        panneauGauche.setBackground(new Color(100, 0, 0));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(panneauGauche, c);

        JPanel panneauCarte = new JPanel();
        panneauCarte.setBackground(new Color(0, 100, 0));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 3;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(panneauCarte, c);

        JPanel panneauDroit = new JPanel(new GridBagLayout());
        panneauDroit.setBackground(new Color(0, 0,100));

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(panneauDroit, c);

        return panel;
    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton boutonTest = new JButton("Test");
        boutonTest.addActionListener(new ButtonListener(controller));
        CHARGER_CARTE = new ActionEvent(boutonTest, 1, "CHARGER_CARTE", ALLBITS, ABORT);
        panel.add(boutonTest);

        return panel;
    }

    public void afficherCarte(Graphe carte) {
        graphicalView.afficherCarte(carte);
    }
}
