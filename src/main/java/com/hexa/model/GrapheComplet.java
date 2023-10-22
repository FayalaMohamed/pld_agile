package com.hexa.model;

import java.util.Iterator;

import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.dijkstra.Dijkstra;

public class GrapheComplet extends Graphe {
	
	
	public GrapheComplet(Graphe carte, Tournee tournee) {
		super();
		
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
		sp.searchShortestPath(carte, entrepot, null);
		for (Intersection inter : intersections) {
			this.ajouterSegment(new Segment(entrepot, inter, sp.getCost(inter), "toto"));
			
			// A FAIRE : Enregistrer le chemin correspondant
		}
		
		//Calcul des plus courts chemin à partir de chaque lieu de livraison
		for (Intersection depart : intersections) {
			sp.searchShortestPath(carte, depart, null);
			
			this.ajouterSegment(new Segment(depart, entrepot, sp.getCost(entrepot), null));
			
			// A FAIRE : Enregistrer le chemin correspondant
			
			for (Intersection arrive : intersections) {
				if ( arrive != depart) {
					this.ajouterSegment(new Segment(depart, arrive, sp.getCost(entrepot), null));
					// A FAIRE : Enregistrer le chemin correspondant
				}
			}
		}
	}
	
	

}
