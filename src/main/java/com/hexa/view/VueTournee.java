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
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;

public class VueTournee {

    private GraphicalView gv;

    private Tournee tournee;
    private Circuit circuit;
    private ArrayList<VueLivraison> vuesLivraisons = new ArrayList<VueLivraison>();
    private ArrayList<VueSegment> vuesSegmentsCircuit = new ArrayList<VueSegment>();

    private Color color;

    public VueTournee(Tournee tournee, GraphicalView gv, Color color) {
        this.tournee = tournee;
        this.gv = gv;
        this.color = color;

        setVue(tournee);
    }

    public void setVue(Tournee tournee) {

        this.tournee = tournee;
        this.vuesSegmentsCircuit.clear();
        this.vuesLivraisons.clear();

        try {
            if (tournee.estCalculee()) {
                System.out.println("tournée calculée");
                this.circuit = tournee.getCircuit();
                constructionVuesSegmentCircuit();
            } else {
                System.out.println("tournée non calculée");
                this.circuit = null;
                //simplement création des vues livraison
                for (Livraison l : this.tournee.getLivraisons()) {
                    System.out.println("livraison tournee");
                    vuesLivraisons.add(new VueLivraison(l, this.gv, this.color));
                }
            }
        } catch (TourneeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void dessinerVue() {

        if (tournee.estCalculee()) {
            System.out.println(vuesSegmentsCircuit.size());
            for (VueSegment vs : vuesSegmentsCircuit) {
                vs.dessinerVue();
            }
        }
        
        for (VueLivraison vl : vuesLivraisons) {
            vl.dessinerVue();
        }
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
            this.vuesSegmentsCircuit.add(new VueSegment(seg, gv, colorSegment, 3));
            
            int i=1;
            for (Livraison l : tournee.getLivraisons()) {
                if (l.getLieu().equals(seg.getDestination())) {
                    vuesLivraisons.add(new VueLivraison(l, gv, color, i));
                }
                i++;
            }
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

//   public void updateTournee(Tournee tournee) {
//     this.tournee = tournee;

//     //mise à jour 
//     vuesLivraisons = new ArrayList<VueLivraison>();
//     for (Livraison l : this.tournee.getLivraisons()) {
//         vuesLivraisons.add(new VueLivraison(l, this.gv, this.color));
//     }
//   }

  /**
   * 
   * @return
   */
  public Tournee getTournee() {
    return tournee;
  }

  /**
   * 
   * @return
   */
  public Color getColor() {
    return color;
  }
    
}
