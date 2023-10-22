package com.hexa.model.algo;

import java.util.List;
import java.util.Set;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;

public interface ShortestPath {
	
	/**
	 * Recherche les plus courts chemins partant de origine et allant vers tous les autres sommets du graphe hormis ceux de exclu
	 * Les sommets de exclu avec les arcs correspondant sont en quelques supprimer du graphe. 
	 * @param carte
	 * @param origine
	 * @param exclu on peut passer null pour n'exclure aucun sommet
	 */
	public void searchShortestPath (Graphe carte, Intersection origine, Set<Intersection> exclu);
	
	/**
	 * 
	 * @param inter
	 * @return le cout du plus court chemin allant de origine à inter (-1 si searchShortestPath n'a pas été appelé avant)
	 */
	public double getCost(Intersection inter);
	
	/**
	 * Détermine la séquence de segment à suivre pour arriver à dest depuis origine de façon la plus courte
	 * @param dest
	 * @return une liste de segment ou null si searchShortestPath n'a pas été appelé avant 
	 */
	public List<Segment> getSolPath(Intersection dest);

}
