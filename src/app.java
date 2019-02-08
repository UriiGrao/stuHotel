import controller.Manager;
import models.*;
import utils.*;

import java.io.*;
import java.util.*;

public class app {

    private static int money;
    // Status de las rooms.
    private static Set<String> statusRoom = new HashSet<>();

    // Create hash from Customers, Workers and Rooms.
    public static HashMap<Integer, Customer> customers = new HashMap<>();
    private static HashMap<Integer, Worker> workers = new HashMap<>();
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
                Manager.checkMoney(money);
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
                        System.out.println(Manager.printBlue("--> new Room added " + lines[1] + " <--"));
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "worker":
                    if (lines.length == 4) {
                        String[] skills = lines[3].toLowerCase().split(",");
                        int dni = Integer.parseInt(lines[1]);
                        makeWorker(dni, lines[2], skills);
                        System.out.println(Manager.printBlue("--> new Worker added " + lines[1] + " <--"));
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "reservation":
                    if (lines.length == 4) {
                        String[] servicesCustomer = lines[3].toLowerCase().split(",");
                        int dni = Integer.parseInt(lines[1]);
                        makeCustomer(dni, lines[2], servicesCustomer);
                        break;
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "hotel":
                    System.out.println(Colors.BLUE + "==> ROOMS <==");
                    for (Room room : rooms.values()) {
                        System.out.println(room.toString());
                    }
                    System.out.println("==> WORKERS <==");
                    for (Worker worker : workers.values()) {
                        System.out.println(worker.toString());
                    }
                    System.out.println(Colors.RESET);
                    break;
                case "problem":
                    if (lines.length == 2) {
                        rooms.get(lines[1]).setStatus("BROKEN");
                        System.out.println(Manager.printBlue("--> Room set as BROKEN <--"));
                        if (rooms.get(lines[1]).getCustomer() != null) {
                            moveCustomer(lines[1]);
                        }
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                    break;
                case "request":
                    if (lines.length == 3) {
                        String[] servicesBroken = lines[2].split(",");
                        setWorker(lines[1], servicesBroken);
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                    break;
                case "finish":
                    if (lines.length == 2) {
                        for (Worker worker : workers.values()) {
                            if (worker.getRoom() != null) {
                                if (worker.getRoom().getNumRoom().equals(lines[1])) {
                                    worker.setRoom(null);
                                }
                            }
                        }
                        System.out.println(Manager.printBlue("--> Services finished in room: " + lines[1]));
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                case "leave":
                    if (lines.length == 2) {
                        Room roomLeave = rooms.get(lines[1]);
                        if (roomLeave.getCustomer() != null) {
                            roomLeave.setStatus("UNCLEAN");
                            //todo: FALLA aaaa
                            Customer customerLeave = roomLeave.getCustomer();
                            if (customerLeave.getServicesRequest().size() == 0) {
                                money += 100;
                            } else {
                                money -= 50;
                            }
                            roomLeave.setCustomer(null);
                        } else {
                            throw new MiExcepcion("[ This room no have reservation ]");
                        }
                    } else {
                        throw new MiExcepcion("[ Wrong number of arguments ]");
                    }
                    break;
                case "money":
                    if (lines.length == 1) {
                        System.out.println(Colors.PURPLE + "================================");
                        System.out.println("==> MONEY : " + money + " $  <==");
                        System.out.println("================================" + Colors.RESET);
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

    private static void setWorker(String numberRoom, String[] services) {
        Room roomBroken = rooms.get(numberRoom);
        Set<String> servicesBroken = new HashSet<>(Arrays.asList(services));
        boolean chivato;

        for (String service : servicesBroken) {
            chivato = false;
            Worker worker = searchWorker(service);
            if (worker != null) {
                worker.setRoom(roomBroken);
                workers.get(worker.getDni()).setRoom(roomBroken);
                System.out.println(Manager.printBlue("--> Worker " + worker.getName() + " assigned to Room " + roomBroken.getNumRoom() + " <--"));
            } else {
                chivato = true;
            }

            if (chivato) {
                //todo: ESTO NO FUNCIONA ABRA QUE PENSAR MEJOR!
                Customer noHappy1 = rooms.get(roomBroken.getNumRoom()).getCustomer();
                System.out.println(noHappy1.getDni());
                noHappy1.setOneServiceRequest(service);
            }
        }
    }

    private static Worker searchWorker(String service) {
        for (Worker worker : workers.values()) {
            if (worker.getRoom() == null) {
                if (worker.getSkills().contains(service)) {
                    return worker;
                }
            }
        }
        return null;
    }

    private static void moveCustomer(String numRoom) throws MiExcepcion {
        Customer moveCustomer = rooms.get(numRoom).getCustomer();
        rooms.get(numRoom).setCustomer(null);
        setCustomerToRoom(moveCustomer);
    }

    private static void setCustomerToRoom(Customer moveCustomer) throws MiExcepcion {
        Room goodRoom = null;
        boolean breac = false;

        for (Room room : rooms.values()) {
            if (!breac) {
                if (room.getStatus().equals("CLEAN")) {
                    if (room.getServices().containsAll(moveCustomer.getServicesWanted())) {
                        if (room.getNumCustomers() == moveCustomer.getNumCustomers()) {
                            goodRoom = room;
                            breac = true;
                        } else {
                            if (room.getNumCustomers() > moveCustomer.getNumCustomers() && goodRoom != null) {
                                if (goodRoom.getNumCustomers() > room.getNumCustomers()) {
                                    goodRoom = room;
                                }
                            } else if (room.getNumCustomers() > moveCustomer.getNumCustomers()) {
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
            customers.get(moveCustomer.getDni());
            rooms.get(goodRoom.getNumRoom()).setStatus("RESERVED");
            rooms.get(goodRoom.getNumRoom()).setCustomer(moveCustomer);
            System.out.println(Manager.printBlue("--> reassigned " + moveCustomer.getDni() + " to Room " + goodRoom.getNumRoom() + " <--"));
        }
    }

    private static void makeCustomer(int dni, String nCustomers, String[] services) throws MiExcepcion {
        try {
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
                                        if (goodRoom.getNumCustomers() > room.getNumCustomers()) {
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
                    Customer nCustomer = new Customer(dni, numCustomers, servicesWanted);
                    customers.put(dni, nCustomer);
                    rooms.get(goodRoom.getNumRoom()).setStatus("RESERVED");
                    rooms.get(goodRoom.getNumRoom()).setCustomer(nCustomer);
                    System.out.println(Manager.printBlue("--> assigned " + dni + " to Room " + goodRoom.getNumRoom() + " <--"));
                }
            } else {
                throw new MiExcepcion("[ Customer already exists ]");
            }
        } catch (NumberFormatException ex) {
            System.out.println(Colors.RED + "[ Wrong dni or number customers ]" + Colors.RESET);
        }
    }

    private static void makeWorker(int dni, String name, String[] skills) throws MiExcepcion {
        try {
            Set<String> skillsGood = new HashSet<>(Arrays.asList(skills));
            if (workers.get(dni) == null) {
                workers.put(dni, new Worker(dni, name, skillsGood));
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


}