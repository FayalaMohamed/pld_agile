package com.hexa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JPanel;

import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
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
  private ArrayList<Intersection> intersections;
  private ArrayList<Segment> segments;

  private double latitudeMin;
  private double latitudeMax;
  private double longitudeMin;
  private double longitudeMax;

  /**
   * Crée la vue graphique correspondant à une tournée dans une fenêtre
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
   * Méthode appelée par les objets observés par GraphicalView à chaque mise à jour de ces derniers
   * @param o
   * @param arg
   */
  @Override
  public void update(Observable o, Object arg) {
    paintComponent(g);
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
    definirExtremesCoordonnees();

    repaint();
  }

  public void display(Intersection i, Color c) {
    int r = 2;
    if (c.equals(Color.red)) {
      r = 6;
    }
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
    g.setColor(c);
    g.fillOval(xpos - r, ypos - r, 2 * r, 2 * r);
  }

  public void display(Segment s, Color c) {

    Intersection origine = s.getOrigine();
    Intersection destination = s.getDestination();

    int xOrigine = (int) ((origine.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int yOrigine = (int) (viewHeight
        - ((origine.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
    int xDestination = (int) ((destination.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int yDestination = (int) (viewHeight
        - ((destination.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));

    g.setColor(c);
    g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
  }

  /**
   * Méthode déterminant les plus grandes coordonnées de la carte choisie
   * Permet de définir l'échelle de la vue graphique
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
  }

  /**
   * Méthode à appeler à chaque fois que la vue graphique doit être redessinée
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    this.g = g;
    if (carte != null) {
      Iterator<Intersection> iit = intersections.iterator();
      while (iit.hasNext()) {
        Intersection intersection = iit.next();
        boolean adresseLivraison = false;
        for (Livraison livraison : tournee.getLivraisons()) {
          if (livraison.getLieu() == intersection || (livraison.getLieu().getLatitude() == intersection.getLatitude()
              && livraison.getLieu().getLongitude() == intersection.getLongitude())) {
            adresseLivraison = true;
          }
        }
        if (adresseLivraison) {
          display(intersection, Color.red);
        } else {
          display(intersection, Color.blue);
        }

      }

      // Iterator<Segment> sit = segments.iterator();
      // while (sit.hasNext()) {
      // for (Segment seg : segments) {
      // ArrayList<Segment> segs = tournee.getSegments();
      // long count = 0;
      // if (segs != null) {
      // count = tournee.getSegments().stream().filter(segment ->
      // seg.equals(segment)).count();
      // }
      // if (count == 1) {
      // display(seg, Color.red);
      // } else if (count > 1) {
      // display(seg, Color.red);
      // } else {
      // display(seg, Color.blue);
      // }
      // }

      // TODO: replace tournee.getSegments with the circuit iterator, but think about
      // implementing a reset method that sets the iterator's index back to 0 in the
      // class circuit
      ArrayList<Segment> segmentsTournee = tournee.getSegments();
      System.out.println("MY SEGMENTS : ");
      if (segmentsTournee != null) {
        for (Segment seg : segmentsTournee) {
          System.out.println("SEGMENT : " + seg);
        }
      }
      for (Segment seg : segments) {
        if (segmentsTournee != null && segmentsTournee.contains(seg)) {
          display(seg, Color.red);
        } else {
          display(seg, Color.blue);
        }
      }
    }

    // Circuit circuitTournee;
    // ArrayList<Segment> segments_rouges = new ArrayList<Segment>();
    // try {
    // circuitTournee = tournee.getCircuit();
    // while (circuitTournee.hasNext()) {
    // Segment seg = circuitTournee.next();
    // // System.out.println("SEGMENT : " + seg);
    // segments_rouges.add(seg);
    // // display(seg, Color.red);
    // }
    // } catch (TourneeException e) {
    // e.printStackTrace();
    // }
    // for (Segment segment : segments) {
    // if (!segments_rouges.contains(segment)) {
    // // display(segment, Color.blue);
    // }
    // }
    // System.out.println("THEIR SEGMENTS : ");
    // for (Segment seg : segments_rouges) {
    // System.out.println("SEGMENT : " + seg);
    // }
    // }
  }

  /**
   * Méthode traduisant des coordonnées GPS en coordonnées en pixels pour l'affichage graphique
   * @param i
   * @return
   */
  public Coordonnees CoordGPSToViewPos(Intersection i) {
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));
    return new Coordonnees(xpos, ypos);
  }

  public int getViewHeight() {
    return viewHeight;
  }

  public int getViewWidth() {
    return viewWidth;
  }

}
