package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Window extends JFrame {

  protected static final String CHARGER_CARTE = "Charger un carte";
  protected static final String CREER_REQUETE = "Créer une requête";
  protected static final String SUPPRIMER_REQUETES = "Supprimer une requête";
  protected static final String CHARGER_REQUETES = "Charger des requêtes";
  protected static final String SAUVEGARDER_REQUETES = "Sauvegarder des requêtes";
  protected static final String CALCULER_TOURNEE = "Calculer la tournée";

  private final String texteBoutons[] = {CHARGER_CARTE, CREER_REQUETE, CHARGER_REQUETES, SUPPRIMER_REQUETES, SAUVEGARDER_REQUETES,
                                        CALCULER_TOURNEE};

  private ArrayList<JButton> boutons;
	private final int buttonHeight = 40;
	private final int buttonWidth = 250;
  private final int messageFrameHeight = 110;

  private GraphicalView graphicalView;
	private JLabel messageFrame;

  private MouseListener mouseListener;
  private ButtonListener buttonListener;

  private int width;
  private int height;

  public Window(Controller controller) {

    super();

    setTitle("AGILE H4113");
    setLayout(null);
    initBoutons(controller);
    // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    graphicalView = new GraphicalView(this);
		messageFrame = new JLabel();
		messageFrame.setBorder(BorderFactory.createTitledBorder("Messages..."));
		getContentPane().add(messageFrame);

    mouseListener = new MouseListener(controller, graphicalView, this);
    addMouseListener(mouseListener);

    setWindowSize();
    setVisible(true);
  }

  private void setWindowSize() {
    int allButtonHeight = buttonHeight*texteBoutons.length;
		height = Math.max(graphicalView.getViewHeight(), allButtonHeight)+messageFrameHeight;
		width = graphicalView.getViewWidth() + buttonWidth + 10;
    setSize(width, height);
    graphicalView.setLocation(buttonWidth+10, 0);
		messageFrame.setSize(width,messageFrameHeight);
		messageFrame.setLocation(0,height-messageFrameHeight);
    setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
  }

  private void initBoutons(Controller controller) {
    
    buttonListener = new ButtonListener(controller);
		boutons = new ArrayList<JButton>();
		
    for (String text : texteBoutons){
			JButton bouton = new JButton(text);
			boutons.add(bouton);
			bouton.setSize(buttonWidth,buttonHeight);
			bouton.setLocation(5,(boutons.size()-1)*buttonHeight);
			bouton.setFocusable(false);
			bouton.setFocusPainted(false);
			bouton.addActionListener(buttonListener);
			getContentPane().add(bouton);
		}
  }

  public void afficherMessage(String message) {
    messageFrame.setText(message);
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
