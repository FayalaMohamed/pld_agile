package com.hexa.model.algo.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.ShortestPath;

public class Dijkstra implements ShortestPath{
	
	/**
	 * Table associant à une intersection le cout minimal pour l'atteindre
	 */
	private Map<Intersection, Double> cout;
	
	/**
	 * Table associant chaque intersection au segment qui permet de l'atteindre
	 */
	private Map<Intersection, Segment> pi;
	
	
	private enum Couleur {
		BLANC, GRIS, NOIR
	};
	
	/**
	 * Table associant chaque intersection à sa couleur
	 */
	private Map<Intersection, Couleur> coloriage;
	
	/**
	 * Tas binaire des intersections atteintes mais pas visités complétement (=grises) 
	 */
	private TasIntersection gris;
	
	/**
	 * Permet de savoir si on a fait au moins une recherche de plus court chemin
	 */
	private boolean searchOK;
	
	/**
	 * Constructeur
	 * 
	 * Initialise les tables
	 */
	public Dijkstra() {
		cout = new HashMap<Intersection, Double>();
		pi = new HashMap<Intersection, Segment>();
		coloriage = new HashMap<Intersection, Couleur>();
		gris = new TasIntersection();
		searchOK = false;
	}
	
	
	/** Implementation de l'algorithme de Dijkstra pour calculer le plus court chemin 
	 * de l'Intersection de départ vers toutes les autres Intersections du graphe
	 * @param carte
	 * @param origine
	 * @param exclu
	 */
	@Override
	public void searchShortestPath (Graphe carte, Intersection origine, Set<Intersection> exclu) {
		if (carte == null)
			return;
		if (exclu == null) {
			exclu = new HashSet<Intersection>();
		}
		
		//Init
		Intersection[] S = carte.getIntersections();
		
		for (Intersection si : S) {
			
			if (! exclu.contains(si)) {
				cout.put(si, Double.MAX_VALUE);
				pi.put(si, null);
				coloriage.put(si, Couleur.BLANC);
			}
		}
		
		if (! exclu.contains(carte.getEntrepot())) {
			cout.put(carte.getEntrepot(), Double.MAX_VALUE);
			pi.put(carte.getEntrepot(), null);
			coloriage.put(carte.getEntrepot(), Couleur.BLANC);
		}
		
		//Def. origine
		if(cout.containsKey(origine)){
		cout.replace(origine, 0.0);
		coloriage.replace(origine, Couleur.GRIS);
		gris.insert(origine, cout);
	}
		
		
		//Algo
		while (gris.getSize() > 0) {
			
			Intersection si = gris.extract(cout);
			Intersection[] successeurs = carte.getSuccesseur(si);
			
			for (Intersection sj : successeurs) {
				
				if (exclu.contains(sj)) {
					continue;
				}
				
				if (coloriage.get(sj) != Couleur.NOIR) {
					relacher(new Segment(si, sj), carte);
					if ( coloriage.replace(sj, Couleur.BLANC, Couleur.GRIS) )  {
						
						gris.insert(sj, cout);
						
					}
				}
			}
			
			coloriage.replace(si, Couleur.NOIR);
			
		}
		
		searchOK = true;
		
	}
	
	
	/** Retourne le cout du plus court chemin de l'Intersection de départ choisie pour faire le calcul
	 *  pour arriver à l'Intersection en paramétre (-1 si l'algorithme n'a pas été exécuté ou l'intersection n'existe pas dans le graphe)
	 * @param inter
	 * @return double
	 */
	@Override
	public double getCost(Intersection inter) {
		return searchOK && cout.containsKey(inter)? cout.get(inter)  : -1.0;
	}
	
	
	/** Retourne la liste des Segments correspondant au chemin le plus court pour arriver 
	 * à l'Intersection souhaitée à partir de l'Intersection de départ choisie pour faire le calcul
	 * @param dest
	 * @return List<Segment>
	 */
	@Override
	public List<Segment> getSolPath(Intersection dest) {
		
		if (!searchOK || !cout.containsKey(dest)) {
			return null;
		}
		
		List<Segment> path = new ArrayList<Segment>();
		
		while (pi.get(dest) != null) {
			path.add(0, pi.get(dest));
			dest = pi.get(dest).getOrigine();
		}
		
		return path;
		
	}
	
	
	/** Fonction utilisée par l'algorithme de dijkstra pour relacher les arcs et mettre à jour le cout pour arriver au sommet
	 * @param seg
	 * @param g
	 */
	private void relacher(Segment seg, Graphe g) {
		
		double temp = cout.get(seg.getOrigine()) + g.getCost(seg);
		if (cout.get(seg.getDestination()) > temp) {
			cout.replace(seg.getDestination(), temp);
			pi.replace(seg.getDestination(), seg);
		}
		
	}
	
}
