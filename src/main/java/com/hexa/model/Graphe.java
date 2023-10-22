package com.hexa.model;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "entrepot", "intersections", "segments" })
public class Graphe extends Observable {

  @XmlElement(name = "intersection")
  private ArrayList<Intersection> intersections;
  @XmlElement(name = "segment")
  private ArrayList<Segment> segments;
  @XmlElement(name = "warehouse")
  private Entrepot entrepot;
  @XmlTransient
  private HashMap<String, Intersection> mappingIntersection;

  public Graphe() {
    this.intersections = new ArrayList<Intersection>();
    this.segments = new ArrayList<Segment>();
    this.mappingIntersection = new HashMap<String, Intersection>();
  }

  public Intersection trouverIntersectionParId(String id) {
    return mappingIntersection.get(id);
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

  public void addMappingIntersection(Intersection intersection) {
    mappingIntersection.put(intersection.getId(), intersection);
  }

  public Intersection getEntrepot() {
    return entrepot;
  }

}
