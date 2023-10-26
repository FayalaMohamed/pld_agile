package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Livreur {

    /**
     * Default constructor
     */
    public Livreur(int id) {
        this.id = id;
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private Set<Tournee> tournees;

    public int getId() {
        return id;
    }
}