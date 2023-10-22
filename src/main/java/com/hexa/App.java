package com.hexa;

import java.util.ArrayList;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.XMLParser;

public class App {
  public static void main(String[] args) {
    String inputFile = "/home/thomasboyer/Downloads/fichiersXML2022/smallMap.xml";
    System.out.println("Making a graph from the file : " + inputFile);
    Graphe map;
    try {
      map = XMLParser.xmlToGraphe(inputFile);
    } catch (Exception ex) {
      ex.printStackTrace();
      return;
    }
    System.out.println("Printing a few intersections out of the Graph object : ");
    ArrayList<Intersection> intersections = map.getIntersections();
    for (Intersection inter : intersections) {
      System.out.println(
          "ID : " + inter.getId() + " Latitude : " + inter.getLatitude() + " Longitude : " + inter.getLongitude());
    }

    ArrayList<Segment> segments = map.getSegments();
    for (Segment seg : segments) {
      System.out.println("Destination : " + seg.getDestination() + " Length : " + seg.getLongueur() + " Name : "
          + seg.getNom() + " Origin : " + seg.getOrigine());
    }
    String outputFile = "/tmp/grapheGenere.xml";
    System.out.println("Writing the graph to " + outputFile);
    try {
      XMLParser.grapheToXml(map, outputFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
