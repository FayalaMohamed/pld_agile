package com.hexa.model;

import java.util.Iterator;
import java.util.List;

public class Chemin implements Iterator<Segment> {

	private Segment[] sequence;
	private int size;
	private int index;
	
	public Chemin(List<Segment> seq) {
		
		sequence = seq.toArray(new Segment[0]);
		size = seq.size();
		
		index = 0;
		
	}
	
	@Override
	public boolean hasNext() {
		
		if (index < size) {
			return true;
		}
		return false;
	}

	@Override
	public Segment next() {
		return sequence[index++];
	}
	
	
	
}
