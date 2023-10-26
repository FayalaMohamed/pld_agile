package com.hexa.model;

import java.util.*;

import com.hexa.model.algo.TSP;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;

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
	 * Ajoute une livraisons
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
		return this.livraisons.add(l);
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
		tsp.searchSolution(20000, grapheComplet);

		// Construction du circuit de segment et recupération des couts
		Intersection depart, arrive;
		ArrayList<Chemin> list = new ArrayList<Chemin>();
		Map<Intersection, Double> cout = new HashMap<Intersection, Double>();
		Double coutTotal = 0.0;
		int i = 0;
		while ((depart = tsp.getSolution(i)) != null && (arrive = tsp.getSolution(i + 1)) != null) {
			i++;

			Segment seg = new Segment(depart, arrive);
			list.add(grapheComplet.getChemin(seg));

			coutTotal += grapheComplet.getCost(seg);
			cout.put(arrive, coutTotal);
			coutTotal += 5.0 / 60.0; // 5 min de battement à ajouter pour faire la livraison

		}

		// Calcul des heures de passage (delta de 5 min à chaque livraison)
		Double longueur;
		int heureDepart = 8;
		int[] tab = new int[2];
		for (Livraison l : livraisons) {

			if ((longueur = cout.get(l.getLieu())) != null) {

				longueur /= 1000; // m -> km
				longueur /= 15.0; // division par la vitesse pour obtenir le temps en h

				// Paramètrage de la livraison
				tab[0] = heureDepart + longueur.intValue();
				double temp = ((double) heureDepart + longueur);
				tab[1] = (int) ((temp - (int) temp) * 60.0);

				l.setHeureEstime(tab[0], tab[1]);

				tab[1] = tab[0] + 1;
				l.setPlageHoraire(tab[0], tab[1]);

			}

			else {
				throw new TourneeException("Cout d'une livraison inexistant");
			}

		}

		finTourneeEstime[0] = heureDepart + cout.get(grapheComplet.getEntrepot()).intValue();
		double temp = ((double) heureDepart + cout.get(grapheComplet.getEntrepot()).intValue());
		finTourneeEstime[1] = (int) ((temp - (int) temp) * 60.0);

		circuit = new Circuit(list);
		circuitCalculer = true;
    
    this.notifyObservers(this);

	}

	/**
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

}