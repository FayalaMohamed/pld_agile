package com.hexa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;
import org.checkerframework.checker.units.qual.A;

/**
 * Méthode permettant l'affichage textuel de la liste des livraisons
 */
public class TextualView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private String text;
    private ArrayList<VueTexteTournee> vuesTournees = new ArrayList<VueTexteTournee>();

	private int viewWidth;
	private int viewHeight;

	/**
	 * Crée la vue textuelle des livraisons
	 * @param window la fenêtre
	 * @param textualViewWidth
	 */
	public TextualView(Window window, int textualViewWidth){
		super();

		this.viewWidth = textualViewWidth;
		this.viewHeight = window.getHeight();

		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder("Liste des livraisons :"));

		window.getContentPane().add(this);
	}
	
	/**
	 * Appelé à chaque modification des objets observés
	 */
	@Override
	public void update(Observable o, Object arg) {

		System.out.println("\nupdate textual view\n");
		VueTexteTournee vueTexteTourneeAUpdate = null;

		boolean tourneeDejaExistante = false;
		for (VueTexteTournee vtt : vuesTournees) {
			if (vtt.getTournee().equals((Tournee)o)) {
				tourneeDejaExistante = true;
				//System.out.println("textual view : tournée déjà existante");
				vtt.setTournee((Tournee)o);
				vueTexteTourneeAUpdate = vtt;
				break;
			}
		}
		boolean tourneeASupprimer = ((Tournee)o).getNbLivraisons() == 0;
		if (tourneeASupprimer) {
			genererVue(vueTexteTourneeAUpdate,true);
		}

		if (!tourneeDejaExistante) {
			//System.out.println("textual view : nouvelle tournée");
			vueTexteTourneeAUpdate = new VueTexteTournee((Tournee)o, this);
			vuesTournees.add(vueTexteTourneeAUpdate);
		}
		genererVue(vueTexteTourneeAUpdate, false);
	}

	public void genererVue(VueTexteTournee vueTexteTourneeAUpdate, boolean supprimeTournee) {
		if (supprimeTournee) {
			this.remove(vueTexteTourneeAUpdate);
			this.vuesTournees.remove(vueTexteTourneeAUpdate);
			revalidate();
			repaint();
		}

		//System.out.println("textual view : générer vue / nb de tournées : " + vuesTournees.size());
		for (VueTexteTournee vueTournee : vuesTournees) {
			//System.out.println("largeur vue : "+vueTournee.getWidth()+" / hauteur vue : " + vueTournee.getHeight());
			if (vueTournee == vueTexteTourneeAUpdate && !supprimeTournee) {
				this.add(vueTournee.dessinerVue(true));
			} else {
				this.add(vueTournee.dessinerVue(false));
			}
		}

	}

	/**
	 * Affichage d'une livraison sous forme textuelle
     * @param l
     */
	public void display(Livraison l) {
		// text = text+"<li>";
		// text = text+"Livraison: " + l.toString();
		// if (tournee.estCalculee()) {
		// 	text = text + " Plage horaire : " + l.getPlageHoraire()[0] + "h - " + l.getPlageHoraire()[1] + "h";
		// }
		// text = text+"</li>";
	}

	public int getViewWidth() {
		return viewWidth;
	}
}
