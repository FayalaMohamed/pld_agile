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

public class GraphicalView extends JPanel implements Observer{

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

  @Override
  public void update(Observable o, Object arg) {
    Iterator<Livraison> it = tournee.getLivraisonIterator();
		while (it.hasNext())
			display(it.next().getLieu(), Color.red);
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
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight);
    g.setColor(c);
    g.fillOval(xpos-r, ypos-r, 2*r, 2*r);
  }

  public void display(Segment s, Color c) {

    Intersection origine = s.getOrigine();
    Intersection destination = s.getDestination();

    int xOrigine = (int) ((origine.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int yOrigine = (int) ((origine.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight);
    int xDestination = (int) ((destination.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int yDestination = (int) ((destination.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight);

    g.setColor(c);
    g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
  }

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

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    g.setColor(Color.blue);
    this.g = g;
    if (carte != null) {
      Iterator<Intersection> iit = intersections.iterator();
      while (iit.hasNext()) {
        display(iit.next(), g.getColor());
      }

      Iterator<Segment> sit = segments.iterator();
      while (sit.hasNext()) {
        display(sit.next(), g.getColor());
      }
    }
  }

  public Coordonnees CoordGPSToViewPos(Intersection i) {
    int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
    int ypos = (int) ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight);
    return new Coordonnees(xpos, ypos);
  }

  public int getViewHeight() {
    return viewHeight;
  }

  public int getViewWidth() {
    return viewWidth;
  }

}
