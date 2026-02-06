package CONTROLLER;

import static CONTROLLER.FileModify.*;
import MODEL.*;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * @author radum
 */
public class Hotel {

// log ruta 
    public static String rutaIFitxerLogActual;

// crear Scanner
    static Scanner sc = new Scanner(System.in);
    static boolean exitProgram = true;
// crear array lists d'objectes
    static ArrayList<Client> clients = new ArrayList();
    static ArrayList<Habitacio> habitacions = new ArrayList();
    static ArrayList<Servei> serveis = new ArrayList();
    static ArrayList<Estada> estades = new ArrayList();
    public static ArrayList<Usuari> usuaris = new ArrayList();
    static String userName;
    static String userPassword;
    static String userRole;
    static boolean exitMenu = false;

    public static void main(String[] args) {
        // crear log
        creacioLog();
        // crear serveis 
        createService();
        // crear client
        createClients();
        // crear habitacio
        createRoom();
        // crear estades 
        createEstada();
        // comencament del programa.
        loginScreen();
    }

    static void loginScreen() {
        boolean loggedIn = false;
        System.out.println("Log into the Program.");
        System.out.println(" ");
        while (!loggedIn) {
            System.out.print("Username: ");
            userName = sc.next();
            boolean exitCommand = false;
            if (userName.equals("exit")) {
                exitCommand = true;
                loggedIn = true;
            }
            if (!exitCommand) {
                System.out.print("Password: ");
                userPassword = sc.next();
                if (loginUser(userName, userPassword)) {
                    System.out.println("Login successful.");
                    exitProgram = false;
                    loggedIn = true;
                    userRole = findUserRole(userName, userPassword);
                } else {
                    System.out.println("UserName or Password wrong, Try again.");
                }
            }
        }
        if (exitProgram == false) {
            switch (userRole) {
                case "admin":
                    exitMenu = false;
                    menuAdmin();
                    break;
                case "recepcio":
                    exitMenu = false;
                    menuRecepcio();
                    break;
                case "client":
                    exitMenu = false;
                    menuClient();
                    break;
            }
        }
    }

// menu admin
    static void menuAdmin() {
        while (!exitMenu) {
            System.out.println("  ");
            System.out.println("Name: " + userName + ", Role: " + userRole);
            System.out.println("0. Sortir");
            System.out.println("1. Alta nou usuari");
            System.out.println("2. Llistar usuaris");
            System.out.println("3. Modificar usuari");
            System.out.print("input: ");
            int action = sc.nextInt();
            System.out.println("  ");

            switch (action) {
                case 0:
                    exit();
                    break;
                case 1:
                    addUser();
                    break;
                case 2:
                    listUsers();
                    break;
                case 3:
                    modUsers();
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

// alta usuari programa
    static void addUser() {
// add user to user.txt file 
        boolean correctName = false;
        boolean correctPass = false;
        boolean correctRole = false;
        String newUserName = "";
        String newUserPasswd = "";
        String newUserRole = "";
        while (!correctName) {
            System.out.print("Select a name for the user: ");
            newUserName = sc.next().toLowerCase();
            // look that the name doesn't match any others.
            if (doesUserNameExist(newUserName)) {
                System.out.println("That user already exists.");
            } else {
                correctName = true;
            }
        }
        while (!correctPass) {
            correctPass = true;
            System.out.print("Select a password for " + newUserName + ": ");
            newUserPasswd = sc.next();
            // look that password matches pattern.
            if (newUserPasswd.toUpperCase().equals(newUserPasswd)) { // no hi ha minuscules
                correctPass = false;
                System.out.println("There has to be lower case letters.");
            }
            if (newUserPasswd.toLowerCase().equals(newUserPasswd)) { // no hi ha majuscules
                correctPass = false;
                System.out.println("There has to be upper case letters.");
            }
            if (newUserPasswd.length() < 8 || newUserPasswd.length() > 15) { // mida
                correctPass = false;
                System.out.println("The length of the password is between 8 and 15");
            }
            String number = "-1";
            for (int i = 0; i < 10; i++) {
                String num = i + "";
                if (newUserPasswd.contains(num)) { // numeros
                    number = num;
                }
            }
            if (number.equals("-1")) {
                System.out.println("There has to be at least one number.");
                correctPass = false;
            }

            if (newUserPasswd.contains("|")) { // separacio fitxers es |
                correctPass = false;
                System.out.println("There can't be a \"|\" in the password");
            }

            boolean specialFound = false;
            String[] ArrayPassword = newUserPasswd.split("");
            for (int i = 0; i < ArrayPassword.length; i++) {
                String numberSpecial = "-1";
                for (int j = 0; j < 10; j++) {
                    String num = j + "";
                    if (ArrayPassword[i].contains(num)) { // numeros
                        numberSpecial = num;
                    }
                }
                if (numberSpecial.equals("-1") && letterInString(ArrayPassword[i])) {
                    specialFound = true;
                }
            }
            if (!specialFound) {
                correctPass = false;
            }
        }

        while (!correctRole) {
            System.out.print("Select a role for " + newUserName + " (admin | recepcio | client) : ");
            newUserRole = sc.next().toLowerCase();
            // look that role is admin, recepcio, client.
            if (newUserRole.equals("admin") || newUserRole.equals("recepcio") || newUserRole.equals("client")) {
                correctRole = true;
            } else {
                System.out.println("Enter a correct role ( admin | recepcio | client )");
            }
        }
        addLine("src/PERSISTENCIA/usuaris.txt", newUserName + "|" + newUserPasswd + "|" + newUserRole);
    }

    static boolean letterInString(String character) {
        character.toLowerCase();
        boolean isThereLetter = true;
        if (character.equals("q")) {
            isThereLetter = false;
        }
        if (character.equals("w")) {
            isThereLetter = false;
        }
        if (character.equals("e")) {
            isThereLetter = false;
        }
        if (character.equals("r")) {
            isThereLetter = false;
        }
        if (character.equals("t")) {
            isThereLetter = false;
        }
        if (character.equals("y")) {
            isThereLetter = false;
        }
        if (character.equals("u")) {
            isThereLetter = false;
        }
        if (character.equals("i")) {
            isThereLetter = false;
        }
        if (character.equals("o")) {
            isThereLetter = false;
        }
        if (character.equals("p")) {
            isThereLetter = false;
        }
        if (character.equals("a")) {
            isThereLetter = false;
        }
        if (character.equals("s")) {
            isThereLetter = false;
        }
        if (character.equals("d")) {
            isThereLetter = false;
        }
        if (character.equals("f")) {
            isThereLetter = false;
        }
        if (character.equals("g")) {
            isThereLetter = false;
        }
        if (character.equals("h")) {
            isThereLetter = false;
        }
        if (character.equals("j")) {
            isThereLetter = false;
        }
        if (character.equals("k")) {
            isThereLetter = false;
        }
        if (character.equals("l")) {
            isThereLetter = false;
        }
        if (character.equals("z")) {
            isThereLetter = false;
        }
        if (character.equals("x")) {
            isThereLetter = false;
        }
        if (character.equals("c")) {
            isThereLetter = false;
        }
        if (character.equals("v")) {
            isThereLetter = false;
        }
        if (character.equals("b")) {
            isThereLetter = false;
        }
        if (character.equals("n")) {
            isThereLetter = false;
        }
        if (character.equals("m")) {
            isThereLetter = false;
        }
        return isThereLetter;
    }

// llistar els usuaris del sistema  
    static void listUsers() {
        usuaris.clear();// detete user array list 
        createUsers(); // read each line and create object,
        for (int i = 0; i < usuaris.size(); i++) {// show users
            System.out.println(usuaris.get(i).showUser());
        }
    }

// modificar els usuaris del sistema
    static void modUsers() {
        boolean exitModUsers = false;
        listUsers();
        System.out.println("  ");
        // chose user by name
        while (!exitModUsers) {
            exitModUsers = true;
            System.out.print("Input the name of the user you want to modify or exit: ");
            String userName = sc.next();
            if (userName.equals("exit")) {
                exitModUsers = false;
                break;
            }

            System.out.print("  ");
            // confirm user exists
            if (!doesUserNameExist(userName)) {
                System.out.println("The user has not been found.");
                exitModUsers = false;
            }
            showUserByName(userName);
            // choose change name,pass,role delete or exit.
            boolean fieldSelection = false;
            while (!fieldSelection) {
                System.out.println("Choose a option to modify or exit.");
                System.out.println("0. Sortir");
                System.out.println("1. Name");
                System.out.println("2. Password");
                System.out.println("3. Role");
                System.out.println("4. Delete");
                System.out.print("-: ");
                int option = sc.nextInt();
                switch (option) {
                    case 0:
                        fieldSelection = true;
                        break;
                    case 1:
                        changeNameUser(userName);
                        break;
                    case 2:
                        changePassUser(userName);
                        break;
                    case 3:
                        changeRoleUser(userName);
                        break;
                    case 4:
                        deleteUser(userName);
                        fieldSelection = true;
                        break;
                }
            }
        }
    }

    // mod users
    static void changeNameUser(String userName) {
        System.out.print("What's the new name? : ");
        String newData = sc.next();
        System.out.println("Are you sure you want to change the name to " + newData + "? y/n");
        System.out.print("-: ");
        String confirmation = sc.next();
        confirmationNewData(userName, newData, confirmation, 0);
    }

    static void changePassUser(String userName) {
        System.out.print("What's the new password? : ");
        String newData = sc.next();
        System.out.println("Are you sure you want to change the password to " + newData + "? y/n");
        System.out.print("-: ");
        String confirmation = sc.next();
        confirmationNewData(userName, newData, confirmation, 1);
    }

    static void changeRoleUser(String userName) {
        System.out.print("What's the new name? : ");
        String newData = sc.next();
        System.out.println("Are you sure you want to change the name to " + newData + "? y/n");
        System.out.print("-: ");
        String confirmation = sc.next();
        confirmationNewData(userName, newData, confirmation, 2);
    }

    static void confirmationNewData(String userName, String newData, String confirmation, int pos) {
        if (confirmation.equals("y")) {
            String newLine = getNewLine("src/PERSISTENCIA/usuaris.txt", userName, 1, pos, newData);
            String oldLine = getOldLine("src/PERSISTENCIA/usuaris.txt", userName, 0, 3);
            esborraLiniaDeFitxer("src/PERSISTENCIA/usuaris.txt", oldLine);
            addLine("src/PERSISTENCIA/usuaris.txt", newLine);
        } else {
            System.out.println("Exiting...");
        }
    }

    static void deleteUser(String userName) {
        System.out.print("Are you sure you want to delete " + userName + " y/n : ");
        String option = sc.next();
        if (option.equals("y")) {
            String oldLine = getOldLine("src/PERSISTENCIA/usuaris.txt", userName, 0, 3);
            esborraLiniaDeFitxer("src/PERSISTENCIA/usuaris.txt", oldLine);
        } else {
            System.out.println("Exiting...");
        }

    }

// menu recepcio
    static void menuRecepcio() {
        while (!exitMenu) {
            System.out.println("  ");
            System.out.println("Name: " + userName + ", Role: " + userRole);
            System.out.println("  ");
            System.out.println("0. Sortir");
            System.out.println("1. Alta nou client");
            System.out.println("2. Llistar clients");
            System.out.println("3. Modificar client");
            System.out.println("4. Afegir nova Habitacio");
            System.out.println("5. Llistar habitacions");
            System.out.println("6. Modificar habitacio");
            System.out.println("7. Afegir nou servei");
            System.out.println("8. Llistar serveis");
            System.out.println("9. Modificar servei");
            System.out.println("10. Afegir estada");
            System.out.println("11. Llistar estades");
            System.out.println("12. Fer servir estada");
            System.out.print("input: ");
            int action = sc.nextInt();

            System.out.println("  ");

            switch (action) {
                case 1:
                    addClient();
                    break;
                case 2:
                    listClient();
                    break;
                case 3:
                    modClient();
                    break;
                case 4:
                    addHab();
                    break;
                case 5:
                    listHab();
                    break;
                case 6:
                    modHab();
                    break;
                case 7:
                    addServei();
                    break;
                case 8:
                    listServei();
                    break;
                case 9:
                    modServei();
                    break;
                case 10:
                    addEstada();
                    break;
                case 11:
                    listEstada();
                    break;
                case 12:
                    useEstada();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

// crea i afageix el client al array list de client
    static void addClient() {
        System.out.print("Nom: ");
        String cliName = sc.next();
        System.out.print("NIF: ");
        String cliNif = sc.next();
        // check client doesn't exist in database
        if (doesClientExist(cliNif)) {
            System.out.println("the client already exists.");
            addClient();
        }
        System.out.print("Data Naixament (YYYY MM DD): ");
        int year = sc.nextInt();
        int month = sc.nextInt();
        int day = sc.nextInt();
        clients.add(new Client(cliName, cliNif, LocalDate.of(year, month, day), false));
        System.out.println("Client afegit a la base de dades...");
    }

// un loop del size del array del client i ensenya les dedes del client.
    static void listClient() {
        for (int i = 0; i < clients.size(); i++) {
            System.out.println("Client " + i + ". " + clients.get(i).showData());
        }
    }

// ensenya els clients que no estan en ninguna estada
    static void listClientEstada() {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getEstada() != true) {
                System.out.println("Client " + i + ". " + clients.get(i).showData());
            }
        }
    }

// crida listClient() i esculls quin vols modificar. amb les opcions dels client nom, dni, borrar
    static void modClient() {
        System.out.println("Quin client vols modificar?");
        listClient();
        System.out.print("input: ");
        int clientChoose = sc.nextInt();
        boolean modClientExitFields = false;
        while (!modClientExitFields) {
            System.out.println("Client seleccionat -> " + clients.get(clientChoose).showData());
            System.out.println("Quin camp vols modificar");
            System.out.println("1. Nom");
            System.out.println("2. NIF");
            System.out.println("3. Data Naixament");
            System.out.println("4. Borrar ");
            System.out.println("5. Sortir ");
            System.out.print("input: ");
            int modCliOption = sc.nextInt();
            switch (modCliOption) {
                case 1:
                    System.out.print("nom: ");
                    String cliNewName = sc.next();
                    // grab old line where client is
                    String oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 4);
                    // gram new line 
                    String newLine = getNewLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 0, cliNewName);
                    // delete using old line 
                    esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
                    // add Line with new line to clients.txt
                    addLine("src/PERSISTENCIA/clients.txt", newLine);
                    clients.get(clientChoose).setNom(cliNewName);
                    break;
                case 2:
                    System.out.print("nif: ");
                    String cliNewNif = sc.next();
                    // grab old line where client is
                    oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 4);
                    // grab new line 
                    newLine = getNewLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 1, cliNewNif);
                    // delete using old line 
                    esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
                    // add Line with new line to clients.txt
                    addLine("src/PERSISTENCIA/clients.txt", newLine);
                    clients.get(clientChoose).setNif(cliNewNif);
                    break;
                case 3:
                    System.out.print("Data Naixament (YYYY MM DD): ");
                    int year = sc.nextInt();
                    int month = sc.nextInt();
                    int day = sc.nextInt();
                    LocalDate date = LocalDate.of(year, month, day);
                    String dateS = date + "";
                    // grab old line where client is
                    oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 4);
                    // grab new line 
                    newLine = getNewLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 2, dateS);
                    // delete using old line 
                    esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
                    // add Line with new line to clients.txt
                    addLine("src/PERSISTENCIA/clients.txt", newLine);

                    clients.get(clientChoose).setDataNeix(date);
                    break;
                case 4:
                    // grab old line where client is
                    oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(clientChoose).getNif(), 1, 4);
                    // delete using old line 
                    esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
                    clients.remove(clientChoose);
                    System.out.println("Client Borrat.");
                    modClientExitFields = true;
                    break;
                case 5:
                    modClientExitFields = true;
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

// crea i afageix l'habitacio en l'array dels habitacions
    static void addHab() {
        System.out.print("Planta: ");
        int habPlanta = sc.nextInt();
        boolean habExist = false;
        int habNumero = 0;
        while (!habExist) {
            habExist = true;
            System.out.print("Num habitacio: ");
            habNumero = sc.nextInt();
            for (int i = 0; i < habitacions.size(); i++) {
                if (habitacions.get(i).getPlanta() == habPlanta && habitacions.get(i).getNumero() == habNumero) {
                    habExist = false;
                    System.out.println("Ja existeix aquesta planta.");
                }
            }
        }

        System.out.print("Preu habitacio: ");
        double habPreu = sc.nextDouble();
        habitacions.add(new Habitacio(habPlanta, habNumero, habPreu));
        System.out.println("Habitacio afegida a la base de dades...");
    }

// llista les habitacions del array.
    static void listHab() {
        for (int i = 0; i < habitacions.size(); i++) {
            System.out.println("Habitacio " + i + ". " + habitacions.get(i).showData());
        }
    }

// Llista les habitacions no ocupades.
    static void listHabEstada() {
        for (int i = 0; i < habitacions.size(); i++) {
            if (habitacions.get(i).isOcupada() == false) {
                System.out.println("Habitacio " + i + ". " + habitacions.get(i).showData());
            }
        }
    }

// crida listHab() i esculls quina habitacio vols modificar.
    static void modHab() {
        System.out.println("Quina habitacio vols modificar?");
        listHab();
        System.out.print("input: ");
        int habChoose = sc.nextInt();
        boolean modHabExitFields = false;
        while (!modHabExitFields) {
            System.out.println("Habitacio seleccionada -> " + habitacions.get(habChoose).showData());
            System.out.println("Quin camp vols modificar");
            System.out.println("1. Planta");
            System.out.println("2. Numero");
            System.out.println("3. Preu");
            System.out.println("4. Ocupada");
            System.out.println("5. Borrar ");
            System.out.println("6. exit ");
            System.out.print("input: ");
            int modHabOption = sc.nextInt();
            switch (modHabOption) {
                case 1:
                    System.out.print("Planta: ");
                    int habNewFloor = sc.nextInt();
                    // grab old line
                    String oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero());
                    // grab new line
                    String newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero(), 0, habNewFloor + "");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/habitacions.txt", newLine);
                    habitacions.get(habChoose).setPlanta(habNewFloor);
                    break;
                case 2:
                    System.out.print("Numero: ");
                    int habNewNumber = 0;
                    boolean habExist = false;
                    while (!habExist) {
                        habExist = true;
                        System.out.print("Num habitacio: ");
                        habNewNumber = sc.nextInt();
                        for (int i = 0; i < habitacions.size(); i++) {
                            if (habitacions.get(i).getPlanta() == habitacions.get(habChoose).getPlanta() && habitacions.get(i).getNumero() == habNewNumber) {
                                habExist = false;
                                System.out.println("Ja existeix aquesta planta.");
                            }
                        }
                    }
                    // grab old line
                    oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero());
                    // grab new line
                    newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero(), 1, habNewNumber + "");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/habitacions.txt", newLine);
                    habitacions.get(habChoose).setNumero(habNewNumber);
                    break;
                case 3:
                    System.out.print("Preu (00.0): ");
                    double habNewPrice = sc.nextDouble();
                    // grab old line
                    oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero());
                    // grab new line
                    newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero(), 2, habNewPrice + "");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/habitacions.txt", newLine);
                    habitacions.get(habChoose).setPreuNit(habNewPrice);
                    break;
                case 4:
                    System.out.print("Ocupada (true / false): ");
                    boolean habNewStatus = sc.nextBoolean();
                    // grab old line
                    oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero());
                    // grab new line
                    newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero(), 3, habNewStatus + "");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/habitacions.txt", newLine);
                    habitacions.get(habChoose).setOcupada(habNewStatus);
                    break;
                case 5:
                    // grab old line
                    oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChoose).getPlanta(), habitacions.get(habChoose).getNumero());
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    habitacions.remove(habChoose);
                    System.out.println("Habitacio borrada.");
                    modHabExitFields = true;
                    break;
                case 6:
                    modHabExitFields = true;
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

// afageix un servei al array list
    static void addServei() {
        System.out.print("Nom: ");
        String serveiName = sc.next();
        System.out.print("Preu: ");
        double serveiPreu = sc.nextDouble();
        serveis.add(new Servei(serveiName, serveiPreu));
        System.out.println("Servei afegit a la base de dades...");
    }

// llista els serveis
    static void listServei() {
        for (int i = 0; i < serveis.size(); i++) {
            System.out.println("Servei " + i + ". " + serveis.get(i).showData());
        }
    }

// llista els serevis i tries quin fer servir per modificar
    static void modServei() {
        System.out.println("Quin servei vols modificar?");
        listServei();
        System.out.print("input: ");
        int serveiChoose = sc.nextInt();
        boolean modServeiExitFields = false;
        while (!modServeiExitFields) {
            System.out.println("Servei seleccionat -> " + serveis.get(serveiChoose).showData());
            System.out.println("Quin camp vols modificar");
            System.out.println("1. Nom");
            System.out.println("2. Preu");
            System.out.println("3. Borrar");
            System.out.println("4. Sortir");
            System.out.print("input: ");
            int modCliOption = sc.nextInt();
            switch (modCliOption) {
                case 1:
                    System.out.print("Nom: ");
                    String serveiNewName = sc.next();
                    // get old line
                    String oldLine = getOldLine("src/PERSISTENCIA/serveis.txt", serveis.get(serveiChoose).getNom(), 0, 2);
                    // get new line
                    String newLine = getNewLineService("src/PERSISTENCIA/serveis.txt", serveis.get(serveiChoose).getNom(), 0, serveiNewName);
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/serveis.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/serveis.txt", newLine);

                    serveis.get(serveiChoose).setNom(serveiNewName);
                    break;
                case 2:
                    System.out.print("Preu: ");
                    double serveiNewPreu = sc.nextDouble();
                    // get old line
                    oldLine = getOldLine("src/PERSISTENCIA/serveis.txt", serveis.get(serveiChoose).getNom(), 0, 2);
                    // get new line
                    newLine = getNewLineService("src/PERSISTENCIA/serveis.txt", serveis.get(serveiChoose).getNom(), 1, serveiNewPreu + "");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/serveis.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/serveis.txt", newLine);
                    serveis.get(serveiChoose).setPreu(serveiNewPreu);
                    break;
                case 3:
                    // get old line
                    oldLine = getOldLine("src/PERSISTENCIA/serveis.txt", serveis.get(serveiChoose).getNom(), 0, 2);
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/serveis.txt", oldLine);
                    serveis.remove(serveiChoose);
                    System.out.println("Servei borrat.");
                    modServeiExitFields = true;
                    break;
                case 4:
                    modServeiExitFields = true;
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

// obj estada es crea i es afageix en l'arraylist 
    static void addEstada() {
        // crear estada selecionant un client, habitacio i els serveis que vol el client
        // ask for time and date 
        System.out.print("Entra la data del checkin. (YYYY MM DD): ");
        int year = sc.nextInt();
        int month = sc.nextInt();
        int day = sc.nextInt();
        LocalDate entradaData = LocalDate.of(year, month, day);

        System.out.print("Entra el la hora del checkin (HH MM): ");
        int hour = sc.nextInt();
        int minute = sc.nextInt();
        LocalTime entradaTemps = LocalTime.of(hour, minute);

        System.out.print("Entra el pais del Hotel (Espanya | Franca | Andorra | Portugal): ");
        String paisEstada = sc.next();
        estades.add(new Estada(estades.size(), entradaTemps, entradaData, paisEstada.toLowerCase()));

        listClientEstada();
        //--------------------------------------------------------------------------------------
        System.out.print("Escull el client: ");
        int clientChosen = sc.nextInt();
        // canviar l'estatus del client
        clients.get(clientChosen).setEstada(true);
        // client change estada to true ------------------------------------------------------------------
        // get old line
        String oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(clientChosen).getNif(), 1, 4);
        // get new line
        String newLine = getNewLine("src/PERSISTENCIA/clients.txt", clients.get(clientChosen).getNif(), 1, 3, "true");
        // delete line
        esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
        // add line
        addLine("src/PERSISTENCIA/clients.txt", newLine);

        estades.getLast().setClientEstada(clients.get(clientChosen).getNif()); //(nif client to string)

        listHabEstada(); // ensenyar habitacions disponibles
        System.out.print("Escull l'habitacio: ");
        int habChosen = sc.nextInt();
        estades.getLast().setHabitacioEstadaFloor(habitacions.get(habChosen).getPlanta());
        estades.getLast().setHabitacioEstadaNum(habitacions.get(habChosen).getNumero());

        // ocupar l'habitacio 
        habitacions.get(habChosen).setOcupada(true);
        // change room true
        // grab old line
        oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChosen).getPlanta(), habitacions.get(habChosen).getNumero());
        // grab new line
        newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(habChosen).getPlanta(), habitacions.get(habChosen).getNumero(), 3, "true");
        // delete line
        esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
        // add line
        addLine("src/PERSISTENCIA/habitacions.txt", newLine);

        System.out.println("Serveis: ");
        listServei();
        boolean serveiChosenEnd = false;
        while (!serveiChosenEnd) {
            System.out.print("Escull els serveis (-1 per sortir): ");
            int serveiChosen = sc.nextInt();
            if (serveiChosen == -1) {
                serveiChosenEnd = true;
            } else {
                // add serveice to estada
                estades.getLast().addServeiEstada(serveis.get(serveiChosen).getNom());
                //list all services added 
                for (int i = 0; i < estades.getLast().getServeiEstada().size(); i++) {
                    for (int j = 0; j < serveis.size(); j++) {
                        if (serveis.get(j).getNom().equals(estades.getLast().getServeiEstada().get(i))) {
                            System.out.println("- " + serveis.get(j).showData());
                        }
                    }
                }
            }
        }
        addLine("src/PERSISTENCIA/estades.txt", estades.getLast().getIdEstada() + "|" + clients.get(clientChosen).getNif() + "|"
                + habitacions.get(habChosen).getPlanta() + "|" + habitacions.get(habChosen).getNumero() + "|"
                + estades.getLast().showEstadaServeis() + "|" + entradaData + "|" + entradaTemps + "|" + "null"
                + "|" + "null" + "|" + "0.0" + "|" + "0.0" + "|" + "null" + "|" + "0" + "|" + paisEstada);
        System.out.println("Estada creada.");
    }

    static void listEstada() {
        for (int i = 0; i < estades.size(); i++) {
            System.out.println("Estada " + i + ". " + estades.get(i).showDetails());
            System.out.println("Sortida data: " + estades.get(i).getDataSortida() + " hora: " + estades.get(i).getHoraSortida());
// show client
            for (int x = 0; x < clients.size(); x++) {
                if (clients.get(x).getNif().equals(estades.get(i).getClientEstada())) {
                    System.out.println("Client estacionat: " + clients.get(x).showData());
                }
            }
// show room
            for (int x = 0; x < habitacions.size(); x++) {
                if (habitacions.get(x).getPlanta() == estades.get(i).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(i).getHabitacioEstadaNum()) {
                    System.out.println("Habitacio: " + habitacions.get(x).showData());
                }
            }
// show serveis
            System.out.println("Serveis: ");
            for (int j = 0; j < estades.get(i).getServeiEstada().size(); j++) {
                for (int x = 0; x < serveis.size(); x++) {
                    if (serveis.get(x).getNom().equals(estades.get(i).getServeiEstada().get(j))) {
                        System.out.println(serveis.get(x).showData());
                    }
                }
            }
            if (estades.get(i).getFacturaFinal() != 0) {
                System.out.print("Factura final: ");
                System.out.printf("%.2f", estades.get(i).getFacturaFinal());
                System.out.print(" euros.");
            }
            System.out.println(" ");
        }
    }

    static void listEstadaUsable() {
        for (int i = 0; i < estades.size(); i++) {
            if (estades.get(i).getFacturaFinal() == 0.00) {
                System.out.println("Estada " + i + ". " + estades.get(i).showDetails());
// client
                for (int x = 0; x < clients.size(); x++) {
                    if (clients.get(x).getNif().equals(estades.get(i).getClientEstada())) {
                        System.out.println("Client estacionat: " + clients.get(x).showData());
                    }
                }
// habitacio
                for (int x = 0; x < habitacions.size(); x++) {
                    if (habitacions.get(x).getPlanta() == estades.get(i).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(i).getHabitacioEstadaNum()) {
                        System.out.println("Habitacio: " + habitacions.get(x).showData());
                    }
                }
// serveis
                System.out.println("Serveis: ");
                for (int j = 0; j < estades.get(i).getServeiEstada().size(); j++) {
                    for (int x = 0; x < serveis.size(); x++) {
                        if (serveis.get(x).getNom().equals(estades.get(i).getServeiEstada().get(j))) {
                            System.out.println(serveis.get(x).showData());
                        }
                    }
                }
                System.out.println(" ");
            }
        }
    }

// es el sub menu que fem serveir quan esta el client en l'hotel.
    static void useEstada() {
        // listEstada();
        listEstadaUsable();
        System.out.print("Escull quina estada vols fer servir (-1 exit): ");
        int triarEstada = sc.nextInt();
        // ensenyar tots els serveis -> afegir els serveis a serveis utilitzats
        // consultar import -> mirar els serveis utilitzats i mirar el import de cada un i ensenyarlos
        // checkout i calcular factura final -> mostrar els serveis utilitzars, dies passats i IVA
        boolean estadaEnd = false;
        if (triarEstada == -1) {
            estadaEnd = true;
            System.out.println(" ");
        }
        if (estades.get(triarEstada).getFacturaFinal() != 0.00) {
            estadaEnd = true;
            System.out.println(" ");
        }
        while (!estadaEnd) {
            System.out.println(" ");
            System.out.println("Opcions: ");
            System.out.println("1. fer servir servei");
            System.out.println("2. consultar import ");
            System.out.println("3. checkout");
            System.out.println("4. sortir");
            System.out.print("input: ");
            int action = sc.nextInt();
            System.out.println(" ");
            switch (action) {
                case 1:
                    estadaServeis(triarEstada);
                    break;
                case 2:
                    estadaImport(triarEstada);
                    break;
                case 3:
                    estadaCheckout(triarEstada);
                    break;
                case 4:
                    estadaEnd = true;
                    break;

                default:
                    System.out.println("");
                    break;
            }
        }

    }

// en l'estade seleccionada fa serveir un servei i afageix el preu a l'import.
    static void estadaServeis(int triarEstada) {
        System.out.println("Serveis: ");
        for (int j = 0; j < estades.get(triarEstada).getServeiEstada().size(); j++) {
            for (int x = 0; x < serveis.size(); x++) {
                if (estades.get(triarEstada).getServeiEstada().get(j).equals(serveis.get(x).getNom())) {
                    System.out.println(j + ". " + serveis.get(x).showData());
                }
            }
        }
        boolean usingServiceEnd = false;
        while (!usingServiceEnd) {
            System.out.print("Fer servir un servei (-1 per sortir): ");
            int estadaServeiUtilitzar = sc.nextInt();
            if (estadaServeiUtilitzar == -1) { // ensenya el index del servei de dins de estada 
                usingServiceEnd = true;
            } else {
                String nomServei = estades.get(triarEstada).getServeiEstada().get(estadaServeiUtilitzar);
                int indexServei = 0;
                for (int x = 0; x < serveis.size(); x++) {
                    if (serveis.get(x).getNom().equals(nomServei)) {
                        estades.get(triarEstada).addServeisUtilitzats(serveis.get(x).getNom());
                        // add serveice to serveis utilitzats
                        // old line
                        String oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
                        // new line
                        String newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 12, serveis.get(x).getNom());
                        // del line
                        esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
                        // add line
                        addLine("src/PERSISTENCIA/estades.txt", newLine);
                        indexServei = x;
                    }
                }
                double inportVell = estades.get(triarEstada).getImportActual();
                double inportServei = serveis.get(indexServei).getPreu();
                double inportTotalServei = inportVell + inportServei;
                estades.get(triarEstada).setImportActual(inportTotalServei);
                // fitxer estades import 
                // old line
                String oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
                // new line
                String newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 10, inportTotalServei + "");
                // del line
                esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
                // add line
                addLine("src/PERSISTENCIA/estades.txt", newLine);
            }
        }
    }

// ensenya l'import de l'estade selecionada
    static void estadaImport(int triarEstada) {
        System.out.println("Import actual: " + estades.get(triarEstada).getImportActual() + " euros.");
    }

// des ocupa l'habitacio i canvia l'estat del client, calcula els dies i el iva de l'import
    static void estadaCheckout(int triarEstada) {
        if (estades.get(triarEstada).getFacturaFinal() != 0.0) {
            System.out.println("There's a checkout already made.");
        } else {
            System.out.println(" ");
            System.out.println("Data d'entrada " + estades.get(triarEstada).getDataEntrada());
            System.out.print("Afegeix la data de sortida (YYYY MM DD): ");
            int year = sc.nextInt();
            int month = sc.nextInt();
            int day = sc.nextInt();
            estades.get(triarEstada).setDataSortida(LocalDate.of(year, month, day));
// set estada data sortida
            // old line
            String oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
            // new line
            String newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 8, LocalDate.of(year, month, day) + "");
            // del line
            esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
            // add line
            addLine("src/PERSISTENCIA/estades.txt", newLine);

            System.out.println("Hora d'entrada " + estades.get(triarEstada).getHoraEntrada());
            System.out.print("Afageix la hora de sortida (HH MM): ");
            int hour = sc.nextInt();
            int minute = sc.nextInt();
            estades.get(triarEstada).setHoraSortida(LocalTime.of(hour, minute));
// estada hora sortida
            // old line
            oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
            // new line
            newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 9, LocalTime.of(hour, minute) + "");
            // del line
            esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
            // add line
            addLine("src/PERSISTENCIA/estades.txt", newLine);
            int diesPassats = (int) ChronoUnit.DAYS.between(estades.get(triarEstada).getDataEntrada(), estades.get(triarEstada).getDataSortida());

            if ((estades.get(triarEstada).getHoraSortida().getHour() >= 12 && estades.get(triarEstada).getHoraSortida().getMinute() > 30)
                    || estades.get(triarEstada).getHoraSortida().getHour() > 12) {
                diesPassats++;
            }

// show serveices
            System.out.println("Serveis utilitzats: ");
            for (int i = 0; i < estades.get(triarEstada).getServeisUtilitzats().size(); i++) {
                for (int x = 0; x < serveis.size(); x++) {
                    if (serveis.get(x).getNom().equals(estades.get(triarEstada).getServeisUtilitzats().get(i))) {
                        System.out.println("Servei. " + serveis.get(x).showData());
                    }
                }
            }
            double importDies = 0.0;
            for (int x = 0; x < habitacions.size(); x++) {
                if (habitacions.get(x).getPlanta() == estades.get(triarEstada).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(triarEstada).getHabitacioEstadaNum()) {
                    importDies = habitacions.get(x).getPreuNit() * diesPassats;
                }
            }
            double importActualEnd = estades.get(triarEstada).getImportActual() + importDies;
            estades.get(triarEstada).setImportActual(importActualEnd);
// import actual fitxer
            // old line
            oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
            // new line
            newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 10, importActualEnd + "");
            // del line
            esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
            // add line
            addLine("src/PERSISTENCIA/estades.txt", newLine);

            IVA IVApais = IVA.valueOf(estades.get(triarEstada).getPais().toUpperCase());
            estades.get(triarEstada).setFacturaFinal(estades.get(triarEstada).getImportActual() * IVApais.showIVA());
// factura final fitxer
            // old line
            oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
            // new line
            newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 11, estades.get(triarEstada).getFacturaFinal() + "");
            // del line
            esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
            // add line
            addLine("src/PERSISTENCIA/estades.txt", newLine);

            System.out.println("Preu sense IVA: " + estades.get(triarEstada).getImportActual() + " euros");
            System.out.print("Factura Final: ");
            System.out.printf("%.2f", estades.get(triarEstada).getFacturaFinal());
            System.out.print(" euros.");

            for (int x = 0; x < habitacions.size(); x++) {
                if (habitacions.get(x).getPlanta() == estades.get(triarEstada).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(triarEstada).getHabitacioEstadaNum()) {
                    habitacions.get(x).setOcupada(false);
                    // grab old line
                    oldLine = getOldLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(x).getPlanta(), habitacions.get(x).getNumero());
                    // grab new line
                    newLine = getNewLineRoom("src/PERSISTENCIA/habitacions.txt", habitacions.get(x).getPlanta(), habitacions.get(x).getNumero(), 3, "false");
                    // delete line
                    esborraLiniaDeFitxer("src/PERSISTENCIA/habitacions.txt", oldLine);
                    // add line
                    addLine("src/PERSISTENCIA/habitacions.txt", newLine);
                }
            }

            for (int x = 0; x < clients.size(); x++) {
                if (clients.get(x).getNif().equals(estades.get(triarEstada).getClientEstada())) {
                    clients.get(x).setEstada(false);
                    // grab old line where client is
                    oldLine = getOldLine("src/PERSISTENCIA/clients.txt", clients.get(x).getNif(), 1, 4);
                    // gram new line 
                    newLine = getNewLine("src/PERSISTENCIA/clients.txt", clients.get(x).getNif(), 1, 3, "false");
                    // delete using old line 
                    esborraLiniaDeFitxer("src/PERSISTENCIA/clients.txt", oldLine);
                    // add Line with new line to clients.txt
                    addLine("src/PERSISTENCIA/clients.txt", newLine);
                }
            }
            // set estat a estada 
            estades.get(triarEstada).setEstat(1);
            // old line
            oldLine = getOldLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0);
            // new line
            newLine = getNewLineStay("src/PERSISTENCIA/estades.txt", estades.get(triarEstada).getIdEstada() + "", 0, 13, "1");
            // del line
            esborraLiniaDeFitxer("src/PERSISTENCIA/estades.txt", oldLine);
            // add line
            addLine("src/PERSISTENCIA/estades.txt", newLine);

            System.out.println(" ");
        }
    }

// menu client
    static void menuClient() {
        while (!exitMenu) {
            System.out.println("  ");
            System.out.println("Name: " + userName + ", Role: " + userRole);
            System.out.println("  ");
            System.out.println("0. Sortir");
            System.out.println("1. Consultar estada actual");
            System.out.println("2. historial d'estades");
            System.out.print("input: ");
            int action = sc.nextInt();
            System.out.println("  ");

            switch (action) {
                case 0:
                    exit();
                    break;
                case 1:
                    consultarEstadaClient(userName);
                    break;
                case 2:
                    historialEstades(userName);
                    break;
                default:
                    System.out.println("Introduiex una opcio correcte.");
                    break;
            }
        }
    }

    static void consultarEstadaClient(String name) {
        // does client exist
        boolean cliFound = true;
        if (!doesClientExistByName(name)) {
            cliFound = false;
            System.out.println("Client not found, please inform a receptionist to register you.");
        }
        if (cliFound) {
            System.out.println("Showing current reservation:");
            // return dni by name
            String ClientDni = returnDNI(name);
            // show estades with client = dni  && estat = 0
            for (int i = 0; i < estades.size(); i++) {
                if (estades.get(i).getClientEstada().equals(ClientDni) && estades.get(i).getEstat() == 0) {
                    System.out.println(estades.get(i).showDetails());
                    for (int x = 0; x < habitacions.size(); x++) {
                        if (habitacions.get(x).getPlanta() == estades.get(i).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(i).getHabitacioEstadaNum()) {
                            System.out.println("Habitacio: " + habitacions.get(x).showData());
                        }
                    }
                }
            }
        }

    }

    static void historialEstades(String name) {
        //  does client exist
        boolean cliFound = true;
        if (!doesClientExistByName(name)) {
            cliFound = false;
            System.out.println("Client not found, please inform a receptionist to register you.");
        }
        if (cliFound) {
            System.out.println("Showing all previous reservations:");
            // return dni by name
            String ClientDni = returnDNI(name);
            // show estades with client = dni 
            for (int i = 0; i < estades.size(); i++) {
                if (estades.get(i).getClientEstada().equals(ClientDni) && estades.get(i).getEstat() == 1) {
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(i + "- " + estades.get(i).showDetails());
                    System.out.println("Sortida data: " + estades.get(i).getDataSortida() + " hora: " + estades.get(i).getHoraSortida());
                    for (int x = 0; x < habitacions.size(); x++) {
                        if (habitacions.get(x).getPlanta() == estades.get(i).getHabitacioEstadaFloor() && habitacions.get(x).getNumero() == estades.get(i).getHabitacioEstadaNum()) {
                            System.out.println("Habitacio: " + habitacions.get(x).showData());
                        }
                    }
                    System.out.print("Factura final: ");
                    System.out.printf("%.2f", estades.get(i).getFacturaFinal());
                    System.out.print(" euros.");
                }
            }
        }
    }

// surt del programa
    static void exit() {
        System.out.println("Loging out...");
        System.out.println("  ");
        exitMenu = true;
        loginScreen();
    }
}
