package com.hexa;

import com.hexa.model.Circuit;
import com.hexa.model.Entrepot;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.model.XMLParser;

public class App {
	
	public static void testThomas() {
		System.out.println("Hello World!");
		try {
			Graphe map = XMLParser
					.xmlToGraphe("/home/tboyer/Downloads/fichiersXML2022(1)/fichiersXML2022/smallMap.xml");
			Intersection[] intersections = map.getIntersections();
			for (Intersection intersection : intersections) {
				System.out.println(intersection);
			}
			Entrepot entrepot = map.getEntrepot();
			System.out.println("Entrepot : " + entrepot);
			map.afficher();
			XMLParser.grapheToXml("/tmp/generated_graph.xml", map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param nbLivraison nombre de livraison que l'on veut dans la tournée
	 * @param carte 
	 * @return une tournée avec des livraisons créées aléatoirement
	 */
	public static Tournee createTournee(int nbLivraison, Graphe carte) {

	    Tournee tournee = new Tournee();

	    Intersection[] inters = carte.getIntersections();

	    for (int i = 0; i < nbLivraison; i++) {
	      int nombreAleatoire = 1 + (int) (Math.random() * ((carte.getNbIntersections() - 1 - 1) + 1));

	      if (tournee.ajouterLivraison(new Livraison(inters[nombreAleatoire])) == false) {
	        i--;
	      }
	    }

	    return tournee;

	  }
	
	public static void processPersistanceAlgo(String pathMap, int nbLivraisonsAFaire) {
		
		try {
			
			//Chargement de la carte
			Graphe map = XMLParser.xmlToGraphe(pathMap);
			
			//Création d'une tournée
			Tournee tournee = createTournee(5, map);
			
			//Calcul du meilleur circuit
			tournee.construireCircuit(map);
			
			//Affichage du circuit
		    Circuit circuit = tournee.getCircuit();
		    Segment seg;
		    while (circuit.hasNext()) {
		    	seg = circuit.next();
		    	System.out.println(seg.getOrigine().getId() + " -> " + seg.getDestination().getId());
		    }
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		
		processPersistanceAlgo("Map_XML/smallMap.xml", 10);
		
	}
}
