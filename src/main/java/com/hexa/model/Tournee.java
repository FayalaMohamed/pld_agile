package com.hexa.model;

import java.util.*;

import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;
import com.hexa.model.algo.dijkstra.Dijkstra;
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

  private Map<Intersection, Double> temps;

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
   * Ajoute une intersection à la tournée après le calcul du plus court chemin
   * 
   * @param l
   * @return boolean
   * @throws TourneeException
   * @throws GrapheException
   */
  public boolean ajouterLivraisonApresCalcul(Graphe carte, Livraison livraisonAjouter, Livraison livraisonPrecedente)
      throws TourneeException, GrapheException {
    if (!circuitCalculer
        || (!livraisons.contains(livraisonPrecedente) && !carte.getEntrepot().equals(livraisonPrecedente.getLieu()))
        || livraisons.contains(livraisonAjouter)) {
      return false;
    }

    this.livraisons.add(livraisonAjouter);

    circuit.reset();
    boolean save = false;
    Intersection intersectionSuivante = null;

    while (circuit.hasNext()) {
      intersectionSuivante = circuit.next().getOrigine();
      if (intersectionSuivante == livraisonPrecedente.getLieu()) {
        save = true;
        continue;
      }
      if (save && livraisons.contains(new Livraison(intersectionSuivante))) {
        break;
      }
    }

    if (temps.containsKey(intersectionSuivante) == false) {
      intersectionSuivante = carte.getEntrepot();
    }

    // calcul des temps et du delta à propager
    ShortestPath dijkstra = new Dijkstra();
    dijkstra.searchShortestPath(carte, livraisonPrecedente.getLieu(), null);
    double t1 = dijkstra.getCost(livraisonAjouter.getLieu()) / 1000.0 / 15.0;
    Chemin cheminPreToNew = new Chemin(dijkstra.getSolPath(livraisonAjouter.getLieu()));
    dijkstra.searchShortestPath(carte, livraisonAjouter.getLieu(), null);
    double t2 = dijkstra.getCost(intersectionSuivante) / 1000.0 / 15.0;
    Chemin cheminNewtoNext = new Chemin(dijkstra.getSolPath(intersectionSuivante));
    temps.put(livraisonAjouter.getLieu(), temps.get(livraisonPrecedente.getLieu()) + 5.0 / 60.0 + t1);

    double oldTime = temps.get(intersectionSuivante) - temps.get(livraisonPrecedente.getLieu()) - 5.0 / 60.0;
    double delta = t1 + t2 - oldTime;

    // MAJ des temps avec le delta pour chaque livraisons suivante
    temps.put(intersectionSuivante, temps.get(intersectionSuivante) + delta);
    while (circuit.hasNext()) { // on reset pas le circuit mais on repart de là où on s'est arrété
      Intersection inter = circuit.next().getOrigine();
      if (livraisons.contains(new Livraison(inter))) {
        temps.put(inter, temps.get(inter) + delta);
      }
    }
    temps.put(carte.getEntrepot(), temps.get(carte.getEntrepot()) + delta);

    updateHeuresLivraison(carte.getEntrepot());
    MAJCircuitAjoutApresCalcul(carte.getEntrepot(), livraisonPrecedente.getLieu(), cheminPreToNew, cheminNewtoNext);
    notifyObservers(this);
    return true;
  }

  
  /** Met à jour le circuit après ajout d'une intersection à la tournée après la calcul du plus court chemin
   * @param entrepot
   * @param intersectionPrecedente
   * @param cheminPreToNew
   * @param cheminNewtoNext
   */
  private void MAJCircuitAjoutApresCalcul(Intersection entrepot, Intersection intersectionPrecedente,
      Chemin cheminPreToNew, Chemin cheminNewtoNext) {
    ArrayList<Chemin> listChemin = new ArrayList<Chemin>();
    ArrayList<Segment> listSeg = new ArrayList<Segment>();

    circuit.reset();
    boolean ignore = false;
    while (circuit.hasNext()) {

      Segment seg = circuit.next();

      if (seg.getOrigine() == intersectionPrecedente) {
        listChemin.add(new Chemin(listSeg));
        listSeg.clear();

        listChemin.add(cheminPreToNew);
        listChemin.add(cheminNewtoNext);

        ignore = true;

      }

      else if (livraisons.contains(new Livraison(seg.getOrigine()))
          || entrepot.equals(seg.getDestination())) {

        if (entrepot.equals(seg.getDestination())) {
          listSeg.add(seg);
        }
        if (!ignore) {
          listChemin.add(new Chemin(listSeg));
          listSeg.clear();
        }

        ignore = false;
      }

      if (!ignore) {
        listSeg.add(seg);
      }

    }

    circuit = new Circuit(listChemin);
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
   * Met à jour les horaires de livraison et les plages horaires de tous les
   * points de livraison
   * 
   * @param carte
   * @throws TourneeException
   */
  private void updateHeuresLivraison(Intersection entrepot) throws TourneeException {
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

      } else {
        throw new TourneeException("Cout d'une livraison inexistant : " + l.getLieu().getId());
      }

    }

    for (Livraison livraison : livraisons) {
      System.err.println(
          livraison.getLieu().getId() + " -> " + livraison.getHeureEstime()[0] + ":" + livraison.getHeureEstime()[1]);
    }

    finTourneeEstime[0] = heureDepart + temps.get(entrepot).intValue();
    double temp = ((double) heureDepart + temps.get(entrepot));
    finTourneeEstime[1] = (int) ((temp - (int) temp) * 60.0);
    System.out.println(finTourneeEstime[0] + ":" + finTourneeEstime[1]);
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
    TSP tsp = new TSPBoundSimple();

    // Calcul du meilleur circuit
    tsp.searchSolution(60000, grapheComplet);

    // Construction du circuit de segment et recupération des couts
    Intersection depart, arrive;
    ArrayList<Chemin> list = new ArrayList<Chemin>();
    temps = new HashMap<Intersection, Double>();
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

    updateHeuresLivraison(carte.getEntrepot());

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

  
  /** 
   * @return ArrayList<Segment>
   */
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

  
  /** 
   * @param intersection
   * @return boolean
   */
  public boolean hasLivraison(Intersection intersection) {
    for (Livraison l : livraisons) {
      if (l.getLieu() == intersection) {
        return true;
      }
    }
    return false;
  }


}
