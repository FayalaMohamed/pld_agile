package com.hexa.view.object;

import javax.swing.JLabel;

import com.hexa.model.Graphe;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Segment;
import com.hexa.view.TextualView;

import java.awt.Font;

/**
 * Objet encapsulant l'objet livraison et permettant son affichage dans la vue textuelle.
 */
public class VueTexteLivraison extends JLabel {
    
    private static final long serialVersionUID = 1L;
	private Livraison livraison;
    boolean highlighted;

    /**
     * Crée un objet VueTexteLivraison
     * @param tv
     * @param livraison
     * @param carte
     * @param font
     */
    VueTexteLivraison(TextualView tv, Livraison livraison, Graphe carte, Font font) {

        this.livraison = livraison;
        this.highlighted = false;

        boolean affPlageHoraire = livraison.getPlageHoraire()[0] == 0 ? false : true;

        setFont(font);
        dessinerVue(carte, affPlageHoraire);
    }

    /**
     * Remplit le JPanel associé à cette VueTexteLivraison
     * @param carte
     * @param affPlageHoraire
     */
    private void dessinerVue(Graphe carte, boolean affPlageHoraire) {
        String desc = "<html><ul><li>";
        desc += buildDescription(carte, desc, livraison.getLieu());
        if (affPlageHoraire){
            desc +="<br/> Plage horaire : " + livraison.getPlageHoraire()[0] + "h - "+livraison.getPlageHoraire()[1]+"h.";
        }
        desc += "</li></ul></html>";

        this.setText(desc);
    }

    private String buildDescription(Graphe carte, String desc, Intersection intersection) {

        Intersection[] successeurs = carte.getSuccesseur(intersection);
        Intersection[] predecesseurs = carte.getPredecesseur(intersection);

        desc = "";
        boolean first = true;
        for (Intersection succ : successeurs) {

            desc = addToDesc(desc, intersection, succ, carte, first);
            first = false;

        }

        for (Intersection pred : predecesseurs) {

            desc = addToDesc(desc, pred, intersection, carte, first);
            first = false;

        }

        desc += ".";
        return desc;
    }

    private String addToDesc(String desc, Intersection origine, Intersection destination, Graphe carte, boolean first) {
        String nom = carte.getNomSegment(new Segment(origine, destination));

        if (!desc.contains(nom) || nom.isEmpty()) {
            if (!first) {
                desc += ", ";
            }
            desc += (nom.isEmpty() ? "Rue sans nom" : nom);
        }
        return desc;
    }

    /**
     * highlight si highlight==true, enlève le highlight sinon.
     * @param highlight
     */
    public void highlight(boolean highlight) {
        if (highlighted && highlight) {
            return;
        } else if (highlighted && !highlight) {
            String desc = "<html><ul><li>";
            desc+=this.getText().substring(37,this.getText().length()-24);
            desc+="</html></li></ul>";
            this.setText(desc);
            System.out.println(this.getText());
            this.highlighted = false;
        } else {
            String desc = "<html><ul><li><span bgcolor=\"yellow\">";
            desc+=this.getText().substring(14,this.getText().length()-17);
            desc+="</span></html></li></ul>";
            this.setText(desc);
            this.highlighted = true;
        }

    }

    public Livraison getLivraison() {
        return livraison;
    }
}
