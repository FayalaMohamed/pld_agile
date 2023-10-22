package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Segment {

    /**
     * Longueur en mètres du segment de route
     */
    private double longueur;

    /**
     * Nom du segment 
     */
    private String nom;

    /**
     * point de départ
     */
    private Intersection origine;

    /**
     * point d'arrivée
     */
    private Intersection destination;

    /**
     * @param origine 
     * @param destination 
     * @param longueur 
     * @param nom
     */
    public Segment(Intersection origine, Intersection destination, double longueur, String nom) {
        this.destination = destination;
        this.origine = origine;
        this.nom = nom;
        this.longueur = longueur;
    }
    
    /**
     * @param origine 
     * @param destination
     */
    public Segment(Intersection origine, Intersection destination) {
        this.destination = destination;
        this.origine = origine;
        this.nom = null;
        this.longueur = Double.MAX_VALUE;
    }

	public double getLongueur() {
		return longueur;
	}

	public Intersection getOrigine() {
		return origine;
	}

	public Intersection getDestination() {
		return destination;
	}


	@Override
	public int hashCode() {
		return Objects.hash(destination, origine);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		return Objects.equals(destination, other.destination) && Objects.equals(origine, other.origine);
	}
    
    
    

}