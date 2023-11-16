package com.hexa.model.algo.branch_bound;

import java.util.Iterator;
import java.util.List;


import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;

public class SeqIter implements Iterator<Intersection> {
	
	private int nbCandidats;
	private Intersection[] candidats;
	
//-----------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Créer un itérateur qui parcourt la liste des sommets de unvisited qui sont successeurs du sommetCourant
	 * L'orde de parcours est l'orde inverse d'apparition dans unvisited
	 * @param unvisited
	 * @param sommetCourant
	 * @param g
	 */
	public SeqIter(List<Intersection> unvisited, Intersection sommetCourant, Graphe g) {
		this.candidats = new Intersection[unvisited.size()];
		nbCandidats = 0;
		for (Intersection inter : unvisited) {
			if (g.hasSegment(new Segment(sommetCourant, inter))) {
				candidats[nbCandidats] = inter;
				nbCandidats++;
			}
		}
	}

	
//-----------------------------------------------------------------------------------------------------------------
	
	/** Retourne True si il existe au moins une autre Intersection dans le tableau candidats 
	 * @return boolean
	 */
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	/** Retourn la prochaine Intersection
	 * @return Intersection
	 */
	@Override
	public Intersection next() {
		nbCandidats--;
		return candidats[nbCandidats];
	}

}
