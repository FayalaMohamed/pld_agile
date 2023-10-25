package com.hexa;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.Segment;
import com.hexa.model.Intersection;
import com.hexa.model.XMLParser;

public class xmlToGrapheTest {
        XMLParser parser = new XMLParser();

        @Test
        public void xmlToGrapheTest1() throws Exception {
                Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/smallMap.xml");
                Segment[] segments = graphe.getSegments();
                assert (graphe.getEntrepot().getId() == 25303831);
                assert (graphe.getNbIntersections() == 307);
                assert (segments.length == 616);
                assert (graphe.hasIntersection(new Intersection(25175791L, 4.857418, 45.75406)));
                assert (graphe.hasIntersection(new Intersection(2129259178L, 4.8744674, 45.750404)));
                assert (graphe.hasSegment(new Segment(
                                new Intersection(25175791L, 4.857418, 45.75406),
                                new Intersection(
                                                25175778L,
                                                4.8574653,
                                                45.75343),
                                69.979805, "Rue Danton")));
        }

        @Test
        public void xmlToGrapheTest2() throws Exception {
                Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest1.xml");
                Segment[] segments = graphe.getSegments();
                System.out.println(segments[0].getLongueur() + " " + segments[0].getOrigine().getId() + " "
                                + segments[0].getDestination().getId());
                assert (graphe.getEntrepot().getId() == 25175791);
                assert (graphe.getNbIntersections() == 1);
                assert (segments.length == 1);
                assert (graphe.hasIntersection(new Intersection(2129259178L, 4.8744674, 45.750404)));
                Segment expectedSegment = new Segment(
                                new Entrepot(25175791L, 4.857418, 45.75406),
                                new Intersection(2129259178L, 4.8744674, 45.750404),
                                136.00636, "Rue de l'Abondance");
                assert (graphe.hasSegment(expectedSegment));
                Segment notExpectedSegment = new Segment(
                                new Intersection(25175791L, 4.857418, 45.75406),
                                new Intersection(2129259178L, 4.8744674, 45.750404),
                                136.00636, "Rue de l'Abondance");
                assert (!graphe.hasSegment(notExpectedSegment));
        }

        @Test
        public void xmlToGrapheTest3() throws Exception {
                Exception exception = assertThrows(Exception.class, () -> {
                        Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest2.xml");
                });

                assertEquals("There are no warehouses in this XML : ./src/test/java/com/hexa/XMLTest/xmlTest2.xml", exception.getMessage());
        }
        @Test
        public void xmlToGrapheTest4() throws Exception {
                Exception exception = assertThrows(Exception.class, () -> {
                        Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest3.xml");
                });

                assertEquals("Warehouse tag should only have one attribute", exception.getMessage());
        }
        @Test
        public void xmlToGrapheTest5() throws Exception {
                Exception exception = assertThrows(Exception.class, () -> {
                        Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest4.xml");
                });

                assertEquals("An intersection must have 3 attributes in this order : id, latitude, longitude", exception.getMessage());
        }
        @Test
        public void xmlToGrapheTest6() throws Exception {
                Exception exception = assertThrows(Exception.class, () -> {
                        Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest5.xml");
                });

                assertEquals("A segment must have 4 attributes in this order : destination, length, name, origin", exception.getMessage());
        }
        @Test
        public void xmlToGrapheTest7() throws Exception {
                Exception exception = assertThrows(Exception.class, () -> {
                        Graphe graphe = XMLParser.xmlToGraphe(
                                "./src/test/java/com/hexa/XMLTest/xmlTest6.xml");
                });

                assertEquals("Segment has an intersection that does not exist: destination = 2129259178 and origin = 25175791", exception.getMessage());
        }
}
