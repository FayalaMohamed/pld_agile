package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Livraison extends Intersection {


    /**
     * 
     */
    private Date heureEstimee;

    /**
     * Type à revoir
     */
    private int plageHoraire;
    
    public Livraison(int id, double longitude, double latitude) {
    	super(id, longitude, latitude);
    }

}