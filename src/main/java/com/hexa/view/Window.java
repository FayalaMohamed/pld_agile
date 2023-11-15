package com.hexa.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.util.stream.*;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatLightLaf;
import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Tournee;
import com.hexa.view.listener.BoxListener;
import com.hexa.view.listener.ButtonListener;
import com.hexa.view.listener.MouseListener;
import com.hexa.view.listener.KeyboardListener;
import com.hexa.view.listener.ZoomHandler;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;

import java.util.List;
import com.hexa.controller.state.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.util.Map;
import java.util.TreeMap;

import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

public class Window extends JFrame implements Observer {

  // -------------------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  public static final String CHARGER_CARTE = "Charger une carte";
  public static final String CREER_REQUETE = "Créer une requête";
  public static final String SUPPRIMER_REQUETES = "Supprimer une requête";
  public static final String CHARGER_REQUETES = "Charger des requêtes";
  public static final String SAUVEGARDER_REQUETES = "Sauvegarder des requêtes";
  public static final String CALCULER_TOURNEE = "Calculer la tournée";
  public static final String REDO = "Redo";
  public static final String UNDO = "Undo";

  public static final String GENERER_FEUILLE_DE_ROUTE = "Générer la feuille de route";

  private final String texteBoutons[] = { CHARGER_CARTE, CREER_REQUETE, CHARGER_REQUETES, SUPPRIMER_REQUETES,
      SAUVEGARDER_REQUETES,
      CALCULER_TOURNEE, REDO, UNDO, GENERER_FEUILLE_DE_ROUTE };

  // Une map qui associe à chaque indice de texteBoutons un bouton de l'IHM
  private Map<String, JButton> boutons;
  private final int buttonHeight = 40;
  private final int buttonWidth = 250;
  private final int messageFrameHeight = 110;
  private int textualViewWidth;

  private GraphicalView graphicalView;
  private TextualView textualView;
  private JLabel messageFrame;
  private JComboBox<String> livreurMenu;

  private MouseListener mouseListener;
  private ButtonListener buttonListener;
  private KeyboardListener keyboardListener;
  private ZoomHandler zoomHandler;

  private int width;
  private int height;

  private Controller controller;
  private boolean undoEnabled;
  private boolean redoEnabled;

  // -------------------------------------------------------------------------------------------------

  /**
   * Crée un objet Window sans Controller.
   * Pour lui donner un Controller, appeler la méthode initialiser.
   */
  public Window() {
    super();
  }

  /**
   * Crée une fenêtre avec des boutons, une zone graphique contenant un plan,
   * une zone de texte dédiée aux messages, une zone de texte dédiée aux
   * livraisons,
   * et les listeners associés aux différents éléments (boutons, comboBox, vue
   * graphique)
   *
   * @param controller
   */
  public void initialiser(Controller controller) {
    this.controller = controller;

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
    livreurMenu = new JComboBox<String>(liste_livreurs);

    BoxListener boxListener = new BoxListener(controller);
    livreurMenu.addActionListener(boxListener);
    livreurMenu.setFocusable(false);
    getContentPane().add(livreurMenu);

    graphicalView = new GraphicalView(this);

    textualView = new TextualView(this, new Tournee()); // A MODIFIER
    textualViewWidth = textualView.getViewWidth();

    messageFrame = new JLabel();
    messageFrame.setFont(UIManager.getFont("large.font"));
    messageFrame.setBorder(BorderFactory.createTitledBorder(""));
    getContentPane().add(messageFrame);

    mouseListener = new MouseListener(controller, graphicalView, this);
    addMouseListener(mouseListener);

    addMouseMotionListener(mouseListener);

    keyboardListener = new KeyboardListener(controller);
    addKeyListener(keyboardListener);

    zoomHandler = new ZoomHandler(controller, graphicalView);
    addMouseWheelListener(zoomHandler);

    controller.getListOfCommands().addObserver(this);

    afficherMessage("Choisissez une carte à afficher");

    setWindowSize();
    setVisible(true);
  }

  // -----------------------------------------------------------------------------------------------------------------------

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public GraphicalView getGraphicalView() {
    return graphicalView;
  }

  public Controller getController() {
    return controller;
  }

  // -----------------------------------------------------------------------------------------------------------------------

  /**
   * Affiche le message message
   * 
   * @param message
   */
  public void afficherMessage(String message) {
    messageFrame.setText(message);
  }

  public void hideButtons(ChargerCarte etatChargerCarte) {
    toggleAllButtons(false);
  }

  public void hideButtons(EtatAuMoinsUneRequete etatAuMoinsUneRequete) {
    toggleAllButtons(true);
    // TODO ne marche pas car etats bizarres
    boolean auMoinsUneTourneeCalculee = true; // a changer en false
    for (Tournee tournee : controller.getTournees()) {
      if (tournee.estCalculee()) {
        auMoinsUneTourneeCalculee = true;
        break;
      }
    }
    boutons.get(GENERER_FEUILLE_DE_ROUTE).setEnabled(auMoinsUneTourneeCalculee);
  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatCarteChargee
   */
  public void hideButtons(EtatCarteChargee etatCarteChargee) {
    toggleAllButtons(true);
    boutons.get(SUPPRIMER_REQUETES).setEnabled(false);
    boutons.get(CALCULER_TOURNEE).setEnabled(false);
    boutons.get(SAUVEGARDER_REQUETES).setEnabled(false);
    boutons.get(GENERER_FEUILLE_DE_ROUTE).setEnabled(false);
  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatChargerRequete
   */
  public void hideButtons(EtatChargerRequete etatChargerRequete) {
    toggleAllButtons(false);
  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatCreerRequete1
   */
  public void hideButtons(EtatCreerRequete1 etatCreerRequete1) {
    toggleAllButtons(false);

  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatCreerRequete2
   */
  public void hideButtons(EtatCreerRequete2 etatCreerRequete2) {
    toggleAllButtons(false);

  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatCreerRequete3
   */
  public void hideButtons(EtatCreerRequete3 etatCreerRequete3) {
    toggleAllButtons(false);

  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatSauvegarderRequete
   */
  public void hideButtons(EtatSauvegarderRequete etatSauvegarderRequete) {
    toggleAllButtons(false);

  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatSupprimerRequete
   */
  public void hideButtons(EtatSupprimerRequete etatSupprimerRequete) {
    toggleAllButtons(false);

  }

  /**
   * Cache les boutons selon l'état appelant
   * @param initialState
   */
  public void hideButtons(InitialState initialState) {
    toggleAllButtons(false);
    boutons.get(CHARGER_CARTE).setEnabled(true);
  }

  /**
   * Cache les boutons selon l'état appelant
   * @param etatTourneeCalculee
   */
  public void hideButtons(EtatTourneeCalculee etatTourneeCalculee) {
    toggleAllButtons(true);
  }

  /**
   * Active ou désactive tous les boutons selon shown, sauf undo et redo.
   * @param shown
   */
  private void toggleAllButtons(boolean shown) {
    for (JButton button : boutons.values()) {
      button.setEnabled(shown);
    }
    if (shown) {
      boutons.get(UNDO).setEnabled(undoEnabled);
      boutons.get(REDO).setEnabled(redoEnabled);
    }
  }

  /**
   * Affiche la carte carte
   * 
   * @param carte
   */
  public void afficherCarte(Graphe carte) {
    graphicalView.ajouterCarte(carte);
  }

  /**
   * Met l'affichage de l'intersection en mode sélectionné
   * @param intersection
   */
  public void afficherIntersectionSelectionnee(Intersection intersection) {
    graphicalView.setSelectionnee(intersection);
  }

  /**
   * Vide la liste des vues tournées
   */
  public void clearTournees() {
    this.graphicalView.clearTournees();
    //this.textualView.clearTournees();
  }

  /**
   * En cas de clic à des coordonnées où plusieurs intersections sont possibles,
   * affiche un popup demandant de choisir.
   * 
   * @param choixPossibles
   * @return l'intersection choisi
   */
  public Intersection popupChoixIntersections(List<Intersection> choixPossibles) {
    class IntersectionWrapper {
      Intersection intersection;
      String desc;

      public IntersectionWrapper(Intersection i) {
        intersection = i;
        buildDescription();
      }

      private void buildDescription() {
        Graphe carte = controller.getCarte();

        Intersection[] successeurs = carte.getSuccesseur(intersection);
        Intersection[] predecesseurs = carte.getPredecesseur(intersection);

        desc = "";
        boolean first = true;
        for (Intersection succ : successeurs) {

          addToDesc(intersection, succ, carte, first);
          first = false;

        }

        for (Intersection pred : predecesseurs) {

          addToDesc(pred, intersection, carte, first);
          first = false;

        }

        desc += ".";

      }

      private void addToDesc(Intersection origine, Intersection destination, Graphe carte, boolean first) {
        String nom = carte.getNomSegment(new Segment(origine, destination));

        if (!desc.contains(nom) || nom.equals("")) {
          if (!first) {
            desc += ", ";
          }
          desc += (nom.equals("") ? "Rue sans nom" : nom);
        }
      }

      public String toString() {
        return desc;
      }
    }

    List<IntersectionWrapper> choixPossiblesWrapped = choixPossibles.stream().map(IntersectionWrapper::new)
        .collect(Collectors.toList());
    System.out.println("window joption:" + choixPossibles);
    System.out.println("window joption:" + choixPossiblesWrapped);
    Object[] possibilities = choixPossiblesWrapped.toArray();
    IntersectionWrapper choix = (IntersectionWrapper) JOptionPane.showInputDialog(this,
        "Plusieurs intersections sont possibles.\n" + "Veuillez choisir une intersection :",
        "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities, null);

    return choix == null ? null : choix.intersection;
  }

  // -----------------------------------------------------------------------------------------------------------------------

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

    livreurMenu.setSize(buttonWidth, buttonHeight);
    livreurMenu.setLocation(5, (boutons.size() + 1) * buttonHeight);

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
    boutons = new TreeMap<String, JButton>();

    for (String text : texteBoutons) {
      JButton bouton = new JButton(text);
      boutons.put(text, bouton);
      bouton.setSize(buttonWidth, buttonHeight);
      bouton.setLocation(5, (boutons.size() - 1) * buttonHeight);
      bouton.setFocusable(false);
      bouton.setFocusPainted(false);
      bouton.addActionListener(buttonListener);
      getContentPane().add(bouton);
    }
  }

  /**
   * La Window peut se faire update oar une ListOfCommands.
   * Cela lui permet d'activer ou non les boutons undo/redo.
   * @param o
   * @param arg
   */
  public void update(Observable o, Object arg) {
    undoEnabled = ((ListOfCommands) o).canUndo();
    redoEnabled = ((ListOfCommands) o).canRedo();
    boutons.get(UNDO).setEnabled(undoEnabled);
    boutons.get(REDO).setEnabled(redoEnabled);

  }

  /**
   * Retourne l'intersection sur laquelle l'utilisateur a cliqué.
   * Dékègue le traitement à la graphicalView
   * 
   * @param coordonneesSouris
   * @return
   */
  public List<Intersection> getIntersectionSelectionnee(Coordonnees coordonneesSouris) {
    return graphicalView.getIntersectionSelectionnee(coordonneesSouris);
  }
  
}
