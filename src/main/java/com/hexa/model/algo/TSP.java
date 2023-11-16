package com.hexa.model.algo;

import com.hexa.model.GrapheComplet;
import com.hexa.model.Intersection;

public interface TSP {

	/**
	 * Recherche le circuit hamiltonien de cout minimal du graphe complet <code>g</code> en <code>timeLimit</code> millisecondes.
	 * (retourne le meilleur circuit trouvé même si le temps limite est atteint)
	 * @param timeLimit
	 * @param g
	 * @throws AlgoException 
	 */
	public void searchSolution(int timeLimit, GrapheComplet g) throws AlgoException;
	
	
	/**
	 * Retourne la iéme Intersection de la meilleure tournée possible
	 * (null si i est inférieur à  0 ou i ets supérieur ou égal au nombre de sommets du graphe ou si le graphe de départ
	 * est null)
	 * 
	 * @param i
	 * @return le i-ème sommet visité de la solution calculée par
	 *         <code>searchSolution</code>
	 */
	public Intersection getSolution(int i);
	
	/**
	 * Retourne le cout de la meilleure tournée possible
	 * 
	 * @return le cout total de la solution calculée par <code>searchSolution</code>
	 */
	public double getSolutionCost();

}
