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
  public GraphicalView(Window w, Tournee tournee) {
    super();
    tournee.addObserver(this);
    this.tournee = tournee;

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
    
    vueTournee = new VueTournee(tournee, this, Color.RED);
    
    repaint();
  }

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

    return intersectionSelec;
  }

  // public void display(Intersection i, Color c, int number) {
  //   int r = 2;
  //   if (c.equals(Color.red) || c.equals(Color.green)) {
  //     r = 6;
  //   }
  //   int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
  //   int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
  //   g.setColor(c);
  //   g.fillOval(xpos - r, ypos - r, 2 * r, 2 * r);
  //   if (number != -1) {
  //     g.setColor(Color.black);
  //     g.setFont(new Font("TimesRoman", Font.BOLD, (int) (25 / zoomFactor + 1)));
  //     g.drawString(String.valueOf(number), xpos + r, ypos + r);
  //   }
  // }

  // public void display(Segment s, Color c) {
  //   Intersection origine = s.getOrigine();
  //   Intersection destination = s.getDestination();
  //   int xOrigine = (int) ((origine.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
  //   int yOrigine = (int) (viewHeight
  //       - ((origine.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
  //   int xDestination = (int) ((destination.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin)
  //       * viewWidth);
  //   int yDestination = (int) (viewHeight
  //       - ((destination.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));

  //   g.setColor(c);
  //   if (c == Color.red || c == Color.green) {
  //     Graphics2D g2 = (Graphics2D) g;
  //     g2.setStroke(new BasicStroke(3));
  //     g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));
  //   } else {
  //     g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
  //   }
  // }

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

  private void translateView() {
    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(viewX, viewY);
    // Appliquer le facteur de zoom
    g2d.scale(zoomFactor, zoomFactor);
  }

  // private boolean isAlreadyVisited(Segment seg, Multimap<Intersection, Intersection> segments_tournee, boolean origin) {
  //   boolean already_visited = false;
  //   Collection<Intersection> entry_intersections;
  //   if (origin) {
  //     entry_intersections = segments_tournee.get(seg.getOrigine());
  //   } else {
  //     entry_intersections = segments_tournee.get(seg.getDestination());
  //   }
  //   for (Intersection inter_destination : entry_intersections) {
  //     Intersection compare;
  //     if (origin) {
  //       compare = seg.getDestination();
  //     } else {
  //       compare = seg.getOrigine();
  //     }
  //     if (inter_destination == compare) {
  //       already_visited = true;
  //       break;
  //     }
  //   }
  //   return already_visited;
  // }

  // private void displayTournee() {
  //   Multimap<Intersection, Intersection> segments_tournee = ArrayListMultimap.create();
  //   try {
  //     Circuit circuit = tournee.getCircuit();
  //     int i = 1;
  //     while (circuit.hasNext()) {
  //       Segment seg = circuit.next();
  //       Color color = Color.red;
  //       boolean already_visited = isAlreadyVisited(seg, segments_tournee, true);
  //       if (!already_visited) {
  //         already_visited = isAlreadyVisited(seg, segments_tournee, false);
  //       }
  //       if (already_visited) {
  //         color = Color.green;
  //       } else {
  //         segments_tournee.put(seg.getOrigine(), seg.getDestination());
  //       }
  //       Intersection inter = seg.getDestination();
  //       display(seg, color);
  //       if (tournee.estLieuLivraison(inter)) {
  //         display(inter, color, i++);
  //       }
  //     }
  //   } catch (TourneeException e) {
  //     for (Livraison livraison : tournee.getLivraisons()) {
  //       display(livraison.getLieu(), Color.red, -1);
  //     }
  //   }
  // }

  // private void displayElements() {
  //   if (carte != null) {
  //     vueEntrepot.dessinerVue(this);
  //     for (VueIntersection vi : vuesIntersections) {
  //       vi.dessinerVue(this);
  //     }
  //     g.fillOval(700, 500, 10, 10);
  //   //   for (Segment segment : segments) {
  //   //     display(segment, Color.blue);
  //   //   }
  //   //   if (tournee != null && tournee.getNbLivraisons() > 0) {
  //   //     displayTournee();
  //   //   }
  //   }
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
    translateView();

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

    xpos = (int) (xpos * zoomFactor) + viewX;
    ypos = (int) (ypos * zoomFactor) + viewY;
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

  public Graphics getGraphics() {
	  return g;
  }

  public double getZoomFactor() {
    return zoomFactor;
  }

}
