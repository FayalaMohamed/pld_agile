package com.hexa.model;

import java.util.Iterator;
import java.util.List;

/**
 * Classe modélisant un circuit, c'est à dire une suite de segments revenant sur le point de départ.
 */
public class Circuit implements Iterator<Segment> {
	
//------------------------------------------------------------------------------------------------------------------

	private Chemin[] sequence;
	private int size;
	private int index;
	
//------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor :
	 * un circuit est un iterator sur les Segments des Chemins donnés en paramètre
	 * 
	 * @param seq liste ordonnée de chemin qui doivent composer le circuit
	 */
	public Circuit(List<Chemin> seq) {

		sequence = seq.toArray(new Chemin[0]);
		size = seq.size();

		index = 0;
	}

//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return true s'il existe au moins un autre Segment à parcourir
	 */
	@Override
	public boolean hasNext() {

		if (index < size && sequence[index].hasNext()) {
			return true;
		}
		while (index < size && !sequence[index].hasNext()) {
			index++;
		}
		if (index >= size) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * @return le prochain Segment parcouru
	 */
	@Override
	public Segment next() {

		if (this.hasNext()) {
			return sequence[index].next();
		} else {
			return null;
		}
	}
	
	
	/**
	 * Remet à zéro l'itérateur
	 */
	public void reset() {
		index = 0;
		
		for (Chemin c : sequence) {
			c.reset();
		}
	}

}
