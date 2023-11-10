package com.hexa.model.algo.branch_bound;

import java.util.Iterator;
import java.util.List;

import com.hexa.model.Intersection;
import com.hexa.model.Segment;

public class TSPBoundSimple extends TemplateTSP {

	
	
	/** Fonction bound permettant d'avoir une estimation du cout pour lier le sommet courant à un des sommets non visités
	 * et tous les sommets non visités à l'entrepôt ou à un des autres sommets non visités
	 * @param sommetCourant
	 * @param unvisited
	 * @return double
	 */
	@Override
	protected double bound(Intersection sommetCourant, List<Intersection> unvisited) {
		
		/**
	     * l = min des longueurs de lastVisited à un des sommet de notVisited
	     * li = pour chaque sommet i non visité le min des longueur allant de i à 0 ou i à un des autres sommets
	     * 
	     * return l + somme(li)
	    **/
		
		if (unvisited.size() == 0) {
			return g.getCost(new Segment(sommetCourant, g.getEntrepot()));
		}
		
		double l = Double.MAX_VALUE;
		double sumLi = 0.0;
		
		for (Intersection inter : unvisited) {
			double li = Double.MAX_VALUE;
			
			//Calcul de l
			double temp = g.getCost(new Segment(sommetCourant, inter));
			if (temp < l) {
				l = temp;
			}
			
			//Calcul de li pour i -> 0
			temp = g.getCost(new Segment(inter, g.getEntrepot()));
			if ( temp < li) {
				li = temp;
			}
			
			//Calcul de li pour i -> un autre
			for (Intersection inter2 : unvisited) {
				
				if (! inter.equals(inter2)) {
					temp = g.getCost(new Segment(inter, inter2));
					if (temp < li) {
						li = temp;
					}
				}
				
			}
			
			sumLi += li;
			
		}
		
		if (l != Double.MAX_VALUE) {
			return l + sumLi;
		}
		else {
			return sumLi;
		}
		
	}

	
	/**
	 * Créer un itérateur qui parcourt la liste des sommets de unvisited qui sont
	 * successeurs du sommetCourant
	 * L'orde de parcours est l'orde inverse d'apparition dans unvisited
	 * 
	 * @param sommetCourant
	 * @param unvisited
	 * @return Iterator<Intersection>
	 */
	@Override
	protected Iterator<Intersection> iterator(Intersection sommetCourant, List<Intersection> unvisited) {
		return new SeqIter(unvisited, sommetCourant, g);
	}

}
