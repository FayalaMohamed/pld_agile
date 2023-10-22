package com.hexa.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "intersections", "segments" })
public class Graphe extends Observable {

  @XmlElement(name = "intersection")
  private Set<Intersection> intersections;
  @XmlElement(name = "segment")
  private Set<Segment> segments;

  public Graphe() {
    this.intersections = new HashSet<Intersection>();
    this.segments = new HashSet<Segment>();
  }

  public Intersection trouverIntersectionParId(Intersection id) {
    return null;
  }

  public void ajouterIntersection(Intersection inter) {
    this.intersections.add(inter);
  }

  public void ajouterSegment(Segment seg) {
    this.segments.add(seg);
  }

  public Set<Intersection> getIntersections() {
    return this.intersections;
  }

  public Set<Segment> getSegments() {
    return this.segments;
  }

}
