package com.hexa.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hexa.model.algo.AlgoException;
import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;
import com.hexa.model.algo.dijkstra.Dijkstra;
import com.hexa.observer.Observable;

public class Tournee extends Observable {

  // -----------------------------------------------------------------------------------------------------

  /**
   * Ensemble des livraisons à effectuer (Set empêche les doublons)
   */
  private Set<Livraison> livraisons;

  private Livreur livreur;

  private int[] finTourneeEstime; // 0: heure | 1: minute

  /**
   * Pour chaque lieu de livraison, enregistre le temps relatifs d'arrivée en
   * minutes
   */
  private Map<Intersection, Double> temps;

  /**
   * Circuit (=sequence de segments) à parcourir par le livreur pour faire toutes
   * les livraisons
   */
  private Circuit circuit;
  private boolean circuitCalculer;

  // -----------------------------------------------------------------------------------------------------

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

  // -----------------------------------------------------------------------------------------------------

  /**
   * Retourne true si la tournee est calculee
   *
   * @return boolean
   */
  public boolean estCalculee() {
    return circuitCalculer;
  }

  /**
   * 
   * @param inter
   * @return true si inter est un lieu de livraison
   */
  public boolean estLieuLivraison(Intersection inter) {
    return livraisons.contains(new Livraison(inter));
  }

  /**
   * Retourne une livraison associé à une intersection
   *
   * @param intersection
   *
   */
  public Livraison getLivraison(Intersection intersection) {
    Livraison livraison = null;
    for (Livraison l : livraisons) {
      if (l.getLieu().equals(intersection)) {
        livraison = l;
        break;
      }
    }
    return livraison;
  }

  /**
   * Retourne le circuit calculé s'il est calculé sinon throws une Exception
   *
   * @return le meilleur circuit à prendre pour faire la tournée
   * @throws TourneeException
   */
  public Circuit getCircuit() throws TourneeException {
    if (circuitCalculer) {
      circuit.reset();
      return circuit;
    } else {
      throw new TourneeException("Le circuit n'a pas encore été calculé");
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

  public Livreur getLivreur() {
    return livreur;
  }

  // -----------------------------------------------------------------------------------------------------

  public void setLivreur(Livreur livreur) {
    this.livreur = livreur;
  }

  /**

	 * @param livraisons
	 */
	public void setLivraisons(Set<Livraison> livraisons) {
		//this.livraisons = livraisons;
		this.livraisons = new HashSet<Livraison>();
		this.livraisons.addAll(livraisons);
		this.notifyObservers(this);
	}

  /**
   * Attribut un circuit à une tournée Méthode utile pour la fonctionnalité undo
   * redo Permet de mettre le booleen circuitCalculer à true ou false Selon si le
   * circuit c est null ou pas
   * 
   * @param c le meilleur circuit à prendre pour faire la tournée
   *
   */
  public void setCircuit(Circuit c) {
    circuit = c;
    circuitCalculer = circuit != null;
    this.notifyObservers(this);
  }

  // -----------------------------------------------------------------------------------------------------

  /**
   * Ajoute une livraisons et notifie les observeurs uniquement si le circuit n'a
   * pas été calculé
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
   * Supprime une livraison de la tournee et notifie les observeurs
   * 
   * @param intersection
   */
  public void supprimerLivraison(Intersection intersection) {
    for (Livraison l : livraisons) {
      if (l.getLieu().equals(intersection)) {
        livraisons.remove(l);
        break;
      }
    }
    this.notifyObservers(this);
  }

  /**
   * Supprime une livraison de la tournee et notifie les observeurs
   * 
   * @param livraison
   * @throws TourneeException
   */
  public boolean supprimerLivraisonApresCalcul(Livraison livraison, Graphe carte) throws TourneeException {
    if (!circuitCalculer
        || !livraisons.contains(livraison)) {
      return false;
    }

    livraisons.remove(livraison);
    if (livraisons.size() == 0) {
      circuit = null;
      circuitCalculer = false;
      finTourneeEstime = new int[2];
      return true;
    }

    Intersection entrepot = carte.getEntrepot();

    // Recherche de l'intersection précédente et suivante le lieu de livraison à
    // supprimer dans le circuit original
    circuit.reset();
    boolean trouve = false;
    Intersection intersectionDeLivraisonPrecedente = null;
    Intersection intersectionDeLivraisonSuivante = null;
    Segment iterator = null;
    while (circuit.hasNext()) {
      iterator = circuit.next();
      if ((estLieuLivraison(iterator.getOrigine()) || iterator.getDestination() == entrepot) && !trouve) {
        intersectionDeLivraisonPrecedente = iterator.getOrigine();
      } else if (trouve && estLieuLivraison(iterator.getOrigine())) {
        intersectionDeLivraisonSuivante = iterator.getOrigine();
        break;
      } else if (trouve && iterator.getDestination() == entrepot) {
        intersectionDeLivraisonSuivante = iterator.getDestination();
        break;
      }
      if (iterator.getOrigine() == livraison.getLieu()) {
        trouve = true;
      }
    }

    // calcul des temps et du delta à propager
    ShortestPath dijkstra = new Dijkstra();
    dijkstra.searchShortestPath(carte, intersectionDeLivraisonPrecedente, null);
    Chemin cheminPreToSuiv = new Chemin(dijkstra.getSolPath(intersectionDeLivraisonSuivante));
    double delta = temps.get(intersectionDeLivraisonSuivante) - temps.get(intersectionDeLivraisonPrecedente)
        - dijkstra.getCost(intersectionDeLivraisonSuivante) / 1000.0 / 15.0 - 5.0 / 60.0;

    temps.put(intersectionDeLivraisonSuivante, temps.get(intersectionDeLivraisonSuivante) - delta);
    while (circuit.hasNext()) { // on reset pas le circuit mais on repart de là où on s'est arrété
      iterator = circuit.next();
      if (livraisons.contains(new Livraison(iterator.getOrigine()))) {
        temps.put(iterator.getOrigine(), temps.get(iterator.getOrigine()) - delta);
      }
    }
    temps.put(carte.getEntrepot(), temps.get(carte.getEntrepot()) - delta);
    updateHeuresLivraison(carte.getEntrepot());

    // MAJ du circuit en ajoutant le chemin calculé au bon endroit
    MAJCircuitSuppressionApresCalcul(carte.getEntrepot(), intersectionDeLivraisonPrecedente,
        intersectionDeLivraisonSuivante, cheminPreToSuiv);

    // On notifie les observeurs que la tournée à changer
    notifyObservers(this);

    return true;
  }

  /**
   * 
   * Construit le meilleur circuit pour réaliser la tournée à partir de la carte
   *
   * @param carte
   * @throws TourneeException
   * @throws GrapheException
 * @throws AlgoException 
   */
  public void construireCircuit(Graphe carte) throws GrapheException, TourneeException, AlgoException {
    if (circuitCalculer) {
      return;
    }

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
   * Ajoute une livraison à la tournée après le calcul du circuit Le circuit est
   * modifié de sorte que la livraisonAjouter se fasse après la
   * livraisonPrecedente.
   * 
   * @param carte
   * @param livraisonAjouter
   * @param livraisonPrecedente
   * @return
   * @throws TourneeException
   * @throws GrapheException
   */
  public boolean ajouterLivraisonApresCalcul(Graphe carte, Livraison livraisonAjouter, Livraison livraisonPrecedente)
      throws TourneeException, GrapheException {

    if (!circuitCalculer
        || (!livraisons.contains(livraisonPrecedente)
            && !carte.getEntrepot().equals(livraisonPrecedente.getLieu()))
        || livraisons.contains(livraisonAjouter)) {
      return false;
    }

    this.livraisons.add(livraisonAjouter);

    // Recherche de l'intersection suivant le lieu de livraisonPrecedente dans le
    // circuit original
    circuit.reset();
    boolean save = false;
    Intersection intersectionSuivante = null;

    while (circuit.hasNext()) {
      intersectionSuivante = circuit.next().getOrigine();
      if (intersectionSuivante.equals(livraisonPrecedente.getLieu())) {
        save = true;
        continue;
      }
      if (save && livraisons.contains(new Livraison(intersectionSuivante))) {
        break;
      }
    }

    if (temps.containsKey(intersectionSuivante) == false) {
      // au cas où l'intersection précédente était le dernier lieu avant de rentrer à
      // l'entrepot
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

    // MAJ des heures d'arrivées et des plages horaires pour chaque objet Livraison
    // en utilisant la map temps à jour
    updateHeuresLivraison(carte.getEntrepot());

    // MAJ du circuit en ajoutant les 2 chemins calculés au bonne endroit
    MAJCircuitAjoutApresCalcul(carte.getEntrepot(), livraisonPrecedente.getLieu(), cheminPreToNew, cheminNewtoNext);

    // On notifie les observeurs que la tournée à changer
    notifyObservers(this);
    return true;
  }

  // -----------------------------------------------------------------------------------------------------

  /**
   * Rajoute au circuit une bifurcation après intersectionPrecedente (afin
   * d'ajouter une livraison après calcul)
   * 
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

      if (seg.getOrigine().equals(intersectionPrecedente)) {
        listChemin.add(new Chemin(listSeg));
        listSeg.clear();

        listChemin.add(cheminPreToNew);
        listChemin.add(cheminNewtoNext);

        ignore = true;

      }

      else if (livraisons.contains(new Livraison(seg.getOrigine())) || entrepot.equals(seg.getDestination())) {

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
   * Rajoute au circuit une bifurcation après intersectionPrecedente (afin
   * d'ajouter une livraison après calcul)
   * 
   * @param entrepot
   * @param intersectionPrecedente
   * @param cheminPreToNew
   * @param cheminNewtoNext
   */
  private void MAJCircuitSuppressionApresCalcul(Intersection entrepot, Intersection intersectionPrecedente,
      Intersection intersectionSuivante,
      Chemin cheminPreToSuiv) {
    ArrayList<Chemin> listChemin = new ArrayList<Chemin>();
    ArrayList<Segment> listSeg = new ArrayList<Segment>();

    circuit.reset();
    boolean ignore = false;
    while (circuit.hasNext()) {
      Segment seg = circuit.next();

      if (!ignore) {
        listSeg.add(seg);
      }

      if (!ignore && livraisons.contains(new Livraison(seg.getDestination())) || entrepot.equals(seg.getDestination())) {
        listChemin.add(new Chemin(listSeg));
        listSeg.clear();
      }

      if (seg.getDestination() == intersectionPrecedente) {
        listChemin.add(cheminPreToSuiv);

        ignore = true;
      }

      if (seg.getDestination() == intersectionSuivante) {
        ignore = false;
      }
    }

    circuit = new Circuit(listChemin);
  }

  /**
   * Met à jour les horaires et les plages horaires de toutes les livraisons en
   * utilisant la map temps
   * 
   * @param entrepot
   * @throws TourneeException
   */
  private void updateHeuresLivraison(Intersection entrepot) throws TourneeException {
    // Calcul des heures réelles de passage
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
        throw new TourneeException("Horaire d'arrivée d'une livraison incalculable : " + l.getLieu().getId());
      }

    }

    finTourneeEstime[0] = heureDepart + temps.get(entrepot).intValue();
    double temp = ((double) heureDepart + temps.get(entrepot));
    finTourneeEstime[1] = (int) ((temp - (int) temp) * 60.0);
  }

  /**
   * Génère la feuille de route correspondant à la tournée calculée
   * 
   * @param carte
   */
  private void genererFeuilleDeRoute(Graphe carte) {

    circuit.reset();

    String nomFichier = "Feuille_de_route" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
    String text = "Tournée calculée le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " - Livreur "
        + livreur + "\n\n";

    ArrayList<String> nomsVisites = new ArrayList<String>();
    String nomPremierSegment = carte.getNomSegment(circuit.next());
    nomsVisites.add(nomPremierSegment);
    text += "Prendre sur " + nomPremierSegment + "\n";

    while (circuit.hasNext()) {
      String nomSegment = carte.getNomSegment(circuit.next());
      if (!nomsVisites.get(nomsVisites.size() - 1).equals(nomSegment)) {

        nomsVisites.add(nomSegment);

        if (nomsVisites.size() == 1) {
          text = text + "Prendre ";
        } else {
          int rand = (int) (Math.random() * 10);
          if (rand > 5)
            text = text + "Continuer sur ";
          else
            text = text + "Tourner sur ";
        }

        text += nomSegment + "\n";
      }
    }

    sauvegarderFeuilleDeRoute(text, nomFichier);
  }

  /**
   * Sauvegarde la feuille de route créé précédemment dans le projet
   * 
   * @param feuilleDeRoute
   * @param nomFichier
   */
  private void sauvegarderFeuilleDeRoute(String feuilleDeRoute, String nomFichier) {

    try {
      String path = "./Feuilles_Route/" + nomFichier;
      BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
      bw.write(feuilleDeRoute);
      bw.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
