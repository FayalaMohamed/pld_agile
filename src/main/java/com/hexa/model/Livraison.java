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

	private Livreur livreur;
    
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

	public Date getHeureEstimee() {
		return heureEstimee;
	}

	public void setHeureEstimee(Date heureEstimee) {
		this.heureEstimee = heureEstimee;
	}

	public int getPlageHoraire() {
		return plageHoraire;
	}

	public void setPlageHoraire(int plageHoraire) {
		this.plageHoraire = plageHoraire;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public void setLieu(Intersection lieu) {
		this.lieu = lieu;
	}
}