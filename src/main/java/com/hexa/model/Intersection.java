package com.hexa.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "latitude", "longitude" })
public class Intersection {
  private int id;
  private double latitude;
  private double longitude;
  private double x;
  private double y;

  public Intersection() {
  }

  public Intersection(int id, double longitude, double latitude) {
    this.id = id;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  @XmlAttribute(name = "latitude")
  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  @XmlAttribute(name = "longitude")
  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @XmlTransient
  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  @XmlTransient
  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  @XmlAttribute(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
