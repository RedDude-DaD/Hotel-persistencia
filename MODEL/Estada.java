package MODEL;

import java.time.*;
import java.util.*;

/**
 * @author radum
 */
public class Estada {

    private int idEstada;
    private String clientEstada;
    private int habitacioEstadaFloor;
    private int habitacioEstadaNum;
    private ArrayList<String> serveiEstada = new ArrayList();
    private LocalDate dataEntrada;
    private LocalTime horaEntrada;
    private LocalDate dataSortida;
    private LocalTime horaSortida;
    private double importActual;
    private double facturaFinal = 0.00;
    private ArrayList<String> serveisUtilitzats = new ArrayList();
    private int estat = 0;
    private String pais;

    public Estada(int idEstada, String clientEstada, int habitacioEstadaFloor, int habitacioEstadaNum, LocalDate dataEntrada, LocalTime horaEntrada, LocalDate dataSortida, LocalTime horaSortida, double importActual, double facturaFinal, int estat, String pais) {
        this.idEstada = idEstada;
        this.clientEstada = clientEstada;
        this.habitacioEstadaFloor = habitacioEstadaFloor;
        this.habitacioEstadaNum = habitacioEstadaNum;
        this.dataEntrada = dataEntrada;
        this.horaEntrada = horaEntrada;
        this.dataSortida = dataSortida;
        this.horaSortida = horaSortida;
        this.facturaFinal = facturaFinal;
        this.importActual = importActual;
        this.estat = estat;
        this.pais = pais;
    }

    public Estada(int idEstada, LocalDate dataEntrada, LocalTime horaEntrada, LocalDate dataSortida, LocalTime horaSortida, double importActual, double facturaFinal) {
        this.idEstada = idEstada;
        this.dataEntrada = dataEntrada;
        this.horaEntrada = horaEntrada;
        this.dataSortida = dataSortida;
        this.horaSortida = horaSortida;
        this.importActual = importActual;
        this.facturaFinal = facturaFinal;
    }

    public Estada(int idEstada, LocalTime temps, LocalDate data, String pais) {
        this.idEstada = idEstada;
        this.dataEntrada = data;
        this.horaEntrada = temps;
        this.importActual = 0;
        this.pais = pais;
    }

    public String showEstadaServeis() {
        String serveis = "";
        for (int i = 0; i < serveiEstada.size(); i++) {
            if (i == 0) {
                serveis = serveis + serveiEstada.get(i);
            } else {
                serveis = serveis + "," + serveiEstada.get(i);
            }
        }
        return serveis;
    }

    public void addServeisUtilitzats(String nom) {
        serveisUtilitzats.add(nom);
    }

    public String getClientEstada() {
        return clientEstada;
    }

    public void setClientEstada(String clientEstada) {
        this.clientEstada = clientEstada;
    }

    public int getHabitacioEstadaFloor() {
        return habitacioEstadaFloor;
    }

    public void setHabitacioEstadaFloor(int habitacioEstadaFloor) {
        this.habitacioEstadaFloor = habitacioEstadaFloor;
    }

    public int getHabitacioEstadaNum() {
        return habitacioEstadaNum;
    }

    public void setHabitacioEstadaNum(int habitacioEstadaNum) {
        this.habitacioEstadaNum = habitacioEstadaNum;
    }

    public ArrayList<String> getServeisUtilitzats() {
        return serveisUtilitzats;
    }

    public void addServeiEstada(String nom) {
        serveiEstada.add(nom);
    }

    public void setServeisUtilitzats(ArrayList<String> serveisUtilitzats) {
        this.serveisUtilitzats = serveisUtilitzats;
    }

    public ArrayList<String> getServeiEstada() {
        return serveiEstada;
    }

    public void setServeiEstada(ArrayList<String> serveiEstada) {
        this.serveiEstada = serveiEstada;
    }

    public int getIdEstada() {
        return idEstada;
    }

    public int getEstat() {
        return estat;
    }

    public void setEstat(int estat) {
        this.estat = estat;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDate getDataSortida() {
        return dataSortida;
    }

    public LocalTime getHoraSortida() {
        return horaSortida;
    }

    public double getImportActual() {
        return importActual;
    }

    public double getFacturaFinal() {
        return facturaFinal;
    }

    public void setIdEstada(int idEstada) {
        this.idEstada = idEstada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setDataSortida(LocalDate dataSortida) {
        this.dataSortida = dataSortida;
    }

    public void setHoraSortida(LocalTime horaSortida) {
        this.horaSortida = horaSortida;
    }

    public void setImportActual(double importActual) {
        this.importActual = importActual;
    }

    public void setFacturaFinal(double facturaFinal) {
        this.facturaFinal = facturaFinal;
    }

    public String showDetails() {
        Calendar c = Calendar.getInstance();
        Date date = Date.from(dataEntrada.atStartOfDay(ZoneId.systemDefault()).toInstant());
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String day = "";
        switch (dayOfWeek) {
            case 1:
                day = "DIUMENGE";
                break;
            case 2:
                day = "DILLUNS";
                break;
            case 3:
                day = "DIMARTS";
                break;
            case 4:
                day = "DIMECRES";
                break;
            case 5:
                day = "DIJOUS";
                break;
            case 6:
                day = "DIVENDRES";
                break;
            case 7:
                day = "DISSAPTE";
                break;

        }
        DiesSetmana diaS = DiesSetmana.valueOf(day);
        IVA IVApais = IVA.valueOf(pais.toUpperCase());

        return "data entrada: " + dataEntrada + " : " + diaS.toString() + ", hora entrada: " + horaEntrada + "\n"
                + "import actual: " + importActual + ", " + "estat: " + EstatEstada.show(estat) + ", pais: " + pais + " iva: " + Math.round((IVApais.showIVA() - 1) * 100) + "%";
    }
}
