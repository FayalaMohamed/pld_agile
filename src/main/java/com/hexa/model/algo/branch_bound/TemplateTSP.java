package com.hexa.model.algo.branch_bound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.algo.TSP;

abstract class TemplateTSP implements TSP {
	
	private Intersection[] bestSol;
	protected Graphe g;
	private double bestSolCost;
	private int timeLimit;
	private long startTime;
	private int sizeBestSol;

	@Override
	public void searchSolution(int timeLimit, Graphe g) {
		
		//Def. limite de temps 
		if (timeLimit <= 0 ) return;
		startTime = System.currentTimeMillis();	
		this.timeLimit = timeLimit;
		
		//Def. graphe
		this.g = g;
		if (g == null)
			return;
		
		//Creation tableau du circuit solution
		sizeBestSol = g.getNbIntersections() +2; //pour l'entrepot au depart et à l'arrivé
		bestSol = new Intersection[sizeBestSol]; 
		
		//Creation liste des sommets visites et non visites
		Intersection[] inters = g.getIntersections();
		List<Intersection> unvisited = new ArrayList<Intersection>(g.getNbIntersections()+1);
		for (int i=0; i<g.getNbIntersections(); i++) {
			unvisited.add(inters[i]);
		}
		
		List<Intersection> visited = new ArrayList<Intersection>(g.getNbIntersections()+1);
		visited.add(g.getEntrepot()); 
		
		//Def. cout de la solution
		bestSolCost = Double.MAX_VALUE;
		
		//Lancement algo branch and bound
		branchAndBound(g.getEntrepot(), unvisited, visited, 0.0);

		//Rajout de l'entrepot à la fin de la solution
		bestSol[sizeBestSol - 1] = g.getEntrepot();
		if(bestSolCost == Double.MAX_VALUE)
			bestSolCost = -1;
	}

	@Override
	public Intersection getSolution(int i) {
		if (g != null && i >= 0 && i < sizeBestSol) {
			return bestSol[i];
		}
		return null;
	}

	@Override
	public double getSolutionCost() {
		if (g != null) {
			return bestSolCost;
		}
		return -1;
	}


	
	/**
	 * Algorithme de branch and bound pour résoudre le TSP dans le graphe g
	 * @param sommetCourant le dernier sommet visité
	 * @param unvisited la liste des sommets qui n'ont pas encore été visités
	 * @param visited la séquence de sommet qui a déjà été visitée (incluant sommetCourant)
	 * @param currentCost le cout du chemin correspondant à visited
	 */	
	private void branchAndBound(Intersection sommetCourant, List<Intersection> unvisited, List<Intersection> visited, double currentCost){
		
		//Si on depasse le temps
		if (System.currentTimeMillis() - startTime > timeLimit) {
			return;
		}
		
		
		//Si on a visité tout le monde
	    if (unvisited.size() == 0) { 
	    	Segment s = new Segment(sommetCourant, g.getEntrepot());
	    	if (g.hasSegment(s)){ 
	    		//Si le cout de ce circuit est inférieur au précédent
	    		if (currentCost+g.getCost(s) < bestSolCost){ 
	    			//On l'enregistre
	    			visited.toArray(bestSol);
	    			bestSolCost = currentCost+g.getCost(s);
	    		}
	    	}
	    }
	    
	    
	    //Sinon on teste si ça vaut le coup de poursuivre avec ce début de circuit	
	    else if (currentCost + bound(sommetCourant, unvisited) < bestSolCost) {
	    	Iterator<Intersection> it = iterator(sommetCourant, unvisited);
	    	
	    	//On tente d'ajouter un par un les sommets non visités
	    	while (it.hasNext()) {
	    		Intersection sommetSuivant = it.next();
	    		
	    		visited.add(sommetSuivant);
	    		unvisited.remove(sommetSuivant);
	    		
	    		int nbVisited = visited.size()-1;
	    		boolean hasCrosses = false;
	    		
	    		//Si l'ajout de visiter créer des croisements alors ce n'est pas la peine de continuer
	    		for (int i = 0; i < nbVisited -1; i++) {
	    			if (croiser(visited.get(i), visited.get(i+1), visited.get(nbVisited -1), visited.get(nbVisited))) {
	    				hasCrosses = true;
	    				break;
	    			}
	    		}
	    		
	    		if (!hasCrosses) {
		    		Segment s = new Segment(sommetCourant, sommetSuivant);
		    		branchAndBound(sommetSuivant, unvisited, visited, currentCost + g.getCost(s));
	    		}
	    		
	    		visited.remove(sommetSuivant);
	    		unvisited.add(sommetSuivant);
	    	}
	    	
    	}
	    	
	   
	}
	
	/**
	 * 
	 * @param sommetCourant
	 * @param unvisited
	 * @return une borne inférieure de la longueur du plus court chemin allant du dernier sommet visité jusqu’à 0 en passant par chaque sommet non visité exactement une fois
	 */
	protected abstract double bound(Intersection sommetCourant, List<Intersection> unvisited);

	/**
	 * 
	 * @param sommetCourant
	 * @param unvisited
	 * @return un itérateur sur les sommets encore non visités
	 */
	protected abstract Iterator<Intersection> iterator(Intersection sommetCourant, List<Intersection> unvisited);

	/**
	 * Eviter les croisements d'arcs permet de réduire le cout du circuit
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @return true si l'arc (i,j) croise l'arc (k,l) 
	 */
	private boolean croiser (Intersection i, Intersection j, Intersection k, Intersection l) {
		
		Segment a = new Segment (i, j);
		Segment b = new Segment (k, l);
		Segment c = new Segment (i, k);
		Segment d = new Segment (j, l);
		
		return (g.getCost(a) + g.getCost(b) > g.getCost(c) + g.getCost(d));
		
	}
	
}
