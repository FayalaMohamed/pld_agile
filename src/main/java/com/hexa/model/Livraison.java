package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Livraison {


    /**
     * Heure de livraison
     */
    private int[] heureEstime; //0: heure | 1: minutes

    /**
     * Type à revoir
     */
    private int[] plageHoraire; //0: min | 1: max
    
    private Intersection lieu;
    
    public Livraison(Intersection lieu) {
    	
    	this.lieu = lieu;
    	
    	this.heureEstime = new int[2];
    	this.plageHoraire = new int[2];
    	
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

	public int[] getHeureEstime() {
		return heureEstime;
	}

	public int[] getPlageHoraire() {
		return plageHoraire;
	}

	public void setHeureEstime(int heure, int minutes) {
		this.heureEstime[0] = heure;
		this.heureEstime[1] = minutes;
	}

	public void setPlageHoraire(int min, int max) {
		this.plageHoraire[0] = min;
		this.plageHoraire[1] = max;
	}

    
	
    

}