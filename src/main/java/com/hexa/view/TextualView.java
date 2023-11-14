package com.hexa.view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.observer.Observable;
import com.hexa.observer.Observer;

/**
 * Méthode permettant l'affichage textuel de la liste des livraisons
 */
public class TextualView extends JLabel implements Observer {

  // -------------------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private String text;
  private Window window;

  // -------------------------------------------------------------------------------------------------

  /**
   * Crée la vue textuelle des livraisons
   * 
   * @param tournee la tournée
   * @param window  la fenêtre
   */
  public TextualView(Window window) {
    super();
    setBorder(BorderFactory.createTitledBorder("Liste des livraisons:"));
    this.setVerticalTextPosition(TOP);
    this.setVerticalAlignment(TOP);
    this.setFont(UIManager.getFont("large.font"));
    window.getContentPane().add(this);
    this.window = window;
    for (Tournee tournee : window.getController().getTournees()) {
      tournee.addObserver(this); // this observes tournee
    }
  }

  // -------------------------------------------------------------------------------------------------

  /**
   * Appelé à chaque modification des objets observés
   */
  @Override
  public void update(Observable o, Object arg) {
    text = "<html><ul>";
    for (Tournee tournee : window.getController().getTournees()) {
      for (Livraison l : tournee.getLivraisons())
        display(l);
    }
    text = text + "</ul></html>";
    setText(text);
  }

  /**
   * Affichage d'une livraison sous forme textuelle
   * 
   * @param l
   */
  public void display(Livraison l) {
    text = text + "<li>";
    text = text + "Livraison: " + l.toString();
    for (Tournee tournee : window.getController().getTournees()) {
      if (tournee.estLieuLivraison(l.getLieu()) && tournee.estCalculee()) {
        text = text + " Plage horaire : " + l.getPlageHoraire()[0] + "h - " + l.getPlageHoraire()[1] + "h";
      }
    }
    text = text + "</li>";
  }
}
