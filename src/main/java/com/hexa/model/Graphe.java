package com.hexa.model;

import java.util.*;

/**
 * 
 */
public class Graphe extends Observable {

  /**
   * Table de hashage contenant l'ensemble des segments du graphe en clé et le
   * coût de chacun en valeur
   */
  protected Map<Segment, Double> segments;

  /**
   * Ensemble d'intersections (= sommets du graphe)
   */
  protected Set<Intersection> intersections;

  protected Map<Intersection, Set<Intersection>> listeSuccesseur;

  /**
   * Entrepot = point de départ et d'arrivée du circuit du livreur
   */
  protected Entrepot entrepot;

  /**
   * Default constructor
   */
  public Graphe() {

    segments = new HashMap<Segment, Double>();
    intersections = new HashSet<Intersection>();

    listeSuccesseur = new HashMap<Intersection, Set<Intersection>>();

  }

  /**
   * Définit l'entrepot pour ce graphe (= intersection de départ et d'arrivé)
   * 
   * @param entrepot
   */
  public void setEntrepot(Entrepot entrepot) {
    this.entrepot = entrepot;
    listeSuccesseur.put(entrepot, new HashSet<Intersection>());
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
    if (intersections.add(inter)) {
      listeSuccesseur.put(inter, new HashSet<Intersection>());
      return true;
    }
    return false;
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

      listeSuccesseur.get(seg.getOrigine()).add(seg.getDestination());

      return true;
    } else {
      return false;
    }
  }

  /**
   * Utile uniquement pour la phase de test de developpement de l'algo de TSP
   */
  public void afficher() {
    for (Map.Entry<Segment, Double> entry : segments.entrySet()) {
      Segment seg = (Segment) entry.getKey();
      System.out.println("cost[" + seg.getOrigine().getId() + "][" + seg.getDestination().getId() + "] = "
          + seg.getLongueur() + ";");
    }

  }

  /**
   * 
   * @return le nombre d'intersections que contient le graphe (n'inclut pas
   *         l'entrepot)
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

  public boolean hasIntersection(Intersection inter) {
    return intersections.contains(inter);
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

  public Intersection[] getSuccesseur(Intersection inter) {
    return listeSuccesseur.get(inter).toArray(new Intersection[0]);
  }

  public Segment[] getSegments() {
    return segments.keySet().toArray(new Segment[0]);
  }

}
