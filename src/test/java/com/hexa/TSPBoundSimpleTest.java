package com.hexa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

public class TSPBoundSimpleTest {

    Graphe g = new Graphe();

    @Test
    public void TSPBoundSimpleCasLimites1() {
        g = null;
        TSP tsp = new TSPBoundSimple();

        tsp.searchSolution(20000, g);
        assert (tsp.getSolutionCost() == -1);
        for (int i = 0; i < 10; i++)
            assert (tsp.getSolution(i) == null);
    }
    @Test
    public void TSPBoundSimpleCasLimites2() {
        g = new Graphe();
        TSP tsp = new TSPBoundSimple();

        tsp.searchSolution(20000, g);
        assert (tsp.getSolutionCost() == -1);
        for (int i = 0; i < 10; i++)
            assert (tsp.getSolution(i) == null);
    }
    
    public void setUp(int nb) {
        Intersection[] inters = new Intersection[nb];

        Entrepot e = new Entrepot(0L, 39.2, 39.3);
        inters[0] = e;

        g.setEntrepot(e);

        for (int i = 1; i < nb; i++) {
            inters[i] = new Intersection((long)i, 40.2 + i, 40.3 + i);
            g.ajouterIntersection(inters[i]);
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
                g.ajouterSegment(segment);
            }
        }
    }
    
    public void generalCase(int nb) {
        setUp(nb);
        TSP tsp = new TSPBoundSimple();
        tsp.searchSolution(20000, g);
        
        double expectedBestSol = 0;

        for (int i = 0; i < g.getNbIntersections() + 1; i++){
            expectedBestSol += 2 * i;
            assert (tsp.getSolution(i).getId() == i);
        }
        expectedBestSol += 9 * g.getNbIntersections();
        assert (tsp.getSolution(g.getNbIntersections() + 1).getId() == 0);
        assert (tsp.getSolution(g.getNbIntersections() + 2) == null);
        assert (tsp.getSolution(-1) == null);
        assert (tsp.getSolutionCost() == expectedBestSol);
    }
    
    @Test
    public void TSPBoundSimpleGeneralCaseTest1() {
        generalCase(5);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest2() {
        generalCase(10);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest3() {
        generalCase(15);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest4() {
        generalCase(20);
    }

}
