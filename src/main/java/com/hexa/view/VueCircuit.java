package com.hexa.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hexa.model.Circuit;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;

public class VueCircuit {

    private GraphicalView gv;

    private Circuit circuit;
    private ArrayList<VueSegment> vuesSegmentsCircuit = new ArrayList<VueSegment>();
    private ArrayList<VueLivraison> vuesLivraisonsCircuit = new ArrayList<VueLivraison>();
    private Livraison[] livraisonsTournee;

    private Color color;

    public VueCircuit(Circuit circuit, Livraison[] livraisons, GraphicalView gv, Color color) {
        this.circuit = circuit;
        this.livraisonsTournee = livraisons;
        this.gv = gv;
        this.color = color;

        constructionVuesSegmentCircuit();
    }

    private void constructionVuesSegmentCircuit() {
        Multimap<Intersection, Intersection> segments_tournee = ArrayListMultimap.create();
        Color colorSegment = this.color;

        while (circuit.hasNext()) {

            Segment seg = circuit.next();
            colorSegment = this.color;

            //changement de couleur si on passe 2 fois par le même segment
            boolean already_visited = isAlreadyVisited(seg, segments_tournee, true);
            if (!already_visited) {
                already_visited = isAlreadyVisited(seg, segments_tournee, false);
            }
            if (already_visited) {
                colorSegment = Color.green;
            } else {
                segments_tournee.put(seg.getOrigine(), seg.getDestination());
            }
            //ajout vue segment
            vuesSegmentsCircuit.add(new VueSegment(seg, gv, colorSegment, 3));
            
            int i=1;
            for (Livraison l : livraisonsTournee) {
                if (l.getLieu().equals(seg.getDestination())) {
                    System.out.println("lieu de livraison");
                    vuesLivraisonsCircuit.add(new VueLivraison(l, gv, color, i));
                }
                i++;
            }
        }
    }

    public void dessinerVue() {

        for (VueSegment vs : vuesSegmentsCircuit) {
            vs.dessinerVue();
        }
        for (VueLivraison vl : vuesLivraisonsCircuit) {
            vl.dessinerVue();
        }
    }

    private boolean isAlreadyVisited(Segment seg, Multimap<Intersection, Intersection> segments_tournee, boolean origin) {
        boolean already_visited = false;
        Collection<Intersection> entry_intersections;
        if (origin) {
            entry_intersections = segments_tournee.get(seg.getOrigine());
        } else {
            entry_intersections = segments_tournee.get(seg.getDestination());
        }

        for (Intersection inter_destination : entry_intersections) {
            Intersection compare;
            if (origin) {
                compare = seg.getDestination();
            } else {
                compare = seg.getOrigine();
            }
            if (inter_destination == compare) {
                already_visited = true;
                break;
            }
        }
    return already_visited;
  }
    
}
