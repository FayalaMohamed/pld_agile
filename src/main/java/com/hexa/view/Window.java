package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import com.hexa.controller.Controller;
import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Tournee;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Window extends JFrame {

  protected static final String CHARGER_CARTE = "Charger une carte";
  protected static final String CREER_REQUETE = "Créer une requête";
  protected static final String SUPPRIMER_REQUETES = "Supprimer une requête";
  protected static final String CHARGER_REQUETES = "Charger des requêtes";
  protected static final String SAUVEGARDER_REQUETES = "Sauvegarder des requêtes";
  protected static final String CALCULER_TOURNEE = "Calculer la tournée";

  private final String texteBoutons[] = { CHARGER_CARTE, CREER_REQUETE, CHARGER_REQUETES, SUPPRIMER_REQUETES,
      SAUVEGARDER_REQUETES,
      CALCULER_TOURNEE };

  private ArrayList<JButton> boutons;
  private final int buttonHeight = 40;
  private final int buttonWidth = 250;
  private final int messageFrameHeight = 110;
  private final int textualViewWidth = 400;

  private GraphicalView graphicalView;
  private TextualView textualView;
  private JLabel messageFrame;
  private JComboBox livreurMenue;

  private MouseListener mouseListener;
  private ButtonListener buttonListener;
  private ZoomHandler zoomHandler;

  private int width;
  private int height;

  /**
   * Crée une fenêtre avec des boutons, une zone graphique contenant un plan,
   * une zone de texte dédiée aux messages, une zone de texte dédiée aux
   * livraisons,
   * et les listeners associés aux différents éléments (boutons, comboBox, vue
   * graphique)
   * 
   * @param controller
   * @param t
   */
  public Window(Controller controller, Tournee t) {

    super();

    FlatLightLaf.setup();

    setTitle("AGILE H4113");
    setLayout(null);
    initBoutons(controller);
    // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    String[] liste_livreurs = new String[controller.getNbLivreurs()];
    for (int i = 0; i < controller.getNbLivreurs(); ++i) {
      liste_livreurs[i] = Integer.toString(i);
    }
    JLabel label = new JLabel("Livreurs");
    label.setSize(buttonWidth, buttonHeight);
    label.setLocation(5, boutons.size() * buttonHeight);
    getContentPane().add(label);
    livreurMenue = new JComboBox<String>(liste_livreurs);
    livreurMenue.setSize(buttonWidth, buttonHeight);
    livreurMenue.setLocation(5, (boutons.size() + 1) * buttonHeight);
    livreurMenue.setFocusable(false);
    BoxListener boxListener = new BoxListener(controller);
    livreurMenue.addActionListener(boxListener);
    getContentPane().add(livreurMenue);

    graphicalView = new GraphicalView(this, t);
    textualView = new TextualView(this, t);
    messageFrame = new JLabel();
    messageFrame.setFont(UIManager.getFont("large.font"));
    messageFrame.setBorder(BorderFactory.createTitledBorder(""));
    getContentPane().add(messageFrame);

    mouseListener = new MouseListener(controller, graphicalView, this);
    addMouseListener(mouseListener);

    addMouseMotionListener(mouseListener);

    zoomHandler = new ZoomHandler(controller, graphicalView);
    addMouseWheelListener(zoomHandler);

    setWindowSize();
    setVisible(true);
  }

  /**
   * Définit la taille de la fenêtre et de ses éléments
   */
  private void setWindowSize() {
    int allButtonHeight = buttonHeight * texteBoutons.length;
    height = Math.max(graphicalView.getViewHeight(), allButtonHeight) + messageFrameHeight;
    width = graphicalView.getViewWidth() + buttonWidth + 10 + textualViewWidth;
    setSize(width, height);
    graphicalView.setLocation(buttonWidth + 10, 0);
    messageFrame.setSize(width, messageFrameHeight);
    messageFrame.setLocation(0, height - messageFrameHeight);
    textualView.setSize(textualViewWidth, height - messageFrameHeight);
    textualView.setLocation(10 + graphicalView.getViewWidth() + buttonWidth, 0);
    setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
  }

  /**
   * Initialise les boutons pour le contrôleur controller avec des listeners
   * 
   * @param controller
   */
  private void initBoutons(Controller controller) {

    buttonListener = new ButtonListener(controller);
    boutons = new ArrayList<JButton>();

    for (String text : texteBoutons) {
      JButton bouton = new JButton(text);
      boutons.add(bouton);
      bouton.setSize(buttonWidth, buttonHeight);
      bouton.setLocation(5, (boutons.size() - 1) * buttonHeight);
      bouton.setFocusable(false);
      bouton.setFocusPainted(false);
      bouton.addActionListener(buttonListener);
      getContentPane().add(bouton);
    }
  }

  /**
   * Retourne l'intersection sur laquelle l'utilisateur a cliqué.
   * Dékègue le traitement à la graphicalView
   * @param coordonneesSouris
   * @return
   */
  public Intersection getIntersectionSelectionnee(Coordonnees coordonneesSouris) {
    return graphicalView.getIntersectionSelectionnee(coordonneesSouris);
  }

  /**
   * Affiche le message message
   * 
   * @param message
   */
  public void afficherMessage(String message) {
    messageFrame.setText(message);
  }

  /**
   * Activate buttons if b = true, unable them otherwise
   * 
   * @param b
   */
  public void allow(Boolean b) {
    System.out.println("Buttons have been enabled : " + b);
    for (JButton bouton : boutons)
      bouton.setEnabled(b);
  }

  /**
   * Affiche la carte carte
   * 
   * @param carte
   */
  public void afficherCarte(Graphe carte) {
    graphicalView.ajouterCarte(carte);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public GraphicalView getGraphicalView() {
    return graphicalView;
  }
}
