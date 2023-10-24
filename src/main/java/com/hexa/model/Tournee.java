package com.hexa.model;


import java.util.*;

import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

/**
 * 
 */
public class Tournee extends Observable {
	
	
	/**
     * Jour à laquelle doit/s'est passé la tournée
     */
    private Date date;

    /**
     * Ensemble des livraisons à effectuer
     */
    private Set<Livraison> livraisons;

    /**
     * Livreur affecté à la tournée
     */
    private Livreur livreur;
    
    /**
     * Circuit (=sequence de segments) à parcourir par le livreur pour faire toutes les livraisons
     */
    private Circuit circuit;
    private boolean circuitCalculer;

    /**
     * Default constructor
     * 
     * Initialise les attributs
     */
    public Tournee() {
    	
    	this.livraisons = new HashSet<Livraison>();
    	circuit = null;
    	circuitCalculer = false;
    	
    }

    

    /**
     * Ajoute une livraisons 
     * 
     * Définit l'état du circuit à non calculé => A FAIRE : décider si on doit recalculer ou si on interdit l'ajout après calcul
     * 
     * @param l une livraison à ajouter à cette tournée
     * @return true si la livraison n'était pas déjà présente
     */
    public boolean ajouterLivraison (Livraison l) {
    	return this.livraisons.add(l);
    }
    
    
    /**
     * uniquement pour le dev
     */
    public void afficher() {
    	for (Livraison l : livraisons) {
    		System.out.println(l.getLieu().getId());
    	}
    }
    
    /**
     * @return le nombre de livraison que contient cette tournée
     */
    public int getNbLivraisons() {
    	return livraisons.size();
    }
    
    /**
     * @return un tableau de l'ensemble des livraisons à faire
     */
    public Livraison[] getLivraisons() {
    	return livraisons.toArray(new Livraison[0]);
    }
    
    /**
     * 
     * Construit le meilleur circuit pour réaliser la tournée à partir de la carte
     * 
     * @param carte 
     * @throws TourneeException 
     * @throws GrapheException 
     */
    public void construireCircuit(Graphe carte) throws GrapheException, TourneeException {
    	
    	//Création du graphe complet associé à la tournée
    	GrapheComplet grapheComplet  = new GrapheComplet(carte, this); 
    	
    	
    	//Calcul du meilleur circuit
    	TSP tsp = new TSPBoundSimple();
		tsp.searchSolution(20000, grapheComplet);
		
		
		//Construction du circuit de segment
		Intersection depart, arrive;
		ArrayList<Chemin> list = new ArrayList<Chemin>();
		int i = 0;
		while ( (depart = tsp.getSolution(i)) != null && (arrive = tsp.getSolution(i+1)) != null ) {
			i++;
			list.add( grapheComplet.getChemin(new Segment(depart, arrive))); 
		}
		
		circuit = new Circuit(list);
		circuitCalculer = true;
    	
    }
    
    public Circuit getCircuit() throws TourneeException {
    	if (circuitCalculer) {
    		return circuit;
    	}
    	else {
    		throw new TourneeException("Le circuit n'a pas encore été calculé");
    	}
    }
    
}