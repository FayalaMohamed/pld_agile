package com.hexa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.model.XMLParser;
import com.hexa.model.GrapheComplet;
import com.hexa.model.Chemin;

public class GrapheCompletTest {
    @Test
    public void GrapheCompletTest1() throws GrapheException {
        int nb = 8;
        Intersection[] inters = new Intersection[nb];
        Graphe graphe = new Graphe();
        inters[0] = new Entrepot(Long.valueOf(0), 40.2, 40.3);
        graphe.setEntrepot((Entrepot) inters[0]);
        for (int i = 1; i < nb; i++) {
            inters[i] = new Intersection(Long.valueOf(i), 40.2 + i, 40.3 + i);
            graphe.ajouterIntersection(inters[i]);
        }
        Segment[] segments = new Segment[11];
        segments[0] = new Segment(inters[0], inters[1], 5, "toto");
        segments[1] = new Segment(inters[0], inters[7], 10, "toto");
        segments[2] = new Segment(inters[0], inters[2], 4, "toto");
        segments[3] = new Segment(inters[2], inters[5], 3, "toto");
        segments[4] = new Segment(inters[5], inters[6], 2, "toto");
        segments[5] = new Segment(inters[2], inters[4], 6, "toto");
        segments[6] = new Segment(inters[4], inters[7], 3, "toto");
        segments[7] = new Segment(inters[7], inters[4], 3, "toto");
        segments[8] = new Segment(inters[7], inters[0], 10, "toto");
        segments[9] = new Segment(inters[1], inters[3], 1, "toto");
        segments[10] = new Segment(inters[3], inters[2], 10, "toto");

        for (int i = 0; i < segments.length; i++) {
            graphe.ajouterSegment(segments[i]);
        }

        Tournee tournee = new Tournee();
        tournee.ajouterLivraison(new Livraison(inters[3]));
        tournee.ajouterLivraison(new Livraison(inters[4]));

        try {
            GrapheComplet grapheComplet = new GrapheComplet(graphe, tournee);
            //grapheComplet.afficher();
            assert (grapheComplet.getCost(new Segment(inters[0], inters[3])) == 6);
            assert (grapheComplet.getCost(new Segment(inters[0], inters[4])) == 10);
            assert (grapheComplet.getCost(new Segment(inters[3], inters[0])) == 29);
            assert (grapheComplet.getCost(new Segment(inters[3], inters[4])) == 16);
            assert (grapheComplet.getCost(new Segment(inters[4], inters[3])) == 19);
            assert (grapheComplet.getCost(new Segment(inters[4], inters[0])) == 13);
            assert (grapheComplet.getCost(new Segment(inters[0], inters[0])) == Double.MAX_VALUE);
            assert (grapheComplet.getCost(new Segment(inters[0], inters[5])) == Double.MAX_VALUE);
            assert (grapheComplet.getIntersections().length == 2);
            assert (grapheComplet.getSegments().length == 6);

            Chemin chemin0To0 = grapheComplet.getChemin(new Segment(inters[0], inters[3]));
            Segment seg = chemin0To0.next();
            assert (seg.getOrigine().getId() == 0);
            assert (seg.getDestination().getId() == 1);
            seg = chemin0To0.next();
            assert (seg.getOrigine().getId() == 1);
            assert (seg.getDestination().getId() == 3);
            Exception exception = assertThrows(Exception.class, () -> {
                chemin0To0.next();
            });
        } catch (TourneeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void GrapheCompletTest2() throws GrapheException, TourneeException {
        Exception exception = assertThrows(Exception.class, () -> {
            new GrapheComplet(null, null);
        });

        assertEquals("Graphe ou tournée null", exception.getMessage());
    }

    @Test
    public void GrapheCompletTest3() throws GrapheException, TourneeException {
        GrapheComplet grapheComplet = new GrapheComplet(new Graphe(), new Tournee());
        assert (grapheComplet.getChemin(new Segment(new Intersection(1L, 1, 1), new Intersection(2L, 2, 2))) == null);
        assert (grapheComplet.getIntersections().length == 0);
        assert (grapheComplet.getSegments().length == 0);
    }

    @Test
    public void GrapheCompletTest4() throws GrapheException, TourneeException {
        Exception exception = assertThrows(Exception.class, () -> {
            Tournee tournee = new Tournee();
            tournee.ajouterLivraison(new Livraison(new Intersection(1L, 1, 1)));
            new GrapheComplet(new Graphe(), tournee);
        });

        assertEquals("Tournee invalide : un point de livraison n'appartient pas à la carte", exception.getMessage());
    }
}
