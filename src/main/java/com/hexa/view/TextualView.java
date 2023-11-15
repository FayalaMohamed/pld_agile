package com.hexa.view;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

/**
 * Méthode permettant l'affichage textuel de la liste des livraisons
 */
public class TextualView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private String text;
    private Tournee tournee;
	private JScrollPane scrollPane;
	private JLabel texteTournees;

	private int viewWidth;
	private int viewHeight;

	/**
	 * Crée la vue textuelle des livraisons
	 * @param tournee la tournée
	 * @param window la fenêtre
	 */
	public TextualView(Window window, Tournee tournee){
		super();

		this.viewHeight = window.getHeight();
		this.viewWidth = 400;

		this.texteTournees = new JLabel();
		Dimension dimensions = texteTournees.getSize();
		dimensions.width = 200;
		texteTournees.setSize(dimensions);

		setBorder(BorderFactory.createTitledBorder("Liste des livraisons:"));
		this.texteTournees.setVerticalTextPosition(JLabel.TOP);
		this.texteTournees.setVerticalAlignment(JLabel.TOP);
		this.texteTournees.setFont(UIManager.getFont("large.font"));
		this.texteTournees.setText("test addichage");

		this.scrollPane = new JScrollPane(texteTournees);
		add(scrollPane);

		window.getContentPane().add(this);

		tournee.addObserver(this); // this observes tournee
		this.tournee = tournee;
	}
	
	/**
	 * Appelé à chaque modification des objets observés
	 */
	@Override
	public void update(Observable o, Object arg) {

		//on génère le texte correspondant aux livraisons chargées
		// text = "<html><ul>";
		// for (Livraison l : tournee.getLivraisons())
		// 	display(l);
		// text = text+"</ul></html>";

		// texteTournees.setText(text);
		// removeAll();
		// createScrollPane(texteTournees);
		// add(scrollPane);
		// revalidate();
		// repaint();
	}

	/**
	 * Affichage d'une livraison sous forme textuelle
     * @param l
     */
	public void display(Livraison l) {
		text = text+"<li>";
		text = text+"Livraison: " + l.toString();
		if (tournee.estCalculee()) {
			text = text + " Plage horaire : " + l.getPlageHoraire()[0] + "h - " + l.getPlageHoraire()[1] + "h";
		}
		text = text+"</li>";
	}

	private void createScrollPane(JLabel zoneTexte) {

		scrollPane = new JScrollPane(zoneTexte,
										ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
   										ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setWheelScrollingEnabled(true);
	}

	public int getViewWidth() {
		return viewWidth;
	}
}
