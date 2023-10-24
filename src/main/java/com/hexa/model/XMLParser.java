package com.hexa.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class XMLParser {

  public static void grapheToXml(Graphe graphe, String outputFile) throws JAXBException, IOException {
    JAXBContext context = JAXBContext.newInstance(Graphe.class);
    Marshaller marsh = context.createMarshaller();
    marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marsh.marshal(graphe, new File(outputFile));
  }

  public static Graphe xmlToGraphe(String path) throws Exception {
    Graphe map = null;
    try {
      JAXBContext context = JAXBContext.newInstance(Graphe.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      map = (Graphe) unmarshaller.unmarshal(new FileInputStream(path));
    } catch (JAXBException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ArrayList<Intersection> intersections = map.getIntersections();
    for (Intersection intersection : intersections) {
      map.addMappingIntersection(intersection);
    }
    ArrayList<Segment> segments = map.getSegments();
    for (int i = 0; i < segments.size(); ++i) {
      Segment seg = segments.get(i);
      Intersection dest = map.trouverIntersectionParId(seg.getDestinationId());
      Intersection ori = map.trouverIntersectionParId(seg.getOrigineId());
      if (dest == null || ori == null) {
        throw new Exception("ERROR WITH IDS, NON EXISTING INTERSECTION INSIDE SEGMENT");
      }
      seg.setDestination(dest);
      seg.setOrigine(ori);
    }
    return map;
  }

}
