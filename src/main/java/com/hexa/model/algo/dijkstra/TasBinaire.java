package com.hexa.model.algo.dijkstra;

import java.util.ArrayList;
import java.util.Map;

import com.hexa.model.Intersection;

public class TasBinaire {
	
	private ArrayList<Intersection> list;
	
	public TasBinaire() {
		this.list = new ArrayList<Intersection>();
	}

	public int getSize() {
		return list.size();
	}
	
	public void insert (Intersection inter, Map<Intersection, Double> cout) {
		
		//Ajout à la fin du tas
		list.add(inter);
		
		//Remonter si necessaire pour respecter le trie
		int i = list.size()-1;
		int pere = (i-1)/2;
		while ( i >= 0 && pere >= 0 && cout.get(list.get(pere)) > cout.get(list.get(i))) {
			
			//swap
			swap(i, pere);
			
			
			i = pere;
			pere = (i-1)/2;
			
		}
	}
	
	private void swap(int i, int j) {
		Intersection temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
	
	public Intersection extract(Map<Intersection, Double> cout) {
		
		if (list.size() == 0) {
			return null;
		}
		
		Intersection result = list.get(0);
		
		//Passage de la dernière case en premier
		list.set(0, list.get(list.size()-1));
		
		list.remove(list.size()-1);
		
		//trie du tableau
		boolean ok = true;
		int i, iG, iD;
		
		i = 0;
		
		while (ok) {
			iG = 2*i+1;
			iD = 2*i+2;
			
			if (iG < list.size() && iD < list.size()) {
				
				if (cout.get(list.get(iG)) <= cout.get(list.get(iD)) && cout.get(list.get(iG)) < cout.get(list.get(i))) {
					swap(iG, i);
					
					i = iG;
				}
				
				else if (cout.get(list.get(iD)) < cout.get(list.get(iG)) && cout.get(list.get(iD)) < cout.get(list.get(i))) {
					swap(iD, i);
					
					i = iD;
				}
				
				else {
					ok = false;
				}
				
			}
			
			else if (iG < list.size() && cout.get(list.get(iG)) < cout.get(list.get(i))) {
				swap(iG, i);
				
				i = iG;
			}
			
			else if (iD < list.size() && cout.get(list.get(iD)) < cout.get(list.get(i))) {
				swap(iD, i);
				
				i = iD;
			}
			
			else {
				ok = false;
			}
		}
		
		return result;
		
	}
	
}
