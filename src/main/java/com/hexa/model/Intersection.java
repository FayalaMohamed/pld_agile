package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Intersection {


    /**
     * latitude réel fourni par le fichier XML
     */
    private double latitude;

    /**
     * longitude réel fourni par le fichier XML
     */
    private double longitude;

    /**
     * 
     */
    private double x;

    /**
     * 
     */
    private double y;

    /**
     * identifient
     */
    private int id;

    /**
     * @param id 
     * @param longitude 
     * @param latitude
     */
    public Intersection(int id, double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intersection other = (Intersection) obj;
		return id == other.id;
	}

    
    
}