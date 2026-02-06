package MODEL;

import static CONTROLLER.FileModify.*;

/**
 * @author radum
 */
public class Habitacio {

    private int planta;
    private int numero;
    private double preuNit;
    private boolean ocupada = false;

    public Habitacio(int planta, int numero, double preuNit) {
        this.planta = planta;
        this.numero = numero;
        this.preuNit = preuNit;
        if (!doesRoomExist(planta, numero)) {
            addLine("src/PERSISTENCIA/habitacions.txt", planta + "|" + numero + "|" + preuNit + "|" + ocupada);
        }
    }

    public Habitacio(int planta, int numero, double preuNit , boolean ocupada) {
        this.planta = planta;
        this.numero = numero;
        this.preuNit = preuNit;
        this.ocupada = ocupada;
        if (!doesRoomExist(planta, numero)) {
            addLine("src/PERSISTENCIA/habitacions.txt", planta + "|" + numero + "|" + preuNit + "|" + ocupada);
        }
    }

    public int getPlanta() {
        return planta;
    }

    public int getNumero() {
        return numero;
    }

    public double getPreuNit() {
        return preuNit;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPreuNit(double preuNit) {
        this.preuNit = preuNit;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String showData() {
        return "planta: " + planta + ", numero: " + numero + ", preu nit " + preuNit + ", ocupada: " + ocupada + ". ";
    }

}
