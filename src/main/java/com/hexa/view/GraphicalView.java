package com.hexa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

public class GraphicalView extends JPanel implements Observer {

  private static final long serialVersionUID = 1L;
  private int viewHeight;
  private int viewWidth;
  private Graphics g;
  private Tournee tournee;

  private Graphe carte;

  private ArrayList<VueIntersection> vuesIntersections = new ArrayList<VueIntersection>();
  private ArrayList<VueSegment> vuesSegments = new ArrayList<VueSegment>();
  private VueEntrepot vueEntrepot;
  private VueTournee vueTournee;

  private double latitudeMin;
  private double latitudeMax;
  private double longitudeMin;
  private double longitudeMax;

  private Coordonnees coordonneesMin;
  private Coordonnees coordonneesMax;

  private int viewX = 0;
  private int viewY = 0;
  private double zoomFactor = 1.0;

  /**
   * Crée la vue graphique correspondant à une tournée dans une fenêtre
   * 
   * @param w
   * @param tournee
   */
  public GraphicalView(Window w) {
    super();

    for (Tournee tournee : w.getController().getTournees()) {
      tournee.addObserver(this);
    }

    viewWidth = 1000;
    viewHeight = 700;

    setSize(viewWidth, viewHeight);
    setBackground(Color.white);
    w.getContentPane().add(this);
  }

  /**
   * Méthode appelée par les objets observés par GraphicalView à chaque mise à
   * jour de ces derniers
   * 
   * @param o
   * @param arg
   */
  @Override
  public void update(Observable o, Object arg) {
    
    vueTournee = new VueTournee((Tournee)arg, this, Color.RED);

    //aucun requête ne peut plus être sélectionnée fonctionnellement
    for(VueIntersection vi : vuesIntersections) {
      vi.afficherNonSelectionnee();
    }
    
    repaint();
  }

  /**
   * Ajoute une carte à la vue
   * 
   * @param carte
   */
  public void ajouterCarte(Graphe carte) {

    this.carte = carte;

	  initialiserVues(carte);

    latitudeMax = -90;
    latitudeMin = 90;
    longitudeMax = -180;
    longitudeMin = 180;

    coordonneesMin = new Coordonnees(0, viewHeight);
    coordonneesMax = new Coordonnees(
        (int) ((longitudeMax - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth),
        (int) (viewHeight - ((latitudeMax - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight)));
    
    definirExtremesCoordonnees();
    repaint();
  }

  /**
   * Crée toutes les vues pour les objets de la carte (segments, intersections, entrepôt...)
   * @param carte
   */
  public void initialiserVues(Graphe carte) {

    //entrepot
    vueEntrepot = new VueEntrepot(carte.getEntrepot(), this);

    //intersections
    for(Intersection i : carte.getIntersections()) {
      vuesIntersections.add(new VueIntersection(i, this));
    }

    //segments
    for(Segment s : carte.getSegments()) {
      vuesSegments.add(new VueSegment(s, this, Color.BLUE));
    }
  }

  /**
   * Retourne l'intersection sélectionnée par le clic souris
   * Met à jour la vue de cette intersection
   * @param coordonneesSouris
   * @return
   */
  public Intersection getIntersectionSelectionnee(Coordonnees coordonneesSouris) {

    Intersection intersectionSelec = null;

    for (VueIntersection vi : vuesIntersections) {
      if (vi.estCliquee(coordonneesSouris)) {
        vi.afficherSelectionnee();
        intersectionSelec = vi.getIntersection();
      } else {
        vi.afficherNonSelectionnee();
      }
    }

    repaint();

    return intersectionSelec;
  }

  /**
   * Méthode déterminant les plus grandes coordonnées de la carte choisie Permet
   * de définir l'échelle de la vue graphique
   */
  private void definirExtremesCoordonnees() {
    
    for(Intersection intersection : carte.getIntersections()) {
      double latitude = intersection.getLatitude();
      double longitude = intersection.getLongitude();
      if (latitude > latitudeMax)
        latitudeMax = latitude;
      if (latitude < latitudeMin)
        latitudeMin = latitude;
      if (longitude > longitudeMax)
        longitudeMax = longitude;
      if (longitude < longitudeMin)
        longitudeMin = longitude;
    }
    
    System.out.println("latitude min : " + latitudeMin + " / latitude max : " + latitudeMax);
    System.out.println("longitude min : " + longitudeMin + " / longitude max : " + longitudeMax);
    coordonneesMin = new Coordonnees(0, viewHeight);
    coordonneesMax = new Coordonnees(
        (int) ((longitudeMax - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth),
        (int) (viewHeight - ((latitudeMax - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight)));
  }

  // private void translateView() {
  //   Graphics2D g2d = (Graphics2D) g;
  //   g2d.translate(viewX, viewY);
  //   // Appliquer le facteur de zoom
  //   g2d.scale(zoomFactor, zoomFactor);
  // }

  /**
   * Méthode à appeler à chaque fois que la vue graphique doit être redessinée
   * 
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.g = g;
    // translateView();

    if (carte != null) {
      vueEntrepot.dessinerVue();
      
      for (VueIntersection vi : vuesIntersections) {
        vi.dessinerVue();
      }

      for (VueSegment vs : vuesSegments) {
        vs.dessinerVue();
      }

      if (vueTournee != null)
        vueTournee.dessinerVue();
    }
  }

  /**
   * Méthode traduisant des coordonnées GPS en coordonnées en pixels pour
   * l'affichage graphique
   * 
   * @param i
   * @return
   */
  public Coordonnees CoordGPSToViewPos(Intersection i) {
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));

    // System.out.println("xpos=" + xpos + " / ypos=" + ypos);

    xpos = (int) (xpos * zoomFactor) + viewX;
    ypos = (int) (ypos * zoomFactor) + viewY;

    // System.out.println("xpos=" + xpos + " / ypos=" + ypos);

    return new Coordonnees(xpos, ypos);
  }

  public int getViewHeight() {
    return viewHeight;
  }

  public int getViewWidth() {
    return viewWidth;
  }

  public void setZoomFactor(int notches) {
    double temp = zoomFactor;
    if (notches < 0) {
      // Zoom in
      zoomFactor *= 1.1;
      if (zoomFactor > 4) {
        zoomFactor = temp;
      }
    } else {
      // Zoom out
      zoomFactor /= 1.1;
      if (zoomFactor < 0.98) {
        zoomFactor = temp;
      } else {
        viewX = 0;
        viewY = 0;
      }
    }
    repaint();
  }

  public void setDrag(Coordonnees coordonnees, Coordonnees dernieresCoordonnees) {
    if (zoomFactor != 1.0) {
      int newViewX = viewX + coordonnees.getX() - dernieresCoordonnees.getX();
      int newViewY = viewY + coordonnees.getY() - dernieresCoordonnees.getY();
      if (newViewX < -(coordonneesMax.getX() * (zoomFactor - 1))) {
        newViewX = viewX;
        System.out.println("Limite A atteinte");
      }
      if (newViewX > coordonneesMin.getX() * (zoomFactor - 1)) {
        newViewX = viewX;
        System.out.println("Limite B atteinte");
      }
      if (newViewY < -coordonneesMin.getY() * (zoomFactor - 1)) {
        newViewY = viewY;
        System.out.println("Limite C atteinte");
      }
      if (newViewY > coordonneesMax.getY() * (zoomFactor - 1)) {
        newViewY = viewY;
        System.out.println("Limite D atteinte");
      }
      viewX = newViewX;
      viewY = newViewY;
      repaint();
    }
  }

  public Graphics getGraphics2() {
	  return g;
  }

  public double getZoomFactor() {
    return zoomFactor;
  }
  
  

}
