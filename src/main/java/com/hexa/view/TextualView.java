package com.hexa.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.hexa.model.*;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;
import com.hexa.view.object.VueTexteLivraison;
import com.hexa.view.object.VueTexteTournee;


public class TextualView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
    private ArrayList<VueTexteTournee> vuesTournees = new ArrayList<VueTexteTournee>();

	private int viewWidth;
	private Graphe carte;
	private Font font;
	private Window window;
	private VueTexteLivraison currentlyHighlightedLivraison;

//---------------------------------------------------------------------------------------------------------

	/**
	 * Crée la vue textuelle des livraisons
	 * @param window la fenêtre
	 * @param textualViewWidth
	 */
	public TextualView(Window window, int textualViewWidth, Font windowFont){
		super();

		this.window = window;
		this.font = windowFont;
		this.viewWidth = textualViewWidth;
		currentlyHighlightedLivraison = null;

		setBackground(Color.WHITE);
		setFont(font);
		TitledBorder border = BorderFactory.createTitledBorder("Liste des livraisons :");
		border.setTitleFont(font);
		setBorder(border);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		window.getContentPane().add(this);
	}
	

//------------------------------------------------------------------------------------------------------------------

	public int getViewWidth() {
		return viewWidth;
	}


//------------------------------------------------------------------------------------------------------------------


	public void ajouterCarte (Graphe graphe) {
		carte=graphe;
	}

//-----------------------------------------------------------------------------------------------------------------


	/**
	 * Appelé à chaque modification des objets observés
	 */
	@Override
	public void update(Observable o, Object arg) {

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
			vueTexteTourneeAUpdate = new VueTexteTournee((Tournee)o, this, font);
			vuesTournees.add(vueTexteTourneeAUpdate);
		}
		genererVue(vueTexteTourneeAUpdate, false);

		window.ajouterMouseListenersTextualView(this.vuesTournees);
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
				this.add(vueTournee.dessinerVue(this.carte, true));
			} else {
				this.add(vueTournee.dessinerVue(this.carte, false));
			}
		}
		revalidate();
		repaint();

	}

	public void clearTournees() {
		ArrayList<VueTexteTournee> TempvuesTournees = new ArrayList<VueTexteTournee>();
		TempvuesTournees.addAll(vuesTournees);
		for (VueTexteTournee vueTournee : TempvuesTournees)
			this.genererVue(vueTournee,true);

	}

	public void highlightLivraison(Tournee t, Livraison l) {
			for (VueTexteTournee vtt : this.vuesTournees) {
				if (vtt.getVuesLivraisons().contains(currentlyHighlightedLivraison)) {
					vtt.highlightLivraison(currentlyHighlightedLivraison.getLivraison(), false);
					this.currentlyHighlightedLivraison = null;
				}
			}
		if (t != null) {
			for (VueTexteTournee vtt : this.vuesTournees) {
				if (vtt.getTournee().getLivreur().equals(t.getLivreur())) {
						vtt.highlightLivraison(l, true);
						currentlyHighlightedLivraison = vtt.getVueLivraisonCorrespondante(l);
					break;
				}
			}
		}

	}
}
