package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Graphe extends Observable {
	
	/**
     * Table de hashage contenant l'ensemble des segments du graphe en clé et le coût de chacun en valeur
     */
    private Map<Segment, Double> segments;

    /**
     * Ensemble d'intersections (= sommets du graphe)
     */
    private Set<Intersection> intersections;
    
    /**
     * Entrepot = point de départ et d'arrivée du circuit du livreur
     */
    private Entrepot entrepot;

    /**
     * Default constructor
     */
    public Graphe() {
    	
    	segments = new HashMap<Segment, Double>();
    	intersections = new HashSet<Intersection>();
    	
    }
    
    /**
     * Définit l'entrepot pour ce graphe (= intersection de départ et d'arrivé)
     * @param entrepot
     */
    public void setEntrepot (Entrepot entrepot) {
    	this.entrepot = entrepot;
    }
    
    /**
     * 
     * @return l'entrepot du graphe
     */
    public Entrepot getEntrepot() {
		return entrepot;
	}

	/**
     * @param inter 
     * @return true si n'est pas déjà présent dans le graphe
     */
    public boolean ajouterIntersection(Intersection inter) {
        return intersections.add(inter);
    }

    /**
     * @param id 
     * @return
     */
    public Intersection trouverIntersectionParId(Intersection id) {
        // TODO implement here
        return null;
    }

    /**
     * @param seg 
     * @return true si n'est pas déjà présent dans le graphe
     */
    public boolean ajouterSegment(Segment seg) {
        if (segments.putIfAbsent(seg, seg.getLongueur()) == null) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    /**
     * Utile uniquement pour la phase de test de developpement de l'algo de TSP
     */
    public void afficher() {
    	for (Map.Entry<Segment, Double> entry : segments.entrySet()) {
    		Segment seg = (Segment) entry.getKey();
    		System.out.println("cost[" + seg.getOrigine().getId() + "][" + seg.getDestination().getId() + "] = " + (int)seg.getLongueur() + ";");
    	}
    		
    }
    
    /**
     * 
     * @return le nombre d'intersections que contient le graphe (n'inclut pas l'entrepot)
     */
    public int getNbIntersections() {
    	return intersections.size();
    }
    
    /**
     * 
     * @return un tableau de toutes les intersections du graphe
     */
    public Intersection[] getIntersections() {
    	return intersections.toArray(new Intersection[0]);
    }

    /**
     * 
     * @param s
     * @return true si le graphe contient un segment identique à s
     */
    public boolean hasSegment(Segment s) {
    	return segments.containsKey(s);
    }
    
    /**
     * 
     * @param s
     * @return le cout de passage par le segment s
     */
    public double getCost(Segment s) {
    	return segments.get(s);
    }

}