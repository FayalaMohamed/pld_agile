package com.hexa.model;

import java.util.*;

import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

import com.hexa.observer.Observable;

/**
 * 
 */
public class Tournee extends Observable {

  /**
   * Jour à laquelle doit/s'est passé la tournée
   */
  // private Date date;

  /**
   * Ensemble des livraisons à effectuer
   */
  private Set<Livraison> livraisons;

  private Livreur livreur;

  private int[] finTourneeEstime; // 0: heure | 1: minute

  /**
   * Circuit (=sequence de segments) à parcourir par le livreur pour faire toutes
   * les livraisons
   */
  private Circuit circuit;
  private boolean circuitCalculer;

  /**
   * Default constructor
   * 
   * Initialise les attributs
   */
  public Tournee() {

    this.livraisons = new HashSet<Livraison>();
    circuit = null;
    circuitCalculer = false;

    finTourneeEstime = new int[2];

  }

  /**
   * Ajoute une livraisons et notifie les observeurs
   * 
   * Définit l'état du circuit à non calculé => A FAIRE : décider si on doit
   * recalculer ou si on interdit l'ajout après calcul
   * 
   * @param l une livraison à ajouter à cette tournée
   * @return true si la livraison n'était pas déjà présente
   */
  public boolean ajouterLivraison(Livraison l) {
    if (circuitCalculer) {
      return false;
    }
    boolean success = this.livraisons.add(l);
    notifyObservers(this);
    return success;
  }

  /**
   * uniquement pour le dev
   */
  public void afficher() {
    for (Livraison l : livraisons) {
      System.out.println(l.getLieu().getId());
    }
  }

  /**
   * @return le nombre de livraison que contient cette tournée
   */
  public int getNbLivraisons() {
    return livraisons.size();
  }

  /**
   * @return un tableau de l'ensemble des livraisons à faire
   */
  public Livraison[] getLivraisons() {
    return livraisons.toArray(new Livraison[0]);
  }

  /**
   * @return Set<Livraison>
   */
  public Set<Livraison> getLivraisonsSet() {
    return livraisons;
  }

  /**
   * @param livraisons
   */
  public void setLivraisons(Set<Livraison> livraisons) {
    this.livraisons = livraisons;
    this.notifyObservers(this);
  }

  /**
   * @return Iterator<Livraison>
   */
  public Iterator<Livraison> getLivraisonIterator() {
    return livraisons.iterator();
  }

  /**
   * 
   * Construit le meilleur circuit pour réaliser la tournée à partir de la carte
   * 
   * @param carte
   * @throws TourneeException
   * @throws GrapheException
   */
  public void construireCircuit(Graphe carte) throws GrapheException, TourneeException {

    // Création du graphe complet associé à la tournée
    GrapheComplet grapheComplet = new GrapheComplet(carte, this);

    // Calcul du meilleur circuit
    TSP tsp = new TSPBoundSimple();
    tsp.searchSolution(60000, grapheComplet);

    // Construction du circuit de segment et recupération des couts
    Intersection depart, arrive;
    ArrayList<Chemin> list = new ArrayList<Chemin>();
    Map<Intersection, Double> temps = new HashMap<Intersection, Double>();
    Double tempsTotal = 0.0;
    int i = 0;
    while ((depart = tsp.getSolution(i)) != null && (arrive = tsp.getSolution(i + 1)) != null) {
      i++;

      Segment seg = new Segment(depart, arrive);
      list.add(grapheComplet.getChemin(seg));

      tempsTotal += (grapheComplet.getCost(seg) / 1000.0) / 15.0; // m -> km -> h
      temps.put(arrive, tempsTotal);
      tempsTotal += 5.0 / 60.0; // 5 min de battement à ajouter pour faire la livraison

    }

    // Calcul des heures de passage (delta de 5 min à chaque livraison)
    Double heure;
    int heureDepart = 8;
    int[] tab = new int[2];
    for (Livraison l : livraisons) {

      if ((heure = temps.get(l.getLieu())) != null) {

        // Paramètrage de la livraison
        tab[0] = heureDepart + heure.intValue();
        double temp = ((double) heureDepart + heure);
        tab[1] = (int) ((temp - (int) temp) * 60.0);

        l.setHeureEstime(tab[0], tab[1]);

        tab[1] = tab[0] + 1;
        l.setPlageHoraire(tab[0], tab[1]);

      }

      else {
        throw new TourneeException("Cout d'une livraison inexistant : " + l.getLieu().getId());
      }

    }

    finTourneeEstime[0] = heureDepart + temps.get(grapheComplet.getEntrepot()).intValue();
    double temp = ((double) heureDepart + temps.get(grapheComplet.getEntrepot()).intValue());
    finTourneeEstime[1] = (int) ((temp - (int) temp) * 60.0);

    circuit = new Circuit(list);
    circuitCalculer = true;

    this.notifyObservers(this);
  }

  /**
   * @param circuitCalculer
   */
  public void setCircuitCalculer(boolean circuitCalculer) {
    this.circuitCalculer = circuitCalculer;
  }

  /**
   * @return Livreur
   */
  public Livreur getLivreur() {
    return livreur;
  }

  /**
   * @param livreur
   */
  public void setLivreur(Livreur livreur) {
    this.livreur = livreur;
  }

  /**
   * Supprime une livraison de la tournee et notifie les observeurs
   * 
   * @param intersection
   */
  public void supprimerLivraison(Intersection intersection) {
    for (Livraison l : livraisons) {
      System.out.println(l.toString());
      if (l.getLieu() == intersection) {
        livraisons.remove(l);
      }
    }
    this.notifyObservers(this);
  }

  /**
   * Retourne le circuit calculé s'il est calculé sinon throws une Exception
   * 
   * @return le meilleur circuit à prendre pour faire la tournée
   * @throws TourneeException
   */
  public Circuit getCircuit() throws TourneeException {
    if (circuitCalculer) {
      return circuit;
    } else {
      throw new TourneeException("Le circuit n'a pas encore été calculé");
    }
  }

  /**
   * Retourne True si la tournee est calculee
   * 
   * @return boolean
   */
  public boolean estCalculee() {
    return circuitCalculer;
  }

  public ArrayList<Segment> getSegments() {
    ArrayList<Segment> segments = new ArrayList<Segment>();
    if (circuit == null) {
      return null;
    }
    Chemin[] chemins = circuit.getChemins();
    for (Chemin chemin : chemins) {
      Segment[] segmentsChemin = chemin.getSegments();
      for (Segment seg : segmentsChemin) {
        segments.add(seg);
      }
    }
    return segments;
  }

  public boolean getCircuitCalculer() {
    return circuitCalculer;
  }

}
