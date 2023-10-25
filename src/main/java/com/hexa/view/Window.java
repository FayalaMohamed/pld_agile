package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;

public class Window extends JFrame {

  protected static final String CHARGER_CARTE = "test";

  private GraphicalView graphicalView;

  private JPanel panelGauche;
  private JPanel panelDroit;

  private int width;
  private int height;

  public Window(Controller controller) {

    super();

    setTitle("AGILE H4413");

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    width = screenSize.width;
    height = screenSize.height;

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    panelGauche = new JPanel(); // remplacer par initPanelGauche(), qui le remplit
    panelGauche.setBackground(Color.blue);
    getContentPane().add(panelGauche);

    graphicalView = new GraphicalView(this);
    graphicalView.addMouseListener(new MouseListener(controller));

    panelDroit = new JPanel(); // idem
    panelDroit.setBackground(Color.red);
    getContentPane().add(panelDroit);

    setWindowSize();

    setVisible(true);
  }

  private void setWindowSize() {
    setSize(width, height);
    panelGauche.setSize(width / 5, height);
    panelDroit.setSize(width / 5, height);
    panelGauche.setLocation(0, 0);
    graphicalView.setLocation(panelGauche.getWidth(), 0);
    panelDroit.setLocation(panelGauche.getWidth() + graphicalView.getViewWidth(), 0);
    setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
  }

  private JPanel initButtons(Controller controller) {
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
