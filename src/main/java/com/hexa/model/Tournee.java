package com.hexa.model;


import java.util.*;

import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

/**
 * 
 */
public class Tournee extends Observable {

    /**
     * Default constructor
     */
    public Tournee() {
    	
    	this.livraisons = new HashSet<Livraison>();
    	
    }

    /**
     * Jour à laquelle doit/ s'est passé la tournée
     */
    private Date date;

    /**
     * Ensemble des livraisons à effectuer
     */
    private Set<Livraison> livraisons;

    /**
     * 
     */
    private Livreur livreur;
    
    private Circuit circuit;

    
    public boolean ajouterLivraison (Livraison l) {
    	return this.livraisons.add(l);
    }
    
    public void afficher() {
    	for (Livraison l : livraisons) {
    		System.out.println(l.getLieu().getId());
    	}
    }
    
    public int getNbLivraisons() {
    	return livraisons.size();
    }
    
    public Livraison[] getLivraisons() {
    	return livraisons.toArray(new Livraison[0]);
    }
    
    public void build(Graphe carte) {
    	
    	GrapheComplet grapheComplet  = new GrapheComplet(carte, this); 
    	
    	TSP tsp = new TSPBoundSimple();
		
		long startTime = System.currentTimeMillis();
		tsp.searchSolution(20000, grapheComplet);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		
		Intersection depart, arrive;
		ArrayList<Chemin> list = new ArrayList<Chemin>();
		int i = 0;
		while ( (depart = tsp.getSolution(i++)) != null && (arrive = tsp.getSolution(i++)) != null ) {
			list.add( grapheComplet.getChemin(new Segment(depart, arrive))); 
		}
		
		circuit = new Circuit(list);
    	
    }
    
}