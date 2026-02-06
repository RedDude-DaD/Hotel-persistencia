package MODEL;

import static CONTROLLER.FileModify.*;
import java.time.LocalDate;

/**
 * @author radum
 */
public class Client {

    private String nom;
    private String nif;
    private LocalDate dataNeix;
    private boolean Estada = false;

    public Client(String nom, String nif, LocalDate dataNeix, boolean estada) {
        this.nom = nom;
        this.nif = nif;
        this.dataNeix = dataNeix;
        this.Estada = estada;
        if (!doesClientExist(nif)) {
            addLine("src/PERSISTENCIA/clients.txt", nom + "|" + nif + "|" + dataNeix + "|" + Estada);
        }
    }

    public String getNom() {
        return nom;
    }

    public String getNif() {
        return nif;
    }

    public LocalDate getDataNeix() {
        return dataNeix;
    }

    public boolean getEstada() {
        return Estada;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEstada(boolean Estada) {
        this.Estada = Estada;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setDataNeix(LocalDate dataNeix) {
        this.dataNeix = dataNeix;
    }

    public String showData() {
        return "nom: " + nom + ", nif: " + nif + ", data neixement: " + dataNeix + ". ";
    }

}
