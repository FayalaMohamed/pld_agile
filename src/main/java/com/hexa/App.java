package com.hexa;

import com.hexa.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hexa.controller.Controller;

public class App {

  public static void testThomas() {
    System.out.println("Hello World!");
    try {
      Graphe map = XMLParser
          .xmlToGraphe("/home/tboyer/Downloads/fichiersXML2022(1)/fichiersXML2022/smallMap.xml");
      Intersection[] intersections = map.getIntersections();
      for (Intersection intersection : intersections) {
        System.out.println(intersection);
      }
      Entrepot entrepot = map.getEntrepot();
      System.out.println("Entrepot : " + entrepot);
      map.afficher();
      XMLParser.grapheToXml("/tmp/generated_graph.xml", map);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 
   * @param nbLivraison nombre de livraison que l'on veut dans la tournée
   * @param carte
   * @return une tournée avec des livraisons créées aléatoirement
   */
  public static Tournee createTournee(int nbLivraison, Graphe carte) {

    Tournee tournee = new Tournee();

    Intersection[] inters = carte.getIntersections();

    for (int i = 0; i < nbLivraison; i++) {
      int nombreAleatoire = 1 + (int) (Math.random() * ((carte.getNbIntersections() - 1 - 1) + 1));

      if (tournee.ajouterLivraison(new Livraison(inters[nombreAleatoire])) == false) {
        i--;
      }
    }

    return tournee;

  }

  public static void processPersistanceAlgo(String pathMap, int nbLivraisonsAFaire) {

    try {

      // Chargement de la carte
      Graphe map = XMLParser.xmlToGraphe(pathMap);

      // Création d'une tournée
      Tournee tournee = createTournee(5, map);

      // Calcul du meilleur circuit
      tournee.construireCircuit(map);

      // Affichage du circuit
      Circuit circuit = tournee.getCircuit();
      Segment seg;
      while (circuit.hasNext()) {
        seg = circuit.next();
        System.out.println(seg.getOrigine().getId() + " -> " + seg.getDestination().getId());
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
  public static ArrayList<Livraison> testParserDeLivraison() throws ParseException {

    ArrayList<Livraison> listeLivraisons = new ArrayList<Livraison>();

    for(int i = 0; i<5;i++){

      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      Date date = sdf.parse("11:11");
      int livreur_id = 55;
      int plage = 2;
      Long id =  25175791L;
      double latitude = 45.75406;
      double longitude = 4.857418;

      Livraison livraison = new Livraison(new Intersection(id, longitude, latitude));
      livraison.setLivreur(new Livreur(livreur_id));
      livraison.setPlageHoraire(plage);
      livraison.setHeureEstimee(date);

      listeLivraisons.add(livraison);


    }

    return listeLivraisons;
  }


  public static void main(String[] args) throws Exception {

    Controller controller = new Controller();
    // String inputFile =
    // "C:\\Users\\marti\\OneDrive\\Bureau\\fichiersXML2022\\largeMap.xml";
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

    XMLParser.listeLivraisonsToXml("/tmp/listeLivraison.xml", testParserDeLivraison());

    ArrayList<Livraison> listeLivraisons= XMLParser.xmlToListeLivraison("/tmp/listeLivraison.xml");
  }






}
