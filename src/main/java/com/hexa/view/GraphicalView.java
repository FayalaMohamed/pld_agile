package com.hexa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexa.model.Coordonnees;
import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Segment;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;
import com.hexa.view.object.VueEntrepot;
import com.hexa.view.object.VueIntersection;
import com.hexa.view.object.VueSegment;
import com.hexa.view.object.VueTournee;

public class GraphicalView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int viewHeight;
	private int viewWidth;
	private Graphics g;

	private Graphe carte;

	private ArrayList<VueIntersection> vuesIntersections = new ArrayList<VueIntersection>();
	private ArrayList<VueSegment> vuesSegments = new ArrayList<VueSegment>();
	private VueEntrepot vueEntrepot;
	private ArrayList<VueTournee> vuesTournees = new ArrayList<VueTournee>();

	private double latitudeMin;
	private double latitudeMax;
	private double longitudeMin;
	private double longitudeMax;

	private Coordonnees coordonneesMin;
	private Coordonnees coordonneesMax;

	private int viewX = 0;
	private int viewY = 0;
	private double zoomFactor = 1.0;

	private final Color[] couleursTournees = { Color.RED, Color.BLACK, Color.CYAN };
	private int nbTournees = 0;
	
	
//------------------------------------------------------------------------------------------------------------------------
	

	/**
	 * Crée la vue graphique correspondant à une tournée dans une fenêtre
	 * 
	 * @param w
	 * @param size
	 */
	public GraphicalView(Window w, Dimension size) {
		super();

		viewWidth = size.width; // 1000
		viewHeight = size.height; // 700

		setSize(viewWidth, viewHeight);
		setBackground(Color.white);
		w.getContentPane().add(this);
	}
	

//------------------------------------------------------------------------------------------------------------------------

	
	public int getViewHeight() {
		return viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}
	
	public Graphics getGraphics2() {
		return g;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}
	
	
//------------------------------------------------------------------------------------------------------------------------

	public void setZoomFactor(int notches) {
		double temp = zoomFactor;
		if (notches < 0) {
			// Zoom in
			zoomFactor *= 1.1;
			if (zoomFactor > 4) {
				zoomFactor = temp;
			}
		} else {
			// Zoom out
			zoomFactor /= 1.1;
			if (zoomFactor < 1) {
				zoomFactor = temp;
			} else {
				if (viewX < -(coordonneesMax.getX() * (zoomFactor - 1))) {
					viewX = (int) (-(coordonneesMax.getX() * (zoomFactor - 1)));
					// System.out.println("Limite A atteinte");
				} else if (viewX > coordonneesMin.getX() * (zoomFactor - 1)) {
					viewX = (int) (coordonneesMin.getX() * (zoomFactor - 1));

				}

				if (viewY < -coordonneesMin.getY() * (zoomFactor - 1)) {
					viewY = (int) (-coordonneesMin.getY() * (zoomFactor - 1));
					// System.out.println("Limite A atteinte");
				} else if (viewY > coordonneesMin.getX() * (zoomFactor - 1)) {
					viewY = (int) (coordonneesMin.getX() * (zoomFactor - 1));

				}
			}
		}
		repaint();
	}

	public void setDrag(Coordonnees coordonnees, Coordonnees dernieresCoordonnees) {
		if (zoomFactor != 1.0) {
			int newViewX = viewX + coordonnees.getX() - dernieresCoordonnees.getX();
			int newViewY = viewY + coordonnees.getY() - dernieresCoordonnees.getY();
			if (newViewX < -(coordonneesMax.getX() * (zoomFactor - 1))) {
				newViewX = viewX;
				//System.out.println("Limite A atteinte");
			}
			if (newViewX > coordonneesMin.getX() * (zoomFactor - 1)) {
				newViewX = viewX;
				//System.out.println("Limite B atteinte");
			}
			if (newViewY < -coordonneesMin.getY() * (zoomFactor - 1)) {
				newViewY = viewY;
				//System.out.println("Limite C atteinte");
			}
			if (newViewY > coordonneesMax.getY() * (zoomFactor - 1)) {
				newViewY = viewY;
				//System.out.println("Limite D atteinte");
			}
			viewX = newViewX;
			viewY = newViewY;
			repaint();
		}
	}


//------------------------------------------------------------------------------------------------------------------------

	/**
	 * Méthode appelée par les objets observés par GraphicalView à chaque mise à
	 * jour de ces derniers
	 * 
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

		boolean tourneeDejaExistante = false;
		for (VueTournee vt : vuesTournees) {
			if (vt.getTournee().equals((Tournee) o)) {
				tourneeDejaExistante = true;
				vt.setVue((Tournee) o);
			}
		}

		if (!tourneeDejaExistante) {
			nbTournees++;
			vuesTournees.add(new VueTournee((Tournee) o, this, couleursTournees[nbTournees - 1]));
		}

		// aucun requête ne peut plus être sélectionnée fonctionnellement
		for (VueIntersection vi : vuesIntersections) {
			vi.afficherNonSelectionnee();
		}

		repaint();
	}

	/**
	 * Ajoute une carte à la vue
	 * 
	 * @param carte
	 */
	public void ajouterCarte(Graphe carte) {

		this.carte = carte;

		initialiserVues(carte);

		latitudeMax = -90;
		latitudeMin = 90;
		longitudeMax = -180;
		longitudeMin = 180;

		coordonneesMin = new Coordonnees(0, viewHeight);
		coordonneesMax = new Coordonnees(
				(int) ((longitudeMax - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth),
				(int) (viewHeight - ((latitudeMax - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight)));

		definirExtremesCoordonnees();
		repaint();
	}

	

	/**
	 * Retourne l'intersection sélectionnée par le clic souris Met à jour la vue de
	 * cette intersection
	 * 
	 * @param coordonneesSouris
	 * @return
	 */
	public List<Intersection> getIntersectionsSelectionnees(Coordonnees coordonneesSouris) {

		List<Intersection> intersectionsSelectionnees = new ArrayList<>();

		for (VueIntersection vi : vuesIntersections) {
			if (vi.estCliquee(coordonneesSouris)) {
				intersectionsSelectionnees.add(vi.getIntersection());
			}
		}
		if (vueEntrepot.estCliquee(coordonneesSouris)) {
			intersectionsSelectionnees.add(vueEntrepot.getIntersection());
		}

		return intersectionsSelectionnees;
	}

	/**
	 * Affichage différent pour l'intersection sélectionnée
	 * 
	 * @param intersection
	 */
	public void setSelectionnee(Intersection intersection) {
		for (VueIntersection vi : vuesIntersections) {
			if (vi.getIntersection().equals(intersection)) {
				vi.afficherSelectionnee();
			}
		}
		repaint();
	}

	public void clearTournees() {
		this.vuesTournees.clear();
		nbTournees = 0;
		repaint();
	}

	

	/**
	 * Méthode à appeler à chaque fois que la vue graphique doit être redessinée
	 * 
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g = g;
		// translateView();

		if (carte != null) {
			vueEntrepot.dessinerVue();

			for (VueIntersection vi : vuesIntersections) {
				vi.dessinerVue();
			}

			for (VueSegment vs : vuesSegments) {
				vs.dessinerVue();
			}

			for (VueTournee vt : vuesTournees) {
				vt.dessinerVue();
			}
		}
	}

	/**
	 * Méthode traduisant des coordonnées GPS en coordonnées en pixels pour
	 * l'affichage graphique
	 * 
	 * @param i
	 * @return
	 */
	public Coordonnees CoordGPSToViewPos(Intersection i) {
		int xpos = (int) ((i.getLongitude() - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth);
		int ypos = (int) (viewHeight - ((i.getLatitude() - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight));

		// System.out.println("xpos=" + xpos + " / ypos=" + ypos);

		xpos = (int) (xpos * zoomFactor) + viewX;
		ypos = (int) (ypos * zoomFactor) + viewY;

		// System.out.println("xpos=" + xpos + " / ypos=" + ypos);

		return new Coordonnees(xpos, ypos);
	}

//-------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Crée toutes les vues pour les objets de la carte (segments, intersections,
	 * entrepôt...)
	 * 
	 * @param carte
	 */
	private void initialiserVues(Graphe carte) {

		// entrepot
		vueEntrepot = new VueEntrepot(carte.getEntrepot(), this);

		// intersections
		for (Intersection i : carte.getIntersections()) {
			vuesIntersections.add(new VueIntersection(i, this));
		}

		// segments
		for (Segment s : carte.getSegments()) {
			vuesSegments.add(new VueSegment(s, this, Color.BLUE));
		}
	}

	/**
	 * Méthode déterminant les plus grandes coordonnées de la carte choisie Permet
	 * de définir l'échelle de la vue graphique
	 */
	private void definirExtremesCoordonnees() {

		for (Intersection intersection : carte.getIntersections()) {
			double latitude = intersection.getLatitude();
			double longitude = intersection.getLongitude();
			if (latitude > latitudeMax)
				latitudeMax = latitude;
			if (latitude < latitudeMin)
				latitudeMin = latitude;
			if (longitude > longitudeMax)
				longitudeMax = longitude;
			if (longitude < longitudeMin)
				longitudeMin = longitude;
		}

		//System.out.println("latitude min : " + latitudeMin + " / latitude max : " + latitudeMax);
		//System.out.println("longitude min : " + longitudeMin + " / longitude max : " + longitudeMax);
		coordonneesMin = new Coordonnees(0, viewHeight);
		coordonneesMax = new Coordonnees(
				(int) ((longitudeMax - longitudeMin) / (longitudeMax - longitudeMin) * viewWidth),
				(int) (viewHeight - ((latitudeMax - latitudeMin) / (latitudeMax - latitudeMin) * viewHeight)));
	}

	// private void translateView() {
	// Graphics2D g2d = (Graphics2D) g;
	// g2d.translate(viewX, viewY);
	// // Appliquer le facteur de zoom
	// g2d.scale(zoomFactor, zoomFactor);
	// }
	
}
