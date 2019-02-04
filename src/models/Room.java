package models;

import utils.Colors;
import utils.MiExcepcion;

import java.util.*;

public class Room {
    private static  Set<String> servicesRooms = new HashSet<>();

    private String numRoom;
    private int numCustomers;
    private Set<String> services;
    private String status;

    public Room(String numRoom, int numCustomers, Set<String> services) {
        setServicesDefault();
        this.numRoom = numRoom;
        this.numCustomers = numCustomers;
        setServices(services);
        this.status = "CLEAN";
    }

    private void setServices(Set<String> services) {
        try {
            int cont = 0;
            for (String service : services) {
                for (String serviceRooms : servicesRooms) {
                    if (service.equals(serviceRooms)) {
                        cont++;
                    }
                }
            }
            if (cont == services.size()) {
                this.services = services;
            } else {
                throw new MiExcepcion( Colors.RED + "[ Wrong service ]" + Colors.RESET);
            }
        } catch (MiExcepcion mx) {
            System.out.println(mx.getMessage());
        }
    }

    public String getNumRoom() {
        return numRoom;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public Set<String> getServices() {
        return services;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void setServicesDefault(){
        // Services from Rooms.
        servicesRooms.add("tv");
        servicesRooms.add("balcon");
        servicesRooms.add("camadoble");
        servicesRooms.add("jacuzzi");
        servicesRooms.add("minibar");
        servicesRooms.add("telefono");
        servicesRooms.add("satelite");
        servicesRooms.add("sweet");
    }
}
