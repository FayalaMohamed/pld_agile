package com.hexa.model;

import java.util.*;

public class Livraison {

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * Heure estimé de livraison
	 */
	private int[] heureEstime; // 0: heure | 1: minutes

	/**
	 * Plage horaire durant laquelle la livraison peut se faire
	 */
	private int[] plageHoraire; // 0: min | 1: max
	private int[] anciennePlageHoraire;
	boolean plageAChangee;

	private Intersection lieu;

	/**
	 * Livreur affecté à la livraison
	 */
	private Livreur livreur;

//-------------------------------------------------------------------------------------------------------------------------	

	public Livraison(Intersection lieu) {

		this.lieu = lieu;

		this.heureEstime = new int[2];
		this.plageHoraire = new int[2];
		this.anciennePlageHoraire = new int[2];
		this.plageAChangee = false;

	}

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * @return Intersection
	 */
	public Intersection getLieu() {
		return lieu;
	}

	
	/** Retourne le nombre de minutes que le livreur doit attendre pour respecter la première plage horaire renseignée
	 * @return int
	 */
	public int getNbMinutesAttente() {
		int res = 0;
		if (heureEstime[0] < plageHoraire[0]) {
			res += 60 - heureEstime[1];
			res += (plageHoraire[0] - heureEstime[0] - 1) * 60;
		}
		return res;
	}

	/**
	 * @return Livreur
	 */
	public Livreur getLivreur() {
		return livreur;
	}

	/**
	 * @return int[]
	 */
	public int[] getHeureEstime() {
		return heureEstime;
	}

	/**
	 * @return int[]
	 */
	public int[] getPlageHoraire() {
		return plageHoraire;
	}

	/**
	 * @return int[]
	 */
	public int[] getAnciennePlageHoraire() {
		return anciennePlageHoraire;
	}

	
	/** 
	 * @return boolean
	 */
	public boolean getPlageAChangee() {
		return this.plageAChangee;
	}


//-------------------------------------------------------------------------------------------------------------------------

	
	
	/**
	 * @param heure
	 * @param minutes
	 */
	public void setHeureEstime(int heure, int minutes) {
		this.heureEstime[0] = heure;
		this.heureEstime[1] = minutes;
	}

	/**
	 * @param min
	 * @param max
	 */
	public void setPlageHoraire(int min, int max) {
		this.plageHoraire[0] = min;
		this.plageHoraire[1] = max;
	}

	/**
	 * @param min
	 * @param max
	 */
	public void setAnciennePlageHoraire(int[] anciennePlage) {
		this.plageAChangee = true;
		this.anciennePlageHoraire = anciennePlage;
	}

	/**
	 * @param livreur
	 */
	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(lieu);
	}

	/**
	 * Retourne True si la Livraison et l'Object donné en paramètre sont égaux :
	 * égaux si l'objet est de la classe Livraison et a le même lieu que la
	 * Livraison appelante
	 * 
	 * @param obj
	 * @return boolean
	 */
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

	/**
	 * Retourne une description textuelle correspondant à la Livraison
	 * 
	 * @return String
	 */
	public String toString() {
		return "Adresse : " + this.lieu + " Livreur : " + this.livreur;
	}
}