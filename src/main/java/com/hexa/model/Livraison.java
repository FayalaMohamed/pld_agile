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

	}

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * @return Intersection
	 */
	public Intersection getLieu() {
		return lieu;
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