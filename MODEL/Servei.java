package MODEL;

import static CONTROLLER.FileModify.*;

/**
 * @author radum
 */
public class Servei {

    private String nom;
    private double preu;

    public Servei(String nom, double preu) {
        this.nom = nom;
        this.preu = preu;
        if (!doesServiceExist(nom)) {
            addLine("src/PERSISTENCIA/serveis.txt", nom + "|" + preu);
        }
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public String showData() {
        return "nom: " + nom + ", preu: " + preu + " euros. ";
    }

}
