package models;

import utils.Colors;
import utils.MiExcepcion;

import java.util.*;

public class Room {
    private static Set<String> servicesRooms = new HashSet<>();

    private String numRoom;
    private int numCustomers;
    private Set<String> services;
    private String status;
    private Customer customer;

    public Room(String numRoom, int numCustomers, Set<String> services) throws MiExcepcion {
        setServicesDefault();
        this.numRoom = numRoom;
        this.numCustomers = numCustomers;
        setServices(services);
        this.status = "CLEAN";
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private void setServices(Set<String> services) throws MiExcepcion {
        if (servicesRooms.containsAll(services)){
            this.services = services;
        } else {
            throw new MiExcepcion("[ Wrong service ]");
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

    private void setServicesDefault() {
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

    @Override
    public String toString() {
        if (customer == null) {
            return "== " + getClass().getSimpleName().toUpperCase() + " " + getNumRoom() + " " + getStatus() + " ==";
        } else {
            return "== " + getClass().getSimpleName().toUpperCase() + " " + getNumRoom() + " " + getCustomer().getClass().getSimpleName().toUpperCase() + ":"+ getCustomer().getDni() + "(" + getCustomer().getNumCustomers() + ")" + " ==";
        }
    }
}