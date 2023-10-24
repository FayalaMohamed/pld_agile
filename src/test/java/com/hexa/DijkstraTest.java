package com.hexa;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.dijkstra.Dijkstra;

public class DijkstraTest {
    Graphe graphe = new Graphe();
    Intersection[] inters;
    ShortestPath sp = new Dijkstra();

    @Test
    public void DijkstraCasLimite1() {
        graphe = null;
        Intersection intersection = new Intersection(0L, 1.0, 1.0);
        sp.searchShortestPath(graphe, intersection, null);
        assert (sp.getCost(intersection) == -1);
        assert (sp.getSolPath(intersection) == null);
    }

    @Test
    public void DijkstraCasLimite2() {
        Intersection intersection = new Intersection(0L, 1.0, 1.0);
        sp.searchShortestPath(graphe, intersection, null);
        assert (sp.getCost(intersection) == -1);
        assert (sp.getSolPath(intersection) == null);
    }

    public void setUp(int nb) throws GrapheException {
        inters = new Intersection[nb];

        Entrepot e = new Entrepot(0L, 39.2, 39.3);
        inters[0] = e;

        graphe.setEntrepot(e);

        for (int i = 1; i < nb; i++) {
            inters[i] = new Intersection(Long.valueOf(i), 40.2 + i, 40.3 + i);
            graphe.ajouterIntersection(inters[i]);
        }

        Segment segment;

        for (int i = 0; i < nb; i++) {
            for (int j = 0; j < nb; j++) {
                int cout = 0;
                if (j == i) {
                    continue;
                } else if (i > j) {
                    cout = 10 * (i + j);
                } else {
                    cout = (i + j);
                }
                segment = new Segment(inters[i], inters[j], cout, "toto");
                graphe.ajouterSegment(segment);
            }
        }
    }

    @Test
    public void DijkstraTest1() throws GrapheException {
        setUp(5);
        sp.searchShortestPath(graphe, inters[0], null);
        assert (sp.getCost(inters[1]) == 1);
        assert (sp.getCost(inters[2]) == 2);
        assert (sp.getCost(inters[3]) == 3);
        assert (sp.getCost(inters[4]) == 4);

        List<Segment> chemin = sp.getSolPath(inters[2]);
        assert (chemin.get(0).getOrigine().getId() == 0);
        assert (chemin.get(0).getDestination().getId() == 2);
    }

    @Test
    public void DijkstraTest2() throws GrapheException {
        setUp(5);
        sp.searchShortestPath(graphe, inters[1], null);

        assert (sp.getCost(inters[0]) == 10);
        assert (sp.getCost(inters[2]) == 3);
        assert (sp.getCost(inters[3]) == 4);
        assert (sp.getCost(inters[4]) == 5);

        List<Segment> chemin = sp.getSolPath(inters[2]);
        assert (chemin.get(0).getOrigine().getId() == 1);
        assert (chemin.get(0).getDestination().getId() == 2);
    }

    @Test
    public void DijkstraTest3() throws GrapheException {
        setUp(5);
        sp.searchShortestPath(graphe, inters[4], null);

        assert (sp.getCost(inters[0]) == 40);
        assert (sp.getCost(inters[1]) == 41);
        assert (sp.getCost(inters[2]) == 42);
        assert (sp.getCost(inters[3]) == 43);

        List<Segment> chemin = sp.getSolPath(inters[2]);
        assert (chemin.get(0).getOrigine().getId() == 4);
        assert (chemin.get(0).getDestination().getId() == 0);
        assert (chemin.get(1).getOrigine().getId() == 0);
        assert (chemin.get(1).getDestination().getId() == 2);
    }

    @Test
    public void DijkstraTest4() throws GrapheException {
        setUp(10);
        sp.searchShortestPath(graphe, inters[4], null);

        assert (sp.getCost(inters[0]) == 40);
        assert (sp.getCost(inters[1]) == 41);
        assert (sp.getCost(inters[2]) == 42);
        assert (sp.getCost(inters[3]) == 43);

        List<Segment> chemin = sp.getSolPath(inters[2]);
        assert (chemin.get(0).getOrigine().getId() == 4);
        assert (chemin.get(0).getDestination().getId() == 0);
        assert (chemin.get(1).getOrigine().getId() == 0);
        assert (chemin.get(1).getDestination().getId() == 2);
    }

    public void createMap() throws GrapheException {
        inters = new Intersection[11];
        inters[0] = new Entrepot(0L, 25.1, 21.3);
        graphe.setEntrepot((Entrepot) inters[0]);

        for (int i = 1; i < 11; i++) {
            inters[i] = new Intersection(Long.valueOf(i), 40.2 + i, 38.6 + i);

            graphe.ajouterIntersection(inters[i]);
        }

        List<Segment> listSeg = new ArrayList<Segment>();

        listSeg.add(new Segment(inters[0], inters[1], 2, "toto"));
        listSeg.add(new Segment(inters[1], inters[0], 6, "toto"));
        listSeg.add(new Segment(inters[1], inters[2], 5, "toto"));
        listSeg.add(new Segment(inters[2], inters[1], 4, "toto"));
        listSeg.add(new Segment(inters[2], inters[3], 7, "toto"));
        listSeg.add(new Segment(inters[3], inters[2], 1, "toto"));
        listSeg.add(new Segment(inters[4], inters[3], 3, "toto"));
        listSeg.add(new Segment(inters[3], inters[4], 3, "toto"));
        listSeg.add(new Segment(inters[5], inters[4], 6, "toto"));
        listSeg.add(new Segment(inters[5], inters[6], 2, "toto"));
        listSeg.add(new Segment(inters[6], inters[5], 9, "toto"));
        listSeg.add(new Segment(inters[6], inters[7], 1, "toto"));
        listSeg.add(new Segment(inters[7], inters[8], 3, "toto"));
        listSeg.add(new Segment(inters[8], inters[9], 4, "toto"));
        listSeg.add(new Segment(inters[9], inters[0], 2, "toto"));
        listSeg.add(new Segment(inters[0], inters[10], 8, "toto"));
        listSeg.add(new Segment(inters[10], inters[9], 1, "toto"));
        listSeg.add(new Segment(inters[10], inters[8], 5, "toto"));
        listSeg.add(new Segment(inters[7], inters[10], 6, "toto"));
        listSeg.add(new Segment(inters[10], inters[6], 7, "toto"));
        listSeg.add(new Segment(inters[3], inters[10], 1, "toto"));
        listSeg.add(new Segment(inters[10], inters[4], 3, "toto"));
        listSeg.add(new Segment(inters[10], inters[3], 10, "toto"));
        listSeg.add(new Segment(inters[2], inters[10], 8, "toto"));
        listSeg.add(new Segment(inters[10], inters[2], 3, "toto"));
        listSeg.add(new Segment(inters[1], inters[10], 7, "toto"));
        listSeg.add(new Segment(inters[3], inters[0], 10, "toto"));
        listSeg.add(new Segment(inters[6], inters[0], 2, "toto"));

        for (Segment seg : listSeg) {
            graphe.ajouterSegment(seg);
        }
    }

    @Test
    public void DijkstraTest5() throws GrapheException {
        createMap();
        sp.searchShortestPath(graphe, inters[10], null);

        assert (sp.getCost(inters[9]) == 1);
        assert (sp.getCost(inters[0]) == 3);
        assert (sp.getCost(inters[1]) == 5);
        assert (sp.getCost(inters[5]) == 16);
        assert (sp.getCost(inters[4]) == 3);

    }
}
