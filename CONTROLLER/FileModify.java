package CONTROLLER;

import static CONTROLLER.Hotel.*;
import MODEL.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author radum
 */
public class FileModify {

    public static void createFile(String nom) {
        try {
            File fitxer = new File(nom);
            if (fitxer.createNewFile()) {
                //  System.out.println("S'ha creat el fitxer : " + fitxer.getName());
            } else {
                // System.out.println("El fitxer ja existeix.");
            }
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }

    }

    public static void addLine(String nom, String text) {
        try {
            FileWriter fitxer = new FileWriter(nom, true);
            fitxer.write(text + "\n");
            fitxer.close();
            // log
            addLogLine(nom, "added line " + text);
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }

    }

    public static void ReadFile(String nom) {
        try {
            File fitxer = new File(nom);
            Scanner sc = new Scanner(fitxer);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                System.out.println(data);
            }
            sc.close();
            // log
            addLogLine(nom, "read file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void createUsers() {
        String regex = "[|]";
        try {
            File fitxerUser = new File("src/PERSISTENCIA/usuaris.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                usuaris.add(new Usuari(user[0], user[1], user[2]));
            }
            sc.close();
            // log
            addLogLine("src/MODEL/Usuari.java", "Created users instances");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void createClients() {
        String regex = "[|]";
        try {
            File fitxerClient = new File("src/PERSISTENCIA/clients.txt");
            Scanner sc = new Scanner(fitxerClient);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                LocalDate date = LocalDate.parse(user[2]);
                boolean estada = false;
                if (user[3].equals("false")) {
                    estada = false;
                } else {
                    estada = true;
                }

                clients.add(new Client(user[0], user[1], date, estada));
            }
            sc.close();
            // log
            addLogLine("src/MODEL/Client.java", "Created client instances");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void createRoom() {
        String regex = "[|]";
        try {
            File fitxerUser = new File("src/PERSISTENCIA/habitacions.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] room = data.split(regex);
                int roomFloor = Integer.parseInt(room[0]);
                int roomNum = Integer.parseInt(room[1]);
                double roomPrice = Double.parseDouble(room[2]);
                String roomOcupiedString = room[3];
                boolean roomOcupied = false;
                if (roomOcupiedString.equals("false")) {
                    roomOcupied = false;
                } else {
                    roomOcupied = true;
                }
                habitacions.add(new Habitacio(roomFloor, roomNum, roomPrice, roomOcupied));
            }
            sc.close();
            // log
            addLogLine("src/MODEL/Habitacion.java", "Created room instances");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void createService() {
        String regex = "[|]";
        try {
            File fitxerUser = new File("src/PERSISTENCIA/serveis.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] service = data.split(regex);
                double servicePrice = Double.parseDouble(service[1]);
                serveis.add(new Servei(service[0], servicePrice));
            }
            sc.close();
            // log
            addLogLine("src/MODEL/Servei.java", "Created services instances");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void createEstada() {
        String regex = "[|]";
        try {
            File fitxerClient = new File("src/PERSISTENCIA/estades.txt");
            Scanner sc = new Scanner(fitxerClient);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] estada = data.split(regex);

                int idEstada = Integer.parseInt(estada[0]);
                String EstadaClient = estada[1];
                int EstadaPlanta = Integer.parseInt(estada[2]);
                int EstadaNumero = Integer.parseInt(estada[3]);
                LocalDate EstadaDataEntrada = LocalDate.parse(estada[5]);
                LocalTime EstadaHoraEntrada = LocalTime.parse(estada[6]);
                LocalDate EstadaDataSortida;
                if (estada[7].equals("null")) {
                    EstadaDataSortida = null;
                } else {
                    EstadaDataSortida = LocalDate.parse(estada[7]);
                }
                LocalTime EstadaHoraSortida;
                if (estada[8].equals("null")) {
                    EstadaHoraSortida = null;
                } else {
                    EstadaHoraSortida = LocalTime.parse(estada[8]);
                }
                double EstadaImportServeis = Double.parseDouble(estada[9]);
                double EstadaImportFinal = Double.parseDouble(estada[10]);
                int EstadaEstat = Integer.parseInt(estada[12]);
                String EstadaPais = estada[13];

                estades.add(new Estada(idEstada, EstadaClient, EstadaPlanta, EstadaNumero, EstadaDataEntrada, EstadaHoraEntrada, EstadaDataSortida, EstadaHoraSortida, EstadaImportServeis, EstadaImportFinal, EstadaEstat, EstadaPais));

                // serveis de estada 
                String regexService = "[,]";
                String[] serveiEstada = estada[4].split(regexService);
                for (int i = 0; i < serveiEstada.length; i++) {
                    estades.getLast().addServeiEstada(serveiEstada[i]);
                }
                // serveis utilitzats de estada
                String[] serveiEstadaUtilitzats = estada[11].split(regexService);
                for (int i = 0; i < serveiEstadaUtilitzats.length; i++) {
                    estades.getLast().addServeisUtilitzats(serveiEstadaUtilitzats[i]);
                }
            }
            sc.close();
            // log
            addLogLine("src/MODEL/Estada.java", "Created estada instances");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static boolean doesUserNameExist(String nom) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/usuaris.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nom.equals(user[0])) {
                    nameExists = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/usuaris.txt", "checked if " + nom + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return nameExists;
    }

    public static boolean doesClientExist(String nif) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/clients.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nif.equals(user[1])) {
                    nameExists = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/clients.txt", "checked if " + nif + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return nameExists;
    }

    public static boolean doesClientExistByName(String nom) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/clients.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nom.equalsIgnoreCase(user[0])) {
                    nameExists = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/clients.txt", "checked if " + nom + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return nameExists;
    }

    public static String returnDNI(String nom) {
        String regex = "[|]";
        String DNI = "";
        try {
            File fitxerUser = new File("src/PERSISTENCIA/clients.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nom.equalsIgnoreCase(user[0])) {
                    DNI = user[1];
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/clients.txt", "checked if " + nom + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return DNI;
    }

    public static boolean doesRoomExist(int floor, int num) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/habitacions.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                int roomFloor = Integer.parseInt(user[0]);
                int roomNum = Integer.parseInt(user[1]);
                if (roomFloor == floor && roomNum == num) {
                    nameExists = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/habitacions.txt", "checked if " + floor + " " + num + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return nameExists;
    }

    public static boolean doesServiceExist(String nom) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/serveis.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nom.equals(user[0])) {
                    nameExists = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/serveis.txt", "checked if " + nom + " exists in file");
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return nameExists;
    }

    public static void showUserByName(String nom) {
        String regex = "[|]";
        boolean nameExists = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/usuaris.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (nom.equals(user[0])) {
                    System.out.println("nom: " + user[0] + ", passwd: " + user[1] + ", role: " + user[2]);
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/usuaris.txt", "showed the user " + nom);
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }

    }

    public static boolean loginUser(String username, String password) {
        String regex = "[|]";
        boolean loginCorrect = false;
        try {
            File fitxerUser = new File("src/PERSISTENCIA/usuaris.txt");
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (username.equals(user[0]) && password.equals(user[1])) {
                    loginCorrect = true;
                }
            }
            sc.close();
            // log
            addLogLine("src/PERSISTENCIA/usuaris.txt", "tried to login with name " + username + " was able to " + loginCorrect);
            //return loginCorrect; 
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return loginCorrect;
    }

    public static String findUserRole(String username, String password) {
        String regex = "[|]";
        String role = "recepcio";
        try {
            File fitxer = new File("src/PERSISTENCIA/usuaris.txt");
            Scanner sc = new Scanner(fitxer);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (username.equals(user[0]) && password.equals(user[1])) {
                    role = user[2];
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return role;
    }

    public static String getNewLine(String file, String name, int idPos, int position, String newData) {
        String regex = "[|]";
        String text = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String OldPassword = "";
                String OldRole = "";
                String OldName = "";
                String OldEstada = "";
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (name.equals(user[idPos])) {
                    // save values of line other than position because there goes the new data
                    if (position == 0) {
                        OldName = newData;
                        OldPassword = user[1];
                        OldRole = user[2];
                        OldEstada = user[3];
                    }
                    if (position == 1) {
                        OldName = user[0];
                        OldPassword = newData;
                        OldRole = user[2];
                        OldEstada = user[3];
                    }
                    if (position == 2) {
                        OldName = user[0];
                        OldPassword = user[1];
                        OldRole = newData;
                        OldEstada = user[3];
                    }
                    if (position == 3) {
                        OldName = user[0];
                        OldPassword = user[1];
                        OldRole = user[2];
                        OldEstada = newData;
                    }
                    // call add line with information from old line
                    text = OldName + "|" + OldPassword + "|" + OldRole + "|" + OldEstada;
                }
            }
            sc.close();
            return text;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static String getOldLine(String file, String name, int idPos, int length) {
        String regex = "[|]";
        String oldFileLine = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (name.equals(user[idPos])) {
                    if (length == 4) {
                        oldFileLine = user[0] + "|" + user[1] + "|" + user[2] + "|" + user[3];
                    }
                    if (length == 3) {
                        oldFileLine = user[0] + "|" + user[1] + "|" + user[2];
                    }
                    if (length == 2) {
                        oldFileLine = user[0] + "|" + user[1];
                    }
                }
            }
            sc.close();
            return oldFileLine;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return oldFileLine;
    }

    public static String getOldLineRoom(String file, int floor, int num) {
        String regex = "[|]";
        String oldFileLine = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] room = data.split(regex);
                int roomFloor = Integer.parseInt(room[0]);
                int roomNum = Integer.parseInt(room[1]);
                if (floor == roomFloor && num == roomNum) {
                    oldFileLine = room[0] + "|" + room[1] + "|" + room[2] + "|" + room[3];
                }
            }
            sc.close();
            return oldFileLine;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return oldFileLine;
    }

    public static String getOldLineStay(String file, String name, int idPos) {
        String regex = "[|]";
        String oldFileLine = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] user = data.split(regex);
                if (name.equals(user[idPos])) {
                    oldFileLine = user[0] + "|" + user[1] + "|" + user[2] + "|" + user[3] + "|" + user[4] + "|" + user[5] + "|" + user[6]
                            + "|" + user[7] + "|" + user[8] + "|" + user[9] + "|" + user[10] + "|" + user[11] + "|" + user[12] + "|" + user[13];
                }
            }
            sc.close();
            return oldFileLine;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return oldFileLine;
    }

    public static String getNewLineRoom(String file, int floor, int num, int position, String newData) {
        String regex = "[|]";
        String text = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String OldFloor = "";
                String OldNum = "";
                String OldPrice = "";
                String Oldused = "";
                String data = sc.nextLine();
                String[] room = data.split(regex);
                int floorRoom = Integer.parseInt(room[0]);
                int numRoom = Integer.parseInt(room[1]);
                if (floor == floorRoom && num == numRoom) {
                    // save values of line other than position because there goes the new data
                    if (position == 0) {
                        OldFloor = newData;
                        OldNum = room[1];
                        OldPrice = room[2];
                        Oldused = room[3];
                    }
                    if (position == 1) {
                        OldFloor = room[0];
                        OldNum = newData;
                        OldPrice = room[2];
                        Oldused = room[3];
                    }
                    if (position == 2) {
                        OldFloor = room[0];
                        OldNum = room[1];
                        OldPrice = newData;
                        Oldused = room[3];
                    }
                    if (position == 3) {
                        OldFloor = room[0];
                        OldNum = room[1];
                        OldPrice = room[2];
                        Oldused = newData;
                    }

                    // call add line with information from old line
                    text = OldFloor + "|" + OldNum + "|" + OldPrice + "|" + Oldused;
                }
            }
            sc.close();
            return text;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static String getNewLineService(String file, String name, int position, String newData) {
        String regex = "[|]";
        String text = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String OldName = "";
                String OldPrice = "";
                String data = sc.nextLine();
                String[] service = data.split(regex);
                if (name.equals(service[0])) {
                    // save values of line other than position because there goes the new data
                    if (position == 0) {
                        OldName = newData;
                        OldPrice = service[1];
                    }
                    if (position == 1) {
                        OldName = service[0];
                        OldPrice = newData;
                    }

                    // call add line with information from old line
                    text = OldName + "|" + OldPrice;
                }
            }
            sc.close();
            return text;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static String getNewLineStay(String file, String name, int idPos, int position, String newData) {
        String regex = "[|]";
        String text = "";
        try {
            File fitxerUser = new File(file);
            Scanner sc = new Scanner(fitxerUser);
            while (sc.hasNextLine()) {
                String idEstada = "";
                String EstadaClient = "";
                String EstadaPlanta = "";
                String EstadaNumero = "";
                String EstadaServeis = "";
                String EstadaDataEntrada = "";
                String EstadaHoraEntrada = "";
                String EstadaDataSortida = "";
                String EstadaHoraSortida = "";
                String EstadaImportServeis = "";
                String EstadaImportFinal = "";
                String EstadaServeisUtilitzats = "";
                String EstadaEstat = "";
                String EstadaPais = "";
                String data = sc.nextLine();
                String[] estada = data.split(regex);
                if (name.equals(estada[idPos])) {
                    // save values of line other than position because there goes the new data
                    if (position == 10) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = estada[7];
                        EstadaHoraSortida = estada[8];
                        EstadaImportServeis = newData;
                        EstadaImportFinal = estada[10];
                        EstadaServeisUtilitzats = estada[11];
                        EstadaEstat = estada[12];
                        EstadaPais = estada[13];
                    }
                    if (position == 12) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = estada[7];
                        EstadaHoraSortida = estada[8];
                        EstadaImportServeis = estada[9];
                        EstadaImportFinal = estada[10];
                        String JoinServeis = "";
                        if (estada[11].equals("null")) {
                            JoinServeis = newData;
                        } else {
                            JoinServeis = estada[11] + "," + newData;
                        }
                        EstadaServeisUtilitzats = JoinServeis;
                        EstadaEstat = estada[12];
                        EstadaPais = estada[13];
                    }
                    if (position == 8) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = newData;
                        EstadaHoraSortida = estada[8];
                        EstadaImportServeis = estada[9];
                        EstadaImportFinal = estada[10];
                        EstadaServeisUtilitzats = estada[11];
                        EstadaEstat = estada[12];
                        EstadaPais = estada[13];
                    }
                    if (position == 9) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = estada[7];
                        EstadaHoraSortida = newData;
                        EstadaImportServeis = estada[9];
                        EstadaImportFinal = estada[10];
                        EstadaServeisUtilitzats = estada[11];
                        EstadaEstat = estada[12];
                        EstadaPais = estada[13];
                    }
                    if (position == 11) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = estada[7];
                        EstadaHoraSortida = estada[8];
                        EstadaImportServeis = estada[9];
                        EstadaImportFinal = newData;
                        EstadaServeisUtilitzats = estada[11];
                        EstadaEstat = estada[12];
                        EstadaPais = estada[13];
                    }
                    if (position == 13) {
                        idEstada = estada[0];
                        EstadaClient = estada[1];
                        EstadaPlanta = estada[2];
                        EstadaNumero = estada[3];
                        EstadaServeis = estada[4];
                        EstadaDataEntrada = estada[5];
                        EstadaHoraEntrada = estada[6];
                        EstadaDataSortida = estada[7];
                        EstadaHoraSortida = estada[8];
                        EstadaImportServeis = estada[9];
                        EstadaImportFinal = estada[10];
                        EstadaServeisUtilitzats = estada[11];
                        EstadaEstat = newData;
                        EstadaPais = estada[13];
                    }

                    // call add line with information from old line
                    text = idEstada + "|" + EstadaClient + "|" + EstadaPlanta + "|" + EstadaNumero + "|" + EstadaServeis + "|" + EstadaDataEntrada
                            + "|" + EstadaHoraEntrada + "|" + EstadaDataSortida + "|" + EstadaHoraSortida + "|" + EstadaImportServeis
                            + "|" + EstadaImportFinal + "|" + EstadaServeisUtilitzats + "|" + EstadaEstat + "|" + EstadaPais;
                }
            }
            sc.close();
            return text;
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static void creacioLog() {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyyMMdd");
        String diaActual = LocalDateTime.now().format(formater);
        String directori = "src/LOGS/";
        String rutaCompleta = directori + diaActual + ".log";
        rutaIFitxerLogActual = rutaCompleta;
        createFile(rutaCompleta);
    }

    public static void addLogLine(String where, String what) {
        try {
            FileWriter fitxer = new FileWriter(rutaIFitxerLogActual, true);
            fitxer.write(LocalTime.now() + "     " + where + "     " + what + "\n");
            fitxer.close();
        } catch (IOException e) {
            System.out.println("An error has ocurred.");
            e.printStackTrace();
        }
    }

    public static void esborraLiniaDeFitxer(String file, String lineToRemove) {
        try {
            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename. 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new 
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                //System.out.println("No s'ha permes esborrar!");
                return;
            } else {
                //System.out.println("Línia esborrada correctament!");
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");
            }

            // log
            addLogLine(file, "deleted line " + lineToRemove);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
