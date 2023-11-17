package com.hexa.model;

/**
 * Classe modélisant un livreur.
 * Elle est caractérisée par un id.
 */
public class Livreur {

//-------------------------------------------------------------------------------------------------------------------------

	private int id;

//-------------------------------------------------------------------------------------------------------------------------

	public Livreur(int id) {
		this.id = id;
	}

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * @return int
	 */
	public int getId() {
		return id;
	}

//-------------------------------------------------------------------------------------------------------------------------

	/**
	 * @return String
	 */
	public String toString() {
		return "id : " + id;
	}

	public boolean equals(Livreur l) {
		return this.id == l.getId();
	}
}