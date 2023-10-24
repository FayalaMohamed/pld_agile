package com.hexa;

import org.junit.jupiter.api.Test;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

public class TSPBoundSimpleTest {

    Graphe graphe= new Graphe();

    @Test
    public void TSPBoundSimpleCasLimites1() {
        graphe= null;
        TSP tsp = new TSPBoundSimple();

        tsp.searchSolution(20000,graphe);
        assert (tsp.getSolutionCost() == -1);
        for (int i = 0; i < 10; i++)
            assert (tsp.getSolution(i) == null);
    }
    @Test
    public void TSPBoundSimpleCasLimites2() {
        graphe= new Graphe();
        TSP tsp = new TSPBoundSimple();

        tsp.searchSolution(20000,graphe);
        assert (tsp.getSolutionCost() == -1);
        for (int i = 0; i < 10; i++)
            assert (tsp.getSolution(i) == null);
    }
    
    public void setUp(int nb) throws GrapheException {
        Intersection[] inters = new Intersection[nb];

        Entrepot e = new Entrepot(0L, 39.2, 39.3);
        inters[0] = e;

        try {
			graphe.setEntrepot(e);
		} catch (GrapheException e1) {
			e1.printStackTrace();
		}

        for (int i = 1; i < nb; i++) {
            inters[i] = new Intersection((long)i, 40.2 + i, 40.3 + i);
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
                try {
					graphe.ajouterSegment(segment);
				} catch (GrapheException e1) {
					e1.printStackTrace();
				}
            }
        }
    }
    
    public void generalCase(int nb) throws GrapheException {
        setUp(nb);
        TSP tsp = new TSPBoundSimple();
        tsp.searchSolution(20000, graphe);
        
        double expectedBestSol = 0;
        // g.afficher();
        for (int i = 0; i < graphe.getNbIntersections() + 2; i++)
            System.out.print(tsp.getSolution(i).getId() + " ");
        for (int i = 0; i < graphe.getNbIntersections() + 1; i++){
            expectedBestSol += 2 * i;
            assert (tsp.getSolution(i).getId() == i);
        }
        expectedBestSol += 9 * graphe.getNbIntersections();
        assert (tsp.getSolution(graphe.getNbIntersections() + 1).getId() == 0);
        assert (tsp.getSolution(graphe.getNbIntersections() + 2) == null);
        assert (tsp.getSolution(-1) == null);
        assert (tsp.getSolutionCost() == expectedBestSol);
    }
    
    @Test
    public void TSPBoundSimpleGeneralCaseTest1() throws GrapheException {
        generalCase(5);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest2() throws GrapheException {
        generalCase(10);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest3() throws GrapheException {
        generalCase(15);
    }
    @Test
    public void TSPBoundSimpleGeneralCaseTest4() throws GrapheException {
        generalCase(21);
    }

}
