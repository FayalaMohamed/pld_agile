package com.hexa.model;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

  // -------------------------------------------------------------------------------------------------------------------------

  /**
   * Génére le fichier correspondant à la sérialisation du graphe donné en
   * paramètre. Le fichier est créé s'il n'existe pas et si on a le droit d'écrire
   * dans le répertoire spécifié et si le dossier n'est pas dans un répertoire
   * sécurisé.
   * 
   * @param path
   * @param graphe
   */
  public static void grapheToXml(String path, Graphe graphe) {
    try {
      PrintWriter writer = new PrintWriter(path, "UTF-8");
      writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
      writer.println("<map>");
      printEntrepot(writer, graphe);
      printIntersections(writer, graphe);
      printSegments(writer, graphe);
      writer.println("</map>");
      writer.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Retourne un graphe créé à partir du fichier dont le path est donné en
   * paramètre le fichier doit être existant et accessible en lecture. Tout
   * fichier XML ne respectant pas la sémantique des tags : noms, nombre
   * d'attributs, noms d'attributs ou lui manquant un entrepôt n'est pas lu et
   * throws une Exception
   * 
   * @param path
   * @return Graphe
   * @throws Exception
   */
  public static Graphe xmlToGraphe(String path) throws Exception {
    Graphe map = new Graphe();
    File stocks = new File(path);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(stocks);
    doc.getDocumentElement().normalize();

    ArrayList<Long> warehouses_list = getWarehouseList(doc, path);

    HashMap<Long, Intersection> mapping_id_intersection = getMappingIdIntersection(doc, warehouses_list, map);

    addSegmentsToMap(doc, mapping_id_intersection, map);
    return map;
  }

  /**
   * Retourne un set de Livraisons créé à partir du fichier dont le path est donné
   * en paramètre. Le fichier doit être existant et accessible en lecture.
   * 
   * Tout fichier XML ne respectant pas la sémantique des tags : noms, nombre
   * d'attributs, noms d'attributs n'est pas lu et throws une Exception.
   * 
   * @param path
   * @return un set des livraisons
   * @throws Exception
   */




  // -------------------------------------------------------------------------------------------------------------------------

  /**
   * Ecrit l'entrepot dans le fichier
   * 
   * @param writer
   * @param graphe
   * @throws Exception
   */
  private static void printEntrepot(PrintWriter writer, Graphe graphe) throws Exception {
    Entrepot entrepot = graphe.getEntrepot();
    if (entrepot != null) {
      writer.println(entrepot.toTag());
      Intersection inter = new Intersection(entrepot.getId(), entrepot.getLongitude(), entrepot.getLatitude());
      writer.println(inter.toTag());
    }
  }

  /**
   * Ecrit les intersections dans le fichier
   * 
   * @param writer
   * @param graphe
   * @throws Exception
   */
  private static void printIntersections(PrintWriter writer, Graphe graphe) throws Exception {
    Intersection[] intersections = graphe.getIntersections();
    Arrays.sort(intersections, new Comparator<Intersection>() {
      @Override
      public int compare(Intersection obj1, Intersection obj2) {
        return Long.compare(obj1.id, obj2.id);
      }
    });
    for (Intersection intersection : intersections) {
      writer.println(intersection.toTag());
    }
  }

  /**
   * Ecrit les segments dans le fichier
   * 
   * @param writer
   * @param graphe
   * @throws Exception
   */
  private static void printSegments(PrintWriter writer, Graphe graphe) throws Exception {
    Segment[] segments = graphe.getSegments();
    for (Segment segment : segments) {
      writer.println(segment.toTag());
    }
  }

  /**
   * Récupère la liste des entrepots (un seul dans l'état actuel de l'application)
   * présents dans le fichier XML
   * 
   * @param doc
   * @param path
   * @throws Exception
   * @return liste des entrepots
   */
  private static ArrayList<Long> getWarehouseList(Document doc, String path) throws Exception {
    ArrayList<Long> warehouses_list = new ArrayList<Long>();
    NodeList warehouses = doc.getElementsByTagName("warehouse");
    if (warehouses.getLength() < 1) {
      throw new Exception("There are no warehouses in this XML : " + path);
    } else {
      for (int i = 0; i < warehouses.getLength(); ++i) {
        Node warehouse = warehouses.item(i);
        NamedNodeMap attributes = warehouse.getAttributes();
        if (attributes.getLength() != 1) {
          throw new Exception("Warehouse tag should only have one attribute");
        }
        warehouses_list.add(Long.parseLong(attributes.item(0).getNodeValue()));
      }
    }
    for (Long warehouse : warehouses_list) {
      System.out.println(warehouse);
    }
    return warehouses_list;
  }

  /**
   * Récupère une hashMap contenant les id intersections et clé et les
   * intersections en valeur
   * 
   * @param doc
   * @param warehouses_list
   * @throws Exception
   * @return HashMap<id, intersection>
   */
  private static HashMap<Long, Intersection> getMappingIdIntersection(Document doc, ArrayList<Long> warehouses_list,
      Graphe map) throws Exception {
    HashMap<Long, Intersection> mapping_id_intersection = new HashMap<Long, Intersection>();
    NodeList intersections = doc.getElementsByTagName("intersection");
    for (int i = 0; i < intersections.getLength(); i++) {
      Node intersection = intersections.item(i);
      NamedNodeMap attributes = intersection.getAttributes();
      if (attributes.getLength() != 3 || attributes.item(0).getNodeName() != "id"
          || attributes.item(1).getNodeName() != "latitude"
          || attributes.item(2).getNodeName() != "longitude") {
        throw new Exception("An intersection must have 3 attributes in this order : id, latitude, longitude");
      }
      Long id = Long.parseLong(attributes.item(0).getNodeValue());
      double latitude = Double.parseDouble(attributes.item(1).getNodeValue());
      double longitude = Double.parseDouble(attributes.item(2).getNodeValue());
      if (warehouses_list.contains(id)) {
        Entrepot entrepot = new Entrepot(id, longitude, latitude);
        map.setEntrepot(entrepot);
        mapping_id_intersection.put(id, entrepot);
      } else {
        Intersection inter = new Intersection(id, longitude, latitude);
        map.ajouterIntersection(inter);
        mapping_id_intersection.put(id, inter);
      }
    }
    return mapping_id_intersection;
  }

  /**
   * Ajoute les segments à la carte, en récupérant les intersections des segments
   * à l'aide d'une HashMap<id, intersection>
   * 
   * @param doc
   * @param mapping_id_intersection
   * @param map
   * @throws Exception
   */
  private static void addSegmentsToMap(Document doc, HashMap<Long, Intersection> mapping_id_intersection, Graphe map)
      throws Exception {
    NodeList segments = doc.getElementsByTagName("segment");
    for (int i = 0; i < segments.getLength(); i++) {
      Node segment = segments.item(i);
      NamedNodeMap attributes = segment.getAttributes();
      if (attributes.getLength() != 4 || attributes.item(0).getNodeName() != "destination"
          || attributes.item(1).getNodeName() != "length" || attributes.item(2).getNodeName() != "name"
          || attributes.item(3).getNodeName() != "origin") {
        throw new Exception(
            "A segment must have 4 attributes in this order : destination, length, name, origin");
      }
      Long destination = Long.parseLong(attributes.item(0).getNodeValue());
      double length = Double.parseDouble(attributes.item(1).getNodeValue());
      String name = attributes.item(2).getNodeValue();
      Long origin = Long.parseLong(attributes.item(3).getNodeValue());
      Intersection inter_origin = mapping_id_intersection.get(origin);
      Intersection inter_destination = mapping_id_intersection.get(destination);
      if (inter_origin == null || inter_destination == null) {
        throw new Exception("Segment has an intersection that does not exist: destination = " + destination
            + " and origin = " + origin);
      }
      Segment seg = new Segment(inter_origin, inter_destination, length, name);
      map.ajouterSegment(seg);
    }
  }

  /**
   * Retourne une ArrayList de livraison de Livraisons créé à partir du fichier dont le path est donné
   * en paramètre. Le fichier doit être existant et accessible en lecture.
   *
   * Tout fichier XML ne respectant pas la sémantique des tags : noms, nombre
   * d'attributs, noms d'attributs n'est pas lu et throws une Exception.
   *
   * @param path
   * @return ArrayList de tournées
   * @throws Exception
   */

  public static ArrayList<Tournee> xmlToListeLivraison(String path) throws Exception {
    File stocks = new File(path);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(stocks);
    doc.getDocumentElement().normalize();
    boolean trouvee = false;

    ArrayList<Tournee> tournees = new ArrayList<Tournee>();

    NodeList livraisons = doc.getElementsByTagName("livraison");
    for (int i = 0; i < livraisons.getLength(); i++) {
      Node uneLivraison = livraisons.item(i);
      NamedNodeMap attributes = uneLivraison.getAttributes();

     if (attributes.getLength() != 4
          || attributes.item(0).getNodeName() != "id"
          || attributes.item(1).getNodeName() != "latitude"
          || attributes.item(2).getNodeName() != "livreurId"
          || attributes.item(3).getNodeName() != "longitude"
          ) {
        throw new Exception(
            "A delivery must have 4 attributes in this order : id, latitude, livreurId, longitude.");
      }


      int livreurId = Integer.parseInt(attributes.item(2).getNodeValue());
      Long id = Long.parseLong(attributes.item(0).getNodeValue());
      double latitude = Double.parseDouble(attributes.item(1).getNodeValue());
      double longitude = Double.parseDouble(attributes.item(3).getNodeValue());

      Livraison livraison = new Livraison(new Intersection(id, longitude, latitude));
      livraison.setLivreur(new Livreur(livreurId));

      if (tournees.size() == 0) {
        Tournee t = new Tournee();
        t.setLivreur(new Livreur(livreurId));
        t.ajouterLivraison(livraison);
        tournees.add(t);
      } else {
        for (Tournee t : tournees) {
          if (livreurId == t.getLivreur().getId()) {
            t.ajouterLivraison(livraison);
            trouvee = true;
            break;
          }
        }
        if (!trouvee) {
          Tournee t = new Tournee();
          t.setLivreur(new Livreur(livreurId));
          t.ajouterLivraison(livraison);
          tournees.add(t);
        }
      }
      trouvee = false;

    }

    return tournees;
  }
  /**
   * Sérialisation d'une Arraylist de tournées et sauvegarde dans le fichier XML
   * spécifié dans le path.
   *
   * Le fichier est créé s'il n'existe pas et si on a le droit d'écrire dans le
   * répertoire spécifié et si le dossier n'est pas dans un répertoire sécurisé.
   *
   * @param path
   * @param tournees
   */
  public static void listeLivraisonsToXml(String path, ArrayList<Tournee> tournees) {
    try {
      PrintWriter writer = new PrintWriter(path, "UTF-8");
      writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
      writer.println("<livraisons>");
      for (Tournee t : tournees) {
        for (Livraison livraison : t.getLivraisons()) {
          writer.println("<livraison livreurId=\""  + t.getLivreur().getId()
             + "\" id=\"" + livraison.getLieu().getId() + "\" latitude=\""
              + livraison.getLieu().getLatitude() + "\" longitude=\"" + livraison.getLieu().getLongitude()
              + "\"/>");

        }
      }
      writer.println("</livraisons>");
      writer.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
