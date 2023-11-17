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


/**
 * Classe gérant la vue textuelle des livraisons.
 */
public class TextualView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
    private ArrayList<VueTexteTournee> vuesTournees = new ArrayList<VueTexteTournee>();

	private int viewWidth;
	private Graphe carte;
	private Font font;
	private Font labelFont;
	private Window window;
	private VueTexteLivraison currentlyHighlightedLivraison;
	private ScrollablePanel innerPanel;
	private JScrollPane innerScrollPane;

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
		this.labelFont = new Font(font.getName(), Font.PLAIN,(int)(font.getSize()*(0.80)));
		this.viewWidth = textualViewWidth;
		currentlyHighlightedLivraison = null;

		setBackground(Color.WHITE);

		TitledBorder border = BorderFactory.createTitledBorder("Liste des livraisons :");
		border.setTitleFont(font);
		setBorder(border);
		setLayout(null);
		innerPanel = new ScrollablePanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
		innerScrollPane = new JScrollPane(innerPanel,    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		innerPanel.setBackground(Color.WHITE);
		innerScrollPane.getViewport().setBackground(Color.WHITE);
		innerScrollPane.setBorder(null);
		add(innerScrollPane);
		window.getContentPane().add(this);
	}

	/**
	 * Donne la taille de la textualView, et adapte chacun des composants qu'elle contient en fonction.
	 * @param width la nouvelle largeur de ce composant en pixels
	 * @param height la nouvelle hauteur de ce composant en pixels
	 */
	@Override
	public void setSize(int width, int height) {
		super.setSize(width,height);
		Insets insets = this.getBorder().getBorderInsets(this);
		innerPanel.setSize(this.getWidth()-insets.left-insets.right,this.getHeight() - insets.top-insets.bottom);
		innerPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
		innerScrollPane.setBounds(insets.left,insets.top,this.getWidth()-insets.left-insets.right,this.getHeight() - insets.top-insets.bottom);
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
			vueTexteTourneeAUpdate = new VueTexteTournee((Tournee)o, this, labelFont);
			vuesTournees.add(vueTexteTourneeAUpdate);
		}
		genererVue(vueTexteTourneeAUpdate, false);

		window.ajouterMouseListenersTextualView(this.vuesTournees);
	}

	/**
	 * Regénère toutes les vues des VueTexteTournees. Si supprimeTournee est à true, supprime la tournée passée
	 * en paramètre. Si supprimeTournee est à false, ajoute la tournée à la liste.
	 * @param vueTexteTourneeAUpdate
	 * @param supprimeTournee
	 */
	public void genererVue(VueTexteTournee vueTexteTourneeAUpdate, boolean supprimeTournee) {
		if (supprimeTournee) {
			this.remove(vueTexteTourneeAUpdate);
			this.vuesTournees.remove(vueTexteTourneeAUpdate);
			revalidate();
			repaint();
			innerPanel.revalidate();
			innerPanel.repaint();
		}

		for (VueTexteTournee vueTournee : vuesTournees) {
			if (vueTournee == vueTexteTourneeAUpdate && !supprimeTournee) {
				innerPanel.add(vueTournee.dessinerVue(this.carte, true));
			} else {
				innerPanel.add(vueTournee.dessinerVue(this.carte, false));
			}
		}
		revalidate();
		repaint();
		innerPanel.revalidate();
		innerPanel.repaint();

	}

	/**
	 * Retire chacune des VueTexteTournee de la TextualView.
	 */
	public void clearTournees() {
		ArrayList<VueTexteTournee> TempvuesTournees = new ArrayList<VueTexteTournee>();
		TempvuesTournees.addAll(vuesTournees);
		for (VueTexteTournee vueTournee : TempvuesTournees)
			this.genererVue(vueTournee,true);

	}

	/**
	 * Highlight la livraison l de la tournée t dans la textualView. Si la tournée est null, enlève le highlight
	 * de toutes les livraisons de la textualView.
	 * @param t
	 * @param l
	 */
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
