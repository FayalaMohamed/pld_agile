package com.hexa.model;


/**
 * Classe modélisant un entrepôt.
 * Se comporte comme une intersection, sauf pour le toString.
 */
public class Entrepot extends Intersection {

	
//------------------------------------------------------------------------------------------
	
	/**
	 * Un Entrepot hérite d'une Intersection
	 * 
	 * @param id
	 * @param longitude
	 * @param latitude
	 */
	public Entrepot(Long id, double longitude, double latitude) {
		super(id, longitude, latitude);
	}

//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Retourne le tag XML correspondant à cet Entrepot
	 * 
	 * @return String
	 */
	public String toTag() {
		return "<warehouse address=\"" + id + "\"/>";
	}
	
}
