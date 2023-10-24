package com.hexa.model;

import java.util.Iterator;
import java.util.List;

public class Circuit implements Iterator<Segment>{
	
	private Chemin[] sequence;
	private int size;
	private int index;
	
	
	
	public Circuit(List<Chemin> seq) {
		
		sequence = seq.toArray(new Chemin[0]);
		size = seq.size();
		
		index = 0;
		
	}
	
	@Override
	public boolean hasNext() {
		
		if (index < size && sequence[index].hasNext()) {
			return true;
		}
		while (index < size && ! sequence[index].hasNext()) {
			index++;
		}
		if (index >= size) {
			return false;
		}
		else {
			return true;
		}
		
	}

	@Override
	public Segment next() {
		
		if (this.hasNext()) {
			return sequence[index].next();
		}
		else {
			return null;
		}
	}

}
