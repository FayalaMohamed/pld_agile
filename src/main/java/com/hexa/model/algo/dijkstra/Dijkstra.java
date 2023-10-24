package com.hexa.model.algo.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.ShortestPath;

public class Dijkstra implements ShortestPath{
	
	private Map<Intersection, Double> cout;
	
	private Map<Intersection, Segment> pi;
	
	private enum Couleur {
		BLANC, GRIS, NOIR
	};
	
	private Map<Intersection, Couleur> coloriage;
	private TasBinaire gris;
	
	private boolean searchOK;
	
	public Dijkstra() {
		cout = new HashMap<Intersection, Double>();
		pi = new HashMap<Intersection, Segment>();
		coloriage = new HashMap<Intersection, Couleur>();
		gris = new TasBinaire();
		searchOK = false;
	}
	
	@Override
	public void searchShortestPath (Graphe carte, Intersection origine, Set<Intersection> exclu) {
		
		if (exclu == null) {
			exclu = new HashSet<Intersection>();
		}
		
		//Init
		Intersection[] S = carte.getIntersections();
		
		for (Intersection si : S) {
			
			if (! exclu.contains(si)) {
				cout.put(si, Double.MAX_VALUE);
				pi.put(si, null);
				coloriage.put(si, Couleur.BLANC);
			}
		}
		
		if (! exclu.contains(carte.getEntrepot())) {
			cout.put(carte.getEntrepot(), Double.MAX_VALUE);
			pi.put(carte.getEntrepot(), null);
			coloriage.put(carte.getEntrepot(), Couleur.BLANC);
		}
		
		//Def. origine
		cout.replace(origine, 0.0);
		coloriage.replace(origine, Couleur.GRIS);
		gris.insert(origine, cout);
		
		
		//Algo
		while (gris.getSize() > 0) {
			
			Intersection si = gris.extract(cout);
			Intersection[] successeurs = carte.getSuccesseur(si);
			
			for (Intersection sj : successeurs) {
				
				if (exclu.contains(sj)) {
					continue;
				}
				
				if (coloriage.get(sj) != Couleur.NOIR) {
					relacher(new Segment(si, sj), carte);
					if ( coloriage.replace(sj, Couleur.BLANC, Couleur.GRIS) )  {
						
						gris.insert(sj, cout);
						
					}
				}
			}
			
			coloriage.replace(si, Couleur.NOIR);
			
		}
		
		searchOK = true;
		
	}
	
	@Override
	public double getCost(Intersection inter) {
		return searchOK ? cout.get(inter) : -1.0;
	}
	
	@Override
	public List<Segment> getSolPath(Intersection dest) {
		
		if ( ! searchOK ) {
			return null;
		}
		
		List<Segment> path = new ArrayList<Segment>();
		
		while (pi.get(dest) != null) {
			path.add(0, pi.get(dest));
			dest = pi.get(dest).getOrigine();
		}
		
		return path;
		
	}
	
	private void relacher(Segment seg, Graphe g) {
		
		double temp = cout.get(seg.getOrigine()) + g.getCost(seg);
		if (cout.get(seg.getDestination()) > temp) {
			cout.replace(seg.getDestination(), temp);
			pi.replace(seg.getDestination(), seg);
		}
		
	}
	
}
