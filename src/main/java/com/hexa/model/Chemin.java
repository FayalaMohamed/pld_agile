package com.hexa.model;

import java.util.Iterator;
import java.util.List;

public class Chemin implements Iterator<Segment> {


	private Segment[] sequence;
	private int size;
	private int index;
	
	/** Constructor :
	 * un chemin est un iterator sur les Segments donnés en paramètre au constructeur
	 * 
	 * @param seq liste ordonnée de segment qui doivent composer le chemin
	 */
	public Chemin(List<Segment> seq) {
		sequence = seq.toArray(new Segment[0]);
		size = seq.size();
		
		index = 0;		
	}
	
	
	/** Retourne True s'il existe encore un Segment à parcourir 
	 * @return boolean
	 */
	@Override
	public boolean hasNext() {
		
		if (index < size) {
			return true;
		}
		return false;
	}

	
	/** Retourne le prochain Segment parcouru
	 * @return Segment
	 */
	@Override
	public Segment next() {
		return sequence[index++];
	}

	
	public void reset() {
		index = 0;
	}

	public void reset() {
		index = 0;
	}

}
