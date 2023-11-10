package com.hexa.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.hexa.controller.Controller;
import com.hexa.model.GrapheException;
import com.hexa.model.TourneeException;

public class BoxListener implements ActionListener {

  private Controller controller;

  public BoxListener(Controller controller) {
    this.controller = controller;
  }

  /**
   * Méthode appelée par le BoxListener chaque fois qu'un choix est fait dans la JComboBox
   * Prend la valeur choisie
   * Puis délègue au contrôleur
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> box = (JComboBox<String>) e.getSource();
    System.out.println("COMBO BOX " + box.getSelectedItem());
    try {
      controller.choixLivreur(Integer.valueOf((String) box.getSelectedItem()));
    } catch (NumberFormatException | GrapheException | TourneeException e1) {
      e1.printStackTrace();
    }
  }
}
