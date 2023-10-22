package com.hexa.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class XMLParser {

  public static void grapheToXml(Graphe graphe) throws JAXBException, IOException {
    JAXBContext context = JAXBContext.newInstance(Graphe.class);
    Marshaller marsh = context.createMarshaller();
    marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marsh.marshal(graphe, new File("/tmp/graphe.xml"));
  }

  public static Graphe xmlToGraphe(String path) {
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
    return map;
  }

}
