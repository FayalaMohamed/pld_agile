package com.hexa.model;

import java.util.HashMap;
import java.util.Map;

import com.hexa.model.algo.ShortestPath;
import com.hexa.model.algo.dijkstra.Dijkstra;

public class GrapheComplet extends Graphe {

//------------------------------------------------------------------------------------------------------------------

	/**
	 * Table associant chaque segment du graphe complet avec le chemin emprunté pour
	 * le créer
	 */
	private Map<Segment, Chemin> cheminsPlusCourt;

//------------------------------------------------------------------------------------------------------------------

	/**
	 * Construit un graphe complet ayant pour sommet l'ensemble des lieux de
	 * livraisons de la tournée. Un segment entre deux intersections du graphe
	 * complet est le plus court chemin entre ces deux intersections dans le graphe
	 * en paramètre du constructeur. Chaque segment est calculé avec l'algorithme de
	 * Dijkstra. Le chemin calculé pour aller d'un sommet à un autre est mémorisé
	 * dans une table.
	 * 
	 * Un GrapheComplet hérite de Graphe
	 * 
	 * @param carte   le graphe représentant la carte
	 * @param tournee la tournée de livraison
	 * @throws GrapheException
	 * @throws TourneeException
	 */
	public GrapheComplet(Graphe carte, Tournee tournee) throws GrapheException, TourneeException {
		super();
		if (carte == null || tournee == null)
			throw new GrapheException("Graphe ou tournée null");

		cheminsPlusCourt = new HashMap<Segment, Chemin>();

		ShortestPath sp = new Dijkstra();

		if (carte.entrepot != null) {
			super.setEntrepot(carte.entrepot);
		}

		// Ajout des lieux de livraison et verification qu'ils sont bien sur la carte =>
		// création des sommets
		Livraison[] livraisons = tournee.getLivraisons();
		for (int i = 0; i < tournee.getNbLivraisons(); i++) {

			if (carte.hasIntersection(livraisons[i].getLieu())) {
				super.ajouterIntersection(livraisons[i].getLieu());
			} else {
				throw new TourneeException("Tournee invalide : un point de livraison n'appartient pas à la carte");
			}

		}

		// Calcul du plus court chemin de l'entrepot à chaque lieu de livraisons
		Segment s;
		if (entrepot != null) {
			sp.searchShortestPath(carte, entrepot, null);
			for (Intersection inter : intersections) {
				s = new Segment(entrepot, inter, sp.getCost(inter), "toto");
				super.ajouterSegment(s);

				this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(inter)));
			}
		}

		// Calcul des plus courts chemin à partir de chaque lieu de livraison
		for (Intersection depart : intersections) {
			sp.searchShortestPath(carte, depart, null);

			s = new Segment(depart, entrepot, sp.getCost(entrepot), null);

			super.ajouterSegment(s);

			this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(entrepot)));

			for (Intersection arrive : intersections) {
				if (!arrive.equals(depart)) {
					s = new Segment(depart, arrive, sp.getCost(arrive), null);

					super.ajouterSegment(s);
					this.cheminsPlusCourt.put(s, new Chemin(sp.getSolPath(arrive)));
				}
			}
		}

	}

//------------------------------------------------------------------------------------------------------------------

	/**
	 * Retourne le chemin (la liste des segments du graphe d'origine) correspondant
	 * un segment du graphe complet
	 * 
	 * @param s un segment du graphe complet
	 * @return le chemin le plus court pour aller de l'origine à la destination du
	 *         Segment en paramètre
	 */
	public Chemin getChemin(Segment s) {
		return cheminsPlusCourt.get(s);
	}

//------------------------------------------------------------------------------------------------------------------

	/**
	 * Surcharge de la méthode de graphe afin de l'interdire pour un graphe complet
	 * 
	 * @param inter
	 * @return rien
	 * @throws GrapheException
	 */
	public boolean ajouterIntersection(Intersection inter) throws GrapheException {
		throw new GrapheException("Impossible d'ajouter une intersection à un graphe complet");
	}

	/**
	 * Surcharge de la méthode de graphe afin de l'interdire pour un graphe complet
	 * 
	 * @param seg
	 * @return true si n'est pas déjà présent dans le graphe
	 */
	public boolean ajouterSegment(Segment seg) throws GrapheException {
		throw new GrapheException("Impossible d'ajouter un segment à un graphe complet");
	}

}
