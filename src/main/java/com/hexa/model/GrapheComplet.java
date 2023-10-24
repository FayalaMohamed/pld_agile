package com.hexa.model;

import java.util.HashMap;
import java.util.Map;

import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.dijkstra.Dijkstra;

public class GrapheComplet extends Graphe {
	
	
	private Map<Segment, Chemin> cheminsPlusCourt;
	
	public GrapheComplet(Graphe carte, Tournee tournee) {
		super();
		
		cheminsPlusCourt = new HashMap<Segment, Chemin>();
		
		ShortestPath sp = new Dijkstra();
		
		this.setEntrepot(carte.entrepot);
		
		//Ajout des lieux de livraison et verification qu'ils sont bien sur la carte => création des sommets
		Livraison[] livraisons = tournee.getLivraisons();
		for (int i = 0; i < tournee.getNbLivraisons(); i++) {
			
			if (carte.hasIntersection(livraisons[i].getLieu())) {
				this.ajouterIntersection(livraisons[i].getLieu());
			}
			
		}
		
		
		//Calcul du plus court chemin de l'entrepot à chaque lieu de livraisons
		Segment s;
		sp.searchShortestPath(carte, entrepot, null);
		for (Intersection inter : intersections) {
			 s = new Segment(entrepot, inter, sp.getCost(inter), "toto");
			this.ajouterSegment(s);
			
			this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(inter)));
		}
		
		//Calcul des plus courts chemin à partir de chaque lieu de livraison
		for (Intersection depart : intersections) {
			sp.searchShortestPath(carte, depart, null);
			
			s = new Segment(depart, entrepot, sp.getCost(entrepot), null);
			
			this.ajouterSegment(s);
			
			this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(entrepot)));
			
			for (Intersection arrive : intersections) {
				if ( arrive != depart) {
					s = new Segment(depart, arrive, sp.getCost(arrive), null);
					this.ajouterSegment(s);
					this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(arrive)));
				}
			}
		}
	}
	
	public Chemin getChemin(Segment s) {
		return cheminsPlusCourt.get(s);
	}

}
