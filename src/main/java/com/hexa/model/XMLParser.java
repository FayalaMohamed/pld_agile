package com.hexa.model;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
  public static void grapheToXml(String path, Graphe graphe) {
    try {
      PrintWriter writer = new PrintWriter(path, "UTF-8");
      writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
      writer.println("<map>");
      Intersection[] intersections = graphe.getIntersections();
      Arrays.sort(intersections, new Comparator<Intersection>() {
        @Override
        public int compare(Intersection obj1, Intersection obj2) {
          return Long.compare(obj1.id, obj2.id);
        }
      });
      Entrepot entrepot = graphe.getEntrepot();
      if (entrepot != null) {
        writer.println(entrepot.toTag());
        Intersection inter = new Intersection(entrepot.getId(), entrepot.getLongitude(), entrepot.getLatitude());
        writer.println(inter.toTag());
      }
      for (Intersection intersection : intersections) {
        writer.println(intersection.toTag());
      }
      Segment[] segments = graphe.getSegments();
      for (Segment segment : segments) {
        writer.println(segment.toTag());
      }
      writer.println("</map>");
      writer.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Graphe xmlToGraphe(String path) throws Exception {
    Graphe map = new Graphe();
    File stocks = new File(path);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(stocks);
    doc.getDocumentElement().normalize();

    NodeList warehouses = doc.getElementsByTagName("warehouse");
    ArrayList<Long> warehouses_list = new ArrayList<Long>();
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

    HashMap<Long, Intersection> mapping_id_intersection = new HashMap<Long, Intersection>();
    NodeList intersections = doc.getElementsByTagName("intersection");
    for (int i = 0; i < intersections.getLength(); i++) {
      Node intersection = intersections.item(i);
      NamedNodeMap attributes = intersection.getAttributes();
      if (attributes.getLength() != 3 || attributes.item(0).getNodeName() != "id"
          || attributes.item(1).getNodeName() != "latitude" || attributes.item(2).getNodeName() != "longitude") {
        throw new Exception("An intersection must have 3 attributes in this order : id, latitude, longitude");
      }
      Long id = Long.parseLong(attributes.item(0).getNodeValue());
      double latitude = Double.parseDouble(attributes.item(1).getNodeValue());
      double longitude = Double.parseDouble(attributes.item(2).getNodeValue());
      if (warehouses_list.contains(id)) {
        Entrepot entrepot = new Entrepot(id, longitude, latitude);
        // TODO Int the files there are only one warehouse, but if scalable entrepot
        // should become a list in Graphe Julien said not
        map.setEntrepot(entrepot);
        mapping_id_intersection.put(id, entrepot);
      } else {
        Intersection inter = new Intersection(id, longitude, latitude);
        map.ajouterIntersection(inter);
        mapping_id_intersection.put(id, inter);
      }
    }

    NodeList segments = doc.getElementsByTagName("segment");
    for (int i = 0; i < segments.getLength(); i++) {
      Node segment = segments.item(i);
      NamedNodeMap attributes = segment.getAttributes();
      if (attributes.getLength() != 4 || attributes.item(0).getNodeName() != "destination"
          || attributes.item(1).getNodeName() != "length" || attributes.item(2).getNodeName() != "name"
          || attributes.item(3).getNodeName() != "origin") {
        throw new Exception("A segment must have 4 attributes in this order : destination, length, name, origin");
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
    return map;
  }
}
