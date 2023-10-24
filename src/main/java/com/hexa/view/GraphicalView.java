package com.hexa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;

import javafx.scene.layout.GridPane;

public class GraphicalView extends JPanel {

    private static final long serialVersionUID = 1L;
	private int scale;
	private int viewHeight;
	private int viewWidth;
	private Graphics g;

    private Graphe carte;
    private ArrayList<Intersection> intersections;
    private ArrayList<Segment> segments;

    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;

    public GraphicalView(Window w) {
        super();

        viewWidth = (int)(w.getWidth()*0.6);
        viewHeight = w.getHeight();

        setSize(viewWidth, viewHeight);
        setBackground(Color.white);
        w.getContentPane().add(this);
    }

    public GraphicalView(Graphe carte, Window w) {
        super();

        setLayout(null);
        setBackground(Color.white);

		setSize(viewWidth, viewHeight);

        this.carte = carte;
        this.intersections = carte.getIntersections();
        this.segments = carte.getSegments();

        latitudeMax = -90; latitudeMin = 90; longitudeMax = -180; longitudeMin = 180;
        definirExtremesCoordonnees();

        repaint();
    }

    public void dessinerIntersection(Intersection i) {
        System.out.println("appel dessin intersection");
        int r = 1;
        int xpos = (int)((i.getLongitude() - longitudeMin)/(longitudeMax - longitudeMin)*viewWidth);
        int ypos = (int)((i.getLatitude() - latitudeMin)/(latitudeMax - latitudeMin)*viewHeight);
        System.out.println("xpos : " + xpos + " / ypos : " + ypos);

        g.fillOval(xpos, ypos, r, r);
        System.out.println("fin dessin intersection");
    }

    private void definirExtremesCoordonnees() {

        Iterator<Intersection> it = intersections.iterator();
        while(it.hasNext()) {
            
            Intersection i = it.next();

            double latitude = i.getLatitude();
            double longitude = i.getLongitude();

            if (latitude > latitudeMax) latitudeMax = latitude;
            if (latitude < latitudeMin) latitudeMin = latitude;
            if (longitude > longitudeMax) longitudeMax = longitude;
            if (longitude < longitudeMin) longitudeMin = longitude;
        }
        System.out.println("latitude min : " + latitudeMin + " / latitude max : " + latitudeMax);
        System.out.println("longitude min : " + longitudeMin + " / longitude max : " + longitudeMax);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.blue);
    }

    public int getViewHeight() {
		return viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}
    
}
