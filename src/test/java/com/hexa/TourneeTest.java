package com.hexa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hexa.model.*;
import com.hexa.model.Livraison;

public class TourneeTest {
    Tournee tournee;
    Graphe graphe;
    Intersection[] inters;

    public void setUp() throws GrapheException, TourneeException {
        int nb = 8;
        graphe = new Graphe();
        tournee = new Tournee();
        inters = new Intersection[nb];

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

        tournee.ajouterLivraison(new Livraison(inters[3]));
        tournee.ajouterLivraison(new Livraison(inters[4]));
        tournee.construireCircuit(graphe);
    }

    @Test
    public void TourneeTest1() throws GrapheException, TourneeException {
        setUp();
        int[] circuitTheorique = { 0, 1, 3, 2, 4, 7, 0 };
        assert (tournee.getNbLivraisons() == 2);
        Circuit circuit = tournee.getCircuit();
        int i = 1;
        while (circuit.hasNext()) {
            Segment segment = circuit.next();
            assert (segment.getOrigine().getId() == circuitTheorique[i - 1]);
            assert (segment.getDestination().getId() == circuitTheorique[i]);
            i++;
        }
        assert (!tournee.ajouterLivraison(new Livraison(inters[5])));

    }

    @Test
    public void TourneeTest2() throws GrapheException, TourneeException {
        Exception exception = assertThrows(Exception.class, () -> {
            tournee = new Tournee();
            tournee.getCircuit();
        });

        assertEquals("Le circuit n'a pas encore été calculé", exception.getMessage());
    }

}
