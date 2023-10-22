package com.hexa.model.algo.branch_bound;

import java.util.Iterator;
import java.util.List;

import com.hexa.model.Intersection;
import com.hexa.model.Segment;

public class TSPBoundSimple extends TemplateTSP {

	
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
		
		return l + sumLi;
	}

	@Override
	protected Iterator<Intersection> iterator(Intersection sommetCourant, List<Intersection> unvisited) {
		return new SeqIter(unvisited, sommetCourant, g);
	}

}
