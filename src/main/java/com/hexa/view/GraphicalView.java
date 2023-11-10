package com.hexa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.swing.JPanel;

import com.hexa.model.Circuit;
import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

public class GraphicalView extends JPanel implements Observer {

  private static final long serialVersionUID = 1L;
  private int viewHeight;
  private int viewWidth;
  private Graphics g;
  private Tournee tournee;

  private Graphe carte;
  private ArrayList<Intersection> intersections;
  private ArrayList<Segment> segments;

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
    repaint();
  }

  public void ajouterCarte(Graphe carte) {

    this.carte = carte;
    this.intersections = new ArrayList<>(Arrays.asList(carte.getIntersections()));
    this.segments = new ArrayList<>(Arrays.asList(carte.getSegments()));

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

  public void display(Intersection i, Color c, int number) {
    int r = 2;
    if (c.equals(Color.red) || c.equals(Color.green)) {
      r = 4;
    }
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
    g.setColor(c);
    g.fillOval(xpos - r, ypos - r, 2 * r, 2 * r);
    if (number != -1) {
      g.setColor(Color.black);
      g.setFont(new Font("TimesRoman", Font.BOLD, (int) (25 / zoomFactor + 1)));
      g.drawString(String.valueOf(number), xpos + r, ypos + r);
    }
  }

  public void display(Segment s, Color c) {

    Intersection origine = s.getOrigine();
    Intersection destination = s.getDestination();

    int xOrigine = (int) ((origine.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int yOrigine = (int) (viewHeight
        - ((origine.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
    int xDestination = (int) ((destination.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin)
        * viewWidth);
    int yDestination = (int) (viewHeight
        - ((destination.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));

    g.setColor(c);
    if (c == Color.red || c == Color.green) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(3));
      g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));
    } else {
      g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
    }
  }

  /**
   * Méthode déterminant les plus grandes coordonnées de la carte choisie Permet
   * de définir l'échelle de la vue graphique
   */
  private void definirExtremesCoordonnees() {

    Iterator<Intersection> it = intersections.iterator();
    while (it.hasNext()) {

      Intersection i = it.next();

      double latitude = i.getLatitude();
      double longitude = i.getLongitude();

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

  /**
   * Méthode à appeler à chaque fois que la vue graphique doit être redessinée
   * 
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    this.g = g;

    Graphics2D g2d = (Graphics2D) g;

    g2d.translate(viewX, viewY);

    // Appliquer le facteur de zoom
    g2d.scale(zoomFactor, zoomFactor);

    if (carte != null) {

      // Affichage de l'entrepot
      display(carte.getEntrepot(), Color.green, -1);

      // Affichage de toutes les intersections
      for (Intersection intersection : intersections) {
        display(intersection, Color.blue, -1);
      }

      for (Segment segment : segments) {
        display(segment, Color.blue);
      }

      // Affichage des lieux de livraisons et segments si calcule
      if (tournee != null && tournee.getNbLivraisons() > 0) {
        Multimap<Intersection, Intersection> segments_tournee = ArrayListMultimap.create();
        try {
          Circuit circuit = tournee.getCircuit();
          System.out.println("TOTO");

          int i = 1;
          while (circuit.hasNext()) {
            Segment seg = circuit.next();
            Color color = Color.red;
            boolean already_visited = false;

            Collection<Intersection> entry_intersections = segments_tournee.get(seg.getOrigine());
            for (Intersection inter_destination : entry_intersections) {
              if (inter_destination == seg.getDestination()) {
                already_visited = true;
                break;
              }
            }

            if (!already_visited) {
              entry_intersections = segments_tournee.get(seg.getDestination());
              for (Intersection inter_origine : entry_intersections) {
                if (inter_origine == seg.getOrigine()) {
                  already_visited = true;
                  break;
                }
              }
            }

            if (already_visited) {
              color = Color.green;
              System.out.println("Deja present : " + seg.toTag());
            } else {
              segments_tournee.put(seg.getOrigine(), seg.getDestination());
            }
            Intersection inter = seg.getDestination();

            display(seg, color);

            if (tournee.estLieuLivraison(inter)) {
              display(inter, color, i++);
            }

          }

        } catch (TourneeException e) {
          for (Livraison livraison : tournee.getLivraisons()) {
            display(livraison.getLieu(), Color.red, -1);
          }
        }

      }
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
    double tempViewX = viewX;
    double tempViewY = viewY;

    if (notches < 0) {
      // Zoom in
      zoomFactor *= 1.1;
      if(zoomFactor>4){

        zoomFactor = temp;

      }
    } else {
      // Zoom out
      zoomFactor /= 1.1;
      if(zoomFactor<1){

        zoomFactor = temp;

      } else {

        if ( viewX  <-(coordonneesMax.getX()*(zoomFactor-1)) ) {
          viewX = (int)(-(coordonneesMax.getX()*(zoomFactor-1)));
          // System.out.println("Limite A atteinte");
        } else if (viewX  > coordonneesMin.getX()*(zoomFactor-1)) {
          viewX = (int)(coordonneesMin.getX()*(zoomFactor-1));

        }

        if ( viewY <-coordonneesMin.getY()*(zoomFactor-1) ) {
          viewY = (int)(-coordonneesMin.getY()*(zoomFactor-1));
          // System.out.println("Limite A atteinte");
        } else if (viewY  > coordonneesMin.getX()*(zoomFactor-1)) {
          viewY = (int)(coordonneesMin.getX()*(zoomFactor-1));

        }


       // viewX += (tempViewX - viewX) * 0.01;
       // viewY += (tempViewY - viewY) * 0.01;
      }
    }
    repaint();

  }

  /**
   * Méthode traduisant des coordonnées GPS en coordonnées en pixels pour
   * l'affichage graphique selon le facteur de zoom ZoomFactor
   * ainsi que le décalage effectué selon viewX, viewY
   * @param coordonnees correspond à la position actuel de la souris
   * @param dernieresCoordonnees correspond à la position de la souris
   * @return
   */
  public void setDrag(Coordonnees coordonnees, Coordonnees dernieresCoordonnees) {
    if (zoomFactor != 1.0) {
    
        int newViewX = viewX + coordonnees.getX() - dernieresCoordonnees.getX();
        int newViewY = viewY + coordonnees.getY() - dernieresCoordonnees.getY();
  
      if ( (newViewX  <-(coordonneesMax.getX()*(zoomFactor-1)) || (newViewX  > coordonneesMin.getX()*(zoomFactor-1)))) {
        newViewX = viewX;
       // System.out.println("Limite A atteinte");
      }
      if ( newViewY <-coordonneesMin.getY()*(zoomFactor-1) || (newViewY  > coordonneesMax.getY()*(zoomFactor-1))) {
        newViewY = viewY;
       // System.out.println("Limite C atteinte");
      }

        viewX =  newViewX;
        viewY =  newViewY;
        
        repaint();
    }
}

}
