package com.hexa.model.algo;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;

public interface TSP {
	
	/**
	 * Recherche le circuit hamiltonien de cout minimal du graphe complet <code>g</code> en <code>timeLimit</code> millisecondes.
	 * (retourne le meilleur circuit trouvé même si le temps limite est atteint)
	 * @param limitTime
	 * @param g
	 */
	public void searchSolution(int timeLimit, Graphe g);
	
	/**
	 * @param i
	 * @return le i-ème sommet visité de la solution calculée par <code>searchSolution</code> 
	 * (null si <code>searchSolution</code> n'a pas encore été appelé, ou si i < 0 ou i >= nombre de sommets du graphe)
	 */
	public Intersection getSolution(int i);
	
	/** 
	 * @return le cout total de la solution calculée par <code>searchSolution</code> 
	 * (-1 si <code>searchSolution</code> n'a pas encore été appelé).
	 */
	public double getSolutionCost();

}
