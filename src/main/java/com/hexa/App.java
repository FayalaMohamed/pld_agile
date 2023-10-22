package com.hexa;

import java.util.Set;

import com.hexa.model.*;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    Intersection intersection = new Intersection(1, 43.1, 42.5);
    Intersection intersection2 = new Intersection(2, 43.5, 47.5);
    Segment segment = new Segment(intersection, intersection2, 200, "toto");
    Graphe graphe = new Graphe();
    graphe.ajouterIntersection(intersection);
    graphe.ajouterIntersection(intersection2);
    graphe.ajouterSegment(segment);
    try {
      XMLParser.grapheToXml(graphe);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("Hello world 2nd time!");
    Graphe map = XMLParser.xmlToGraphe("/home/thomasboyer/Downloads/fichiersXML2022/smallMap.xml");
    Set<Segment> segments = map.getSegments();
    for (Segment seg : segments) {
      System.out.println(seg.getNom() + " " + seg.getLongueur());
    }
  }
}
