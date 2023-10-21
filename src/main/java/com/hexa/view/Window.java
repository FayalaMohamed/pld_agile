package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Window extends JFrame{

    private ActionEvent CHARGER_CARTE;

    public Window() {

        super();
        
        setTitle("AGILE");
        setSize(1000, 666);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(initButtons());

        setVisible(true);
    }

    private JPanel initButtons() {
        JPanel panel = new JPanel();

        JButton boutonTest = new JButton("Test");
        boutonTest.addActionListener(new ButtonListener());
        CHARGER_CARTE = new ActionEvent(boutonTest, 1, "CHARGER_CARTE", ALLBITS, ABORT);
        panel.add(boutonTest);

        return panel;
    }
}
