package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Livraison {


    /**
     * Heure de livraison
     */
    private Date heureEstimee;

    /**
     * Type à revoir
     */
    private int plageHoraire;
    
    private Intersection lieu;
    
    public Livraison(Intersection lieu) {
    	
    	this.lieu = lieu;
    	
    }

	@Override
	public int hashCode() {
		return Objects.hash(lieu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livraison other = (Livraison) obj;
		return Objects.equals(lieu, other.lieu);
	}

	public Intersection getLieu() {
		return lieu;
	}
    
    
    

}