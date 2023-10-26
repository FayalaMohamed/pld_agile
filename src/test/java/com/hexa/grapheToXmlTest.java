package com.hexa;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.XMLParser;

public class grapheToXmlTest {
    XMLParser parser = new XMLParser();

    @Test
    public void grapheToXmlTest1() throws ParserConfigurationException, SAXException, IOException {
        Graphe graphe = new Graphe();
        XMLParser.grapheToXml("./src/test/java/com/hexa/XMLTest/grapheToXmlTest1.xml", graphe);

        Scanner scanner = new Scanner(new File("./src/test/java/com/hexa/XMLTest/grapheToXmlTest1.xml"));

        assert (scanner.nextLine().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
        assert (scanner.nextLine().equals("<map>"));
        assert (scanner.nextLine().equals("</map>"));
    }

    @Test
    public void grapheToXmlTest2() throws GrapheException, FileNotFoundException {
        String path = "./src/test/java/com/hexa/XMLTest/grapheToXmlTest2.xml";
        Graphe graphe = new Graphe();
        Intersection inters1 = new Intersection(25175791L, 4.857418, 45.75406);
        Intersection inters2 = new Intersection(25175778L, 4.8574653, 45.75343);
        Segment segment = new Segment(inters1, inters2, 69.979805, "Rue Danton");
        graphe.ajouterIntersection(inters1);
        graphe.ajouterIntersection(inters2);
        graphe.ajouterSegment(segment);
        XMLParser.grapheToXml(path, graphe);

        Scanner scanner = new Scanner(new File(path));

        assert (scanner.nextLine().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
        assert (scanner.nextLine().equals("<map>"));
        assert (scanner.nextLine()
                .equals("<intersection id=\"25175778\" latitude=\"45.75343\" longitude=\"4.8574653\"/>"));
        assert (scanner.nextLine()
                .equals("<intersection id=\"25175791\" latitude=\"45.75406\" longitude=\"4.857418\"/>"));
        assert (scanner.nextLine().equals(
                "<segment destination=\"25175778\" length=\"69.979805\" name=\"Rue Danton\" origin=\"25175791\"/>"));
        assert (scanner.nextLine().equals("</map>"));
    }

    @Test
    public void grapheToXmlTest3() throws GrapheException, FileNotFoundException {
        String path = "./src/test/java/com/hexa/XMLTest/grapheToXmlTest3.xml";
        Graphe graphe = new Graphe();
        Entrepot inters1 = new Entrepot(25175791L, 4.857418, 45.75406);
        Intersection inters2 = new Intersection(25175778L, 4.8574653, 45.75343);
        Segment segment = new Segment(inters1, inters2, 69.979805, "Rue Danton");
        graphe.setEntrepot(inters1);
        graphe.ajouterIntersection(inters2);
        graphe.ajouterSegment(segment);
        XMLParser.grapheToXml(path, graphe);

        Scanner scanner = new Scanner(new File(path));

        assert (scanner.nextLine().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
        assert (scanner.nextLine().equals("<map>"));
        assert (scanner.nextLine().equals("<warehouse address=\"25175791\"/>"));
        assert (scanner.nextLine()
                .equals("<intersection id=\"25175791\" latitude=\"45.75406\" longitude=\"4.857418\"/>"));
        assert (scanner.nextLine()
                .equals("<intersection id=\"25175778\" latitude=\"45.75343\" longitude=\"4.8574653\"/>"));
        assert (scanner.nextLine().equals(
                "<segment destination=\"25175778\" length=\"69.979805\" name=\"Rue Danton\" origin=\"25175791\"/>"));
        assert (scanner.nextLine().equals("</map>"));
    }

}
