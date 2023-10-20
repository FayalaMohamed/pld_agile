package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Tournee extends Observable {

    /**
     * Default constructor
     */
    public Tournee() {
    }

    /**
     * 
     */
    private Date date;

    /**
     * 
     */
    private Set<Livraison> livraisons;

    /**
     * 
     */
    private Livreur livreur;

    /**
     * 
     */
    private Circuit circuit;

}