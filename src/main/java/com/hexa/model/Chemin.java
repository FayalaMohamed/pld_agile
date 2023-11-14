package com.hexa.model;

import java.util.Iterator;
import java.util.List;

public class Chemin implements Iterator<Segment> {

//-------------------------------------------------------------------------------------------------------------------------------

	private Segment[] sequence;
	private int size;
	private int index;
	
//-------------------------------------------------------------------------------------------------------------------------------
	
	/** Constructor :
	 * un chemin est un iterator sur les Segments donnés en paramètre au constructeur
	 * 
	 * @param seq liste ordonnée de segment qui doivent composer le chemin
	 */
	public Chemin(List<Segment> seq) {
		if (seq == null) {
			return;
		}
		sequence = seq.toArray(new Segment[0]);
		size = seq.size();
		
		index = 0;		
	}
	
//-------------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * @return true s'il existe encore un Segment à parcourir 
	 */
	@Override
	public boolean hasNext() {
		
		if (index < size) {
			return true;
		}
		return false;
	}

	
	/**
	 * @return le prochain Segment parcouru
	 */
	@Override
	public Segment next() {
		return sequence[index++];
	}

	
	public void reset() {
		index = 0;
	}


}
