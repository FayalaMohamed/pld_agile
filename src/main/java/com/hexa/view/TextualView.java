package com.hexa.view;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

public class TextualView extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private String text;
    private Tournee tournee;

	/**
	 * Crée la vue textuelle des livraisons
	 * @param tournee la tournée
	 * @param window la fenêtre
	 */
	public TextualView(Window window, Tournee tournee){
		super();
		setBorder(BorderFactory.createTitledBorder("Liste des livraisons:"));
		this.setVerticalTextPosition(TOP);
		this.setVerticalAlignment(TOP);
		window.getContentPane().add(this);
		tournee.addObserver(this); // this observes plan
		this.tournee = tournee;
	}
	
	/**
	 * Appelé à chaque modification des objets observés
	 */
	@Override
	public void update(Observable o, Object arg) {
		Iterator<Livraison> it = tournee.getLivraisonIterator();
		text = "<html><ul>";
		while (it.hasNext())
			display(it.next());
		text = text+"</ul></html>";
		setText(text);
	}

	/**
	 * Affichage d'une livraison sous forme textuelle
	 */
	public void display(Livraison l) {
		text = text+"<li>";
		text = text+"Livraison: adresse=(" + l.getLieu().getId();
		text = text+"</li>";
	}
}
