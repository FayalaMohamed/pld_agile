package com.hexa.model.algo;

import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

public class Test {
	
	/**
	 * Créer un grahe complet avec des longueurs de segments aléatoires
	 * @param nb nombre d'intersection à créer dans le graphe
	 * @return un graphe complet
	 */
	public static Graphe createCompleteGraph(int nb) {
		Intersection[] inters = new Intersection[nb];
		
		Entrepot e = new Entrepot(0, 39.2,39.3);
		inters[0] = e;
		
		Graphe g = new Graphe();
		g.setEntrepot(e);
	
		for (int i = 1; i < nb; i++) {
			inters[i] = new Intersection(i, 40.2 + i,40.3 + i);
			g.ajouterIntersection(inters[i]);
		}
		
		Segment segment;
		
		for (int i = 0; i < nb; i++) {
			for (int j = 0; j < nb; j++) {
				if (j == i) {
					continue;
				}
				int nombreAleatoire = 1 + (int)(Math.random() * ((10 - 1) + 1));
				segment = new Segment(inters[i], inters[j], nombreAleatoire, "toto");
				g.ajouterSegment(segment);
			}
		}
		
		return g;
		
	}
	
	/**
	 * Calcul le circuit hamiltonien le plus court pour le graphe complet g et l'affiche
	 * @param g
	 */
	public static void computeSolutionTSP(Graphe g) {
		TSP tsp = new TSPBoundSimple();
		
		long startTime = System.currentTimeMillis();
		tsp.searchSolution(20000, g);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		for (int i=0; i<g.getNbIntersections()+2; i++)
			System.out.print(tsp.getSolution(i).getId()+" ");
	}
	
	public static void main(String[] args) {
		System.out.println("Bonjour");
		
		Graphe g = createCompleteGraph(20);
		
		g.afficher();
		
		
		
		computeSolutionTSP(g);

	}

}
