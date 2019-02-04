import models.*;
import utils.*;

import java.io.*;
import java.util.*;

public class app {

    private static int money;
    // Status de las rooms.
    private static Set<String> statusRoom = new HashSet<>();

    // Create hash from Customers, Workers and Rooms.
    public static HashMap<String, Customer> customers = new HashMap<>();
    private static HashMap<String, Worker> workers = new HashMap<>();
    private static HashMap<String, Room> rooms = new HashMap<>();

    public static void main(String[] args) {
        // start app with 1000 of money
        money = 1000;
        //initialize status rooms for checkout later.
        setStatusRoom();
        // We watch data to import from Mar.
        watchDataMar();

        // now start speak with customers.
        startPlay();
    }

    private static void startPlay() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            // igual que en Mar pero con for.
            for (String chain = br.readLine(); chain != null; chain = br.readLine()) {
                checkMoney();
                String[] lines = chain.split(" ");
                menu(lines);
            }
            br.close();
        } catch (IOException iox) {
            System.out.println("Error: " + iox.getMessage());
        }
    }


    private static void watchDataMar() {
        File dates = new File("Enunciado/P3_load_data.txt");
        FileReader fr;
        try {
            fr = new FileReader(dates);
            BufferedReader br = new BufferedReader(fr);
            String chain;
            while ((chain = br.readLine()) != null) {
                String[] lines = chain.split(" ");
                menu(lines);
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Error leer: " + ex.getMessage());
        }
    }

    private static void menu(String[] lines) {
        try {
            switch (lines[0].toLowerCase()) {
                case "room":
                    if (lines.length == 4) {
                        String[] servicesRoom = lines[3].toLowerCase().split(",");
                        makeRoom(lines[1], lines[2], servicesRoom);
                        System.out.println(Colors.BLUE + "--> new Room added " + lines[1] + " <--" + Colors.RESET);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "worker":
                    if (lines.length == 4) {
                        String[] skills = lines[3].toLowerCase().split(",");
                        makeWorker(lines[1], lines[2], skills);
                        System.out.println(Colors.BLUE + "--> new Worker added " + lines[1] + " <--" + Colors.RESET);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "reservation":
                    if (lines.length == 4) {
                        String[] servicesCustomer = lines[3].toLowerCase().split(",");
                        makeCustomer(lines[1], lines[2], servicesCustomer);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "hotel":
                    break;
                case "problem":
                    break;
                case "request":
                    break;
                case "finish":
                    break;
                case "leave":
                    break;
                case "money":
                    if (lines.length == 1) {
                        System.out.println(Colors.PURPLE + "================================");
                        System.out.println("==> MONEY : " + money + " $  <==");
                        System.out.println("================================" + Colors.RESET);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "exit":
                    if (lines.length == 1) {
                        System.exit(0);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                default:
                    throw new MiExcepcion("[ Wrong Service ]");
            }
        } catch (MiExcepcion mx) {
            System.out.println(Colors.RED + mx.getMessage() + Colors.RESET);
        }
    }

    private static void makeCustomer(String dni, String nCustomers, String[] services) throws MiExcepcion {
        try {
            int numDni = Integer.parseInt(dni);
            int numCustomers = Integer.parseInt(nCustomers);
            Set<String> servicesWanted = new HashSet<>(Arrays.asList(services));
            if (customers.get(dni) == null) {
                Room goodRoom = null;
                boolean breac = false;


                for (Room room : rooms.values()) {
                    if (!breac) {
                        if (room.getStatus().equals("CLEAN")) {
                            if (room.getServices().containsAll(servicesWanted)) {
                                if (room.getNumCustomers() == numCustomers) {
                                    goodRoom = room;
                                    breac = true;
                                } else {
                                    if (room.getNumCustomers() > numCustomers && goodRoom != null) {
                                        if (goodRoom.getNumCustomers() < room.getNumCustomers()) {
                                            goodRoom = room;
                                        }
                                    } else if (room.getNumCustomers() > numCustomers) {
                                        goodRoom = room;
                                    }
                                }
                            }
                        }
                    }
                }

                if (goodRoom == null) {
                    money -= 100;
                    throw new MiExcepcion("[ There isn't any room available. Custome not asigned. You've lost 100$ ]");
                } else {
                    customers.put(dni, new Customer(numDni, numCustomers, servicesWanted));
                    customers.get(dni).setIsInRoom(goodRoom.getNumRoom());
                    System.out.println(Colors.BLUE + "--> assigned " + dni + " to Room " + goodRoom.getNumRoom() + " <--" + Colors.RESET);
                }
            } else {
                throw new MiExcepcion("[ Customer already exists ]");
            }

            //todo: FALTA AÑADIR CLIENTE A UNA HABITACION.

        } catch (NumberFormatException ex) {
            System.out.println(Colors.RED + "[ Wrong dni or number customers ]" + Colors.RESET);
        }
    }

    private static void makeWorker(String dni, String name, String[] skills) throws MiExcepcion {
        try {
            //todo: Falta comprobar el nombre.
            int numDni = Integer.parseInt(dni);
            Set<String> skillsGood = new HashSet<>(Arrays.asList(skills));
            if (workers.get(dni) == null) {
                workers.put(dni, new Worker(numDni, name, skillsGood));
            } else {
                throw new MiExcepcion("[ Worker already exists ]");
            }
        } catch (NumberFormatException ex) {
            System.out.println(Colors.RED + "[ Wrong dni ]" + Colors.RESET);
        }
    }

    private static void makeRoom(String numRoom, String numCustomers, String[] services) throws MiExcepcion {
        int nCustomers = Integer.parseInt(numCustomers);
        Set<String> servicesGood = new HashSet<>(Arrays.asList(services));
        if (rooms.get(numRoom) == null) {
            rooms.put(numRoom, new Room(numRoom, nCustomers, servicesGood));
        } else {
            throw new MiExcepcion("[ Room already exists ]");
        }
    }

    private static void setStatusRoom() {

        // Status from Rooms.
        statusRoom.add("CLEAN");
        statusRoom.add("UNCLEAN");
        statusRoom.add("BROKEN");
        statusRoom.add("RESERVED");
    }


    private static void checkMoney() {
        if (0 >= money) {
            System.out.println(Colors.PURPLE + "================================");
            System.out.println("==> MONEY : " + money + " $  <==");
            System.out.println("================================" + Colors.RESET);
            System.exit(0);
        }
    }
}