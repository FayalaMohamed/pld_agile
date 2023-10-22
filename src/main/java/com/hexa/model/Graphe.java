package com.hexa.model;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "intersections", "segments" })
public class Graphe extends Observable {

  @XmlElement(name = "intersection")
  private ArrayList<Intersection> intersections;
  @XmlElement(name = "segment")
  private ArrayList<Segment> segments;

  public Graphe() {
    this.intersections = new ArrayList<Intersection>();
    this.segments = new ArrayList<Segment>();
  }

  public Intersection trouverIntersectionParId(String id) {
    for (Intersection inter : intersections) {
      if (inter.getId().equals(id)) {
        return inter;
      }
    }
    return null;
  }

  public void ajouterIntersection(Intersection inter) {
    this.intersections.add(inter);
  }

  public void ajouterSegment(Segment seg) {
    this.segments.add(seg);
  }

  public ArrayList<Intersection> getIntersections() {
    return this.intersections;
  }

  public ArrayList<Segment> getSegments() {
    return this.segments;
  }

}
