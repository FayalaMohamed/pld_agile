package com.hexa.model;


import java.util.*;

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

    /**
     * Circuit optimal à faire pour effectuer toutes les livraisons
     */
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
}