package com.hexa;

import java.util.ArrayList;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.XMLParser;
import com.hexa.view.Window;

public class App {
  public static void main(String[] args) {

    Controller controller = new Controller();
    // String inputFile = "/home/canaple/Dev/pld_agile/largeMap.xml";
    // controller.chargerCarteTest(inputFile);
    // controller.chargerCarte();

    Long start = System.currentTimeMillis();

    // Intersection entrepot = new Intersection();
    // ArrayList<Intersection> intersections = map.getIntersections();
    // for (Intersection inter : intersections) {
    // System.out.println(
    // "ID : " + inter.getId() + " Latitude : " + inter.getLatitude() + " Longitude
    // : " + inter.getLongitude());
    // if (map.isEntrepot(inter.getId())) {
    // entrepot = inter;
    // }
    // }

    // ArrayList<Segment> segments = map.getSegments();
    // for (Segment seg : segments) {
    // System.out.println("Destination : " + seg.getDestination() + " Length : " +
    // seg.getLongueur() + " Name : "
    // + seg.getNom() + " Origin : " + seg.getOrigine());
    // }

    // System.out.println("Entrepot : " + entrepot);

    // TODO: FIX THE PARSER SO THAT IT CAN PARSE ENTREPOT CORRECTLY AND GENERATE AN
    // XML
    // String outputFile = "/tmp/grapheGenere.xml";
    // System.out.println("Writing the graph to " + outputFile);
    // try {
    // XMLParser.grapheToXml(map, outputFile);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }
    Long end = System.currentTimeMillis();
    System.out.println("Time : " + (double) (end - start) / 1000);
  }
}
