package com.hexa.model.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hexa.model.Circuit;
import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.GrapheComplet;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.model.algo.branch_bound.TSPBoundSimple;
import com.hexa.model.algo.dijkstra.Dijkstra;

public class Test {

  /**
   * Créer un grahe complet avec des longueurs de segments aléatoires
   * 
   * @param nb nombre d'intersection à créer dans le graphe
   * @return un graphe complet
 * @throws GrapheException 
   */
  public static Graphe createCompleteGraph(int nb) throws GrapheException {
    Intersection[] inters = new Intersection[nb];

    Entrepot e = new Entrepot(0L, 39.2, 39.3);
    inters[0] = e;

    Graphe g = new Graphe();
    g.setEntrepot(e);

    for (int i = 1; i < nb; i++) {
      inters[i] = new Intersection(Long.valueOf(i), 40.2 + i, 40.3 + i);
      g.ajouterIntersection(inters[i]);
    }

    Segment segment;

    for (int i = 0; i < nb; i++) {
      for (int j = 0; j < nb; j++) {
        if (j == i) {
          continue;
        }
        int nombreAleatoire = 1 + (int) (Math.random() * ((10 - 1) + 1));
        segment = new Segment(inters[i], inters[j], nombreAleatoire, "toto");
        g.ajouterSegment(segment);
      }
    }

    return g;

  }

  /**
   * Calcul le circuit hamiltonien le plus court pour le graphe complet g et
   * l'affiche
   * 
   * @param g
   */
  public static void computeSolutionTSP(GrapheComplet g) {
    TSP tsp = new TSPBoundSimple();

    long startTime = System.currentTimeMillis();
    try {
		tsp.searchSolution(20000, g);
	} catch (AlgoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    System.out.print("Solution of cost " + tsp.getSolutionCost() + " found in "
        + (System.currentTimeMillis() - startTime) + "ms : ");
    for (int i = 0; i < g.getNbIntersections() + 2; i++)
      System.out.print(tsp.getSolution(i).getId() + " ");
  }

  /**
   * Créer une map imaginaire fixe
   * 
   * @return
 * @throws GrapheException 
   */
  public static Graphe createMap() throws GrapheException {

    Intersection[] inters = new Intersection[11];

    Graphe g = new Graphe();
    inters[0] = new Entrepot(0L, 25.1, 21.3);
    g.setEntrepot((Entrepot) inters[0]);

    for (int i = 1; i < 11; i++) {
      inters[i] = new Intersection(Long.valueOf(i), 40.2 + i, 38.6 + i);

      g.ajouterIntersection(inters[i]);
    }

    List<Segment> listSeg = new ArrayList<Segment>();

    listSeg.add(new Segment(inters[0], inters[1], 2*1000, "toto"));
    listSeg.add(new Segment(inters[1], inters[0], 6*1000, "toto"));
    listSeg.add(new Segment(inters[1], inters[2], 5*1000, "toto"));
    listSeg.add(new Segment(inters[2], inters[1], 4*1000, "toto"));
    listSeg.add(new Segment(inters[2], inters[3], 7*1000, "toto"));
    listSeg.add(new Segment(inters[3], inters[2], 1*1000, "toto"));
    listSeg.add(new Segment(inters[4], inters[3], 3*1000, "toto"));
    listSeg.add(new Segment(inters[3], inters[4], 3*1000, "toto"));
    listSeg.add(new Segment(inters[5], inters[4], 6*1000, "toto"));
    listSeg.add(new Segment(inters[5], inters[6], 2*1000, "toto"));
    listSeg.add(new Segment(inters[6], inters[5], 9*1000, "toto"));
    listSeg.add(new Segment(inters[6], inters[7], 1*1000, "toto"));
    listSeg.add(new Segment(inters[7], inters[8], 3*1000, "toto"));
    listSeg.add(new Segment(inters[8], inters[9], 4*1000, "toto"));
    listSeg.add(new Segment(inters[9], inters[0], 2*1000, "toto"));
    listSeg.add(new Segment(inters[0], inters[10], 8*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[9], 1*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[8], 5*1000, "toto"));
    listSeg.add(new Segment(inters[7], inters[10], 6*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[6], 7*1000, "toto"));
    listSeg.add(new Segment(inters[3], inters[10], 1*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[4], 3*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[3], 10*1000, "toto"));
    listSeg.add(new Segment(inters[2], inters[10], 8*1000, "toto"));
    listSeg.add(new Segment(inters[10], inters[2], 3*1000, "toto"));
    listSeg.add(new Segment(inters[1], inters[10], 7*1000, "toto"));
    listSeg.add(new Segment(inters[3], inters[0], 10*1000, "toto"));
    listSeg.add(new Segment(inters[6], inters[0], 2*1000, "toto"));

    for (Segment seg : listSeg) {
      g.ajouterSegment(seg);
    }

    return g;

  }

  public static Tournee createTournee(int nbLivraison, Graphe g) {

    Tournee tournee = new Tournee();

    Intersection[] inters = g.getIntersections();

    for (int i = 0; i < nbLivraison; i++) {
      int nombreAleatoire = 1 + (int) (Math.random() * ((g.getNbIntersections() - 1 - 1) + 1));

      if (tournee.ajouterLivraison(new Livraison(inters[nombreAleatoire])) == false) {
        i--;
      }
    }

    return tournee;
  }

  public static void computeShortestPath(Graphe g) {

    ShortestPath sp = new Dijkstra();

    HashSet<Intersection> exclu = new HashSet<Intersection>();
    exclu.add(new Intersection(1L, 0.0, 0.0));

    sp.searchShortestPath(g, g.getEntrepot(), exclu);

    Intersection[] inters = g.getIntersections();
    for (int i = 0; i < g.getNbIntersections(); i++) {

      if (inters[i].getId() == 1) {
        continue;
      }

      System.out
          .println("Cout de " + g.getEntrepot().getId() + " à " + inters[i].getId() + " = " + sp.getCost(inters[i]));
    }

  }

  public static void main(String[] args) {
    System.out.println("Bonjour");

    
//	  //Test algo TSP
//	  Graphe g;
//	try {
//		g = createCompleteGraph(20);
//		
//		computeSolutionTSP(g);
//		
//	} catch (GrapheException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	  
	  
	  
     

    
	try {
		Graphe g2 = createMap();
		Tournee tournee = createTournee(7, g2);
	    tournee.construireCircuit(g2);
	    
	    //Affichage du circuit
	    Circuit circuit = tournee.getCircuit();
	    Segment seg;
	    while (circuit.hasNext()) {
	    	seg = circuit.next();
	    	System.out.println(seg.getOrigine().getId() + " -> " + seg.getDestination().getId());
	    }
	    
	    
//	    System.out.println("\nLivraisons avec heure estime : ");
//	    
//	    for (Livraison l : tournee.getLivraisons()) {
//	    	System.out.println ("Lieu : " + l.getLieu().getId() + " -> Heure : " + l.getHeureEstime()[0] + " Minutes : " + l.getHeureEstime()[1]);
//	    }

	} catch (GrapheException e) {
		e.printStackTrace();
	} catch (TourneeException e) {
		e.printStackTrace();
	} catch (AlgoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
    
  }

}
