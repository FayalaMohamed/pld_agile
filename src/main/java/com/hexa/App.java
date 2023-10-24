package com.hexa;

import java.util.Set;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.XMLParser;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    try {
      Graphe map = XMLParser.xmlToGraphe("/home/tboyer/Downloads/fichiersXML2022(1)/fichiersXML2022/smallMap.xml");
      Intersection[] intersections = map.getIntersections();
      for (Intersection intersection : intersections) {
        System.out.println(intersection);
      }
      Entrepot entrepot = map.getEntrepot();
      System.out.println("Entrepot : " + entrepot);
      map.afficher();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
