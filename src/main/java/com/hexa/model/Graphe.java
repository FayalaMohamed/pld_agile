package com.hexa.model;

import java.util.*;

public class Graphe {
	
	
//--------------------------------------------------------------------------------------------------------

	/**
	 * Table contenant l'ensemble des segments du graphe en clé et le coût de chacun
	 * en valeur
	 */
	protected Map<Segment, Double> segments;

	/**
	 * Table contenant l'ensemble des segments du graphe en clé et le nom de chacun en valeur
	 */
	protected Map<Segment, String> nomSegments;

	/**
	 * Ensemble d'intersections (= sommets du graphe)
	 */
	protected Set<Intersection> intersections;

	/**
	 * Table associant à chaque sommet, sa liste de successeur
	 */
	protected Map<Intersection, Set<Intersection>> listeSuccesseur;
	
	/**
	 * Table associant à chaque sommet, sa liste de predecesseur
	 */
	protected Map<Intersection, Set<Intersection>> listePredecesseur;

	/**
	 * Entrepot
	 */
	protected Entrepot entrepot;
	private boolean entrepotDefinit;
	
//--------------------------------------------------------------------------------------------------------

	/**
	 * Default constructor
	 * 
	 * Initialise les attributs du graphe
	 */
	public Graphe() {

		segments = new HashMap<Segment, Double>();
		intersections = new HashSet<Intersection>();
		nomSegments = new HashMap<Segment, String>();

		listeSuccesseur = new HashMap<Intersection, Set<Intersection>>();
		listePredecesseur = new HashMap<Intersection, Set<Intersection>>();

		entrepotDefinit = false;

	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**
	 * @return l'entrepot du graphe
	 */
	public Entrepot getEntrepot() {
		return entrepot;
	}
	
	/**
	 * Retourne le nombre d'intersections dans le graphe
	 * 
	 * @return le nombre d'intersections que contient le graphe (n'inclut pas
	 *         l'entrepot)
	 */
	public int getNbIntersections() {
		return intersections.size();
	}
	
	/**
	 * Retourne un tableau de toutes les intersections du graphe
	 * 
	 * @return un tableau de toutes les intersections du graphe (sans l'entrepot)
	 */
	public Intersection[] getIntersections() {
		return intersections.toArray(new Intersection[0]);
	}

	/**
	 * @param inter
	 * @return true si inter est une intersection de ce graphe
	 */
	public boolean hasIntersection(Intersection inter) {
		return intersections.contains(inter);
	}

	
	/**
	 * @param s
	 * @return true si le graphe contient un segment identique à s
	 */
	public boolean hasSegment(Segment s) {
		return segments.containsKey(s);
	}
	
	/**
	 * @param s
	 * @return le cout de passage par le segment s
	 */
	public double getCost(Segment s) {
		return segments.containsKey(s) ? segments.get(s) : Double.MAX_VALUE;
	}

	/**
	 * 
	 * @param s
	 * @return le nom du segment
	 */
	public String getNomSegment(Segment s) {
		return nomSegments.get(s);
	}
	
	/**
	 * Retourne la liste des Intersections successeurs à l'Intersection en paramètre
	 * 
	 * @param inter une intersection du graphe
	 * @return la liste des successeur de inter
	 */
	public Intersection[] getSuccesseur(Intersection inter) {
		return listeSuccesseur.get(inter).toArray(new Intersection[0]);
	}
	
	/**
	 * Retourne la liste des Intersections predecesseur à l'Intersection en paramètre
	 * 
	 * @param inter une intersection du graphe
	 * @return la liste des successeur de inter
	 */
	public Intersection[] getPredecesseur(Intersection inter) {
		return listePredecesseur.get(inter).toArray(new Intersection[0]);
	}

	/**
	 * @return un tableau de tous les segments du graphe
	 */
	public Segment[] getSegments() {
		return segments.keySet().toArray(new Segment[0]);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**
	 * Définit l'entrepot pour ce graphe (= intersection de départ et d'arrivé) s'il
	 * n'a pas été déjà définit
	 * 
	 * @param entrepot
	 * @throws GrapheException
	 */
	public void setEntrepot(Entrepot entrepot) throws GrapheException {
		if (!entrepotDefinit) {
			this.entrepot = entrepot;
			listeSuccesseur.put(entrepot, new HashSet<Intersection>());
			listePredecesseur.put(entrepot, new HashSet<Intersection>());
			this.entrepotDefinit = true;
		} else {
			throw new GrapheException("L'entrepot de ce graphe a déjà été défini. Il n'est pas possible de le changer");
		}
	}

	
//--------------------------------------------------------------------------------------------------------

	/**
	 * Ajoute l'Intersection en paramètre au graphe si elle n'a pas déjà été ajoutée
	 * 
	 * @param inter
	 * @return true si n'est pas déjà présent dans le graphe
	 */
	public boolean ajouterIntersection(Intersection inter) throws GrapheException {
		if (intersections.add(inter)) {
			listeSuccesseur.put(inter, new HashSet<Intersection>());
			listePredecesseur.put(inter, new HashSet<Intersection>());
			return true;
		}
		return false;
	}

	/**
	 * Ajoute le Segment en paramètre au graphe si elle n'a pas déjà été ajoutée
	 * 
	 * @param seg
	 * @return true si n'est pas déjà présent dans le graphe
	 */
	public boolean ajouterSegment(Segment seg) throws GrapheException {
		if (segments.putIfAbsent(seg, seg.getLongueur()) == null) {

			listeSuccesseur.get(seg.getOrigine()).add(seg.getDestination());
			
			if (listePredecesseur.get(seg.getDestination()) == null) {
				listePredecesseur.put(seg.getDestination(), new HashSet<Intersection>());
			}
			listePredecesseur.get(seg.getDestination()).add(seg.getOrigine());
			

			nomSegments.putIfAbsent(seg, seg.getNom());

			return true;
		} else {
			return false;
		}
	}

	

}
