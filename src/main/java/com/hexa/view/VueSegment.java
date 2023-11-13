package com.hexa.view;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class VueSegment {

    private GraphicalView gv;
    
    private Segment segment;
    private Intersection origine;
    private Intersection destination;

    private Color color;
    private BasicStroke stroke;
    
    private Coordonnees coordOrigine;
    private Coordonnees coordDestination;

    public VueSegment(Segment s, GraphicalView gv, Color color){
        this.gv = gv;
        this.segment = s;
        this.origine = segment.getOrigine();
        this.destination = segment.getDestination();

        this.stroke = new BasicStroke((float) 0.5);

        this.color = color;
    }

    public VueSegment(Segment s, GraphicalView gv, Color color, int stroke){
        this.gv = gv;
        this.segment = s;
        this.origine = segment.getOrigine();
        this.destination = segment.getDestination();

        this.stroke = new BasicStroke(stroke);

        this.color = color;
    }

    public void dessinerVue() {

        coordOrigine = this.gv.CoordGPSToViewPos(origine);
        coordDestination = this.gv.CoordGPSToViewPos(destination);

        Graphics2D g2 = (Graphics2D) gv.getGraphics();
        g2.setColor(color);
        g2.setStroke(stroke);
        g2.draw(new Line2D.Float(coordOrigine.getX(), coordOrigine.getY(), coordDestination.getX(), coordDestination.getY()));
    }
}
