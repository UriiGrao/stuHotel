package models;

import java.util.*;

public class Customer {
    private int dni;
    private int numCustomers;
    private Set<String> servicesWanted;
    private String isInRoom;

    public Customer(int dni, int numCustomers, Set<String> servicesWanted) {
        this.dni = dni;
        this.numCustomers = numCustomers;
        this.servicesWanted = servicesWanted;
        this.isInRoom = null;
    }

    public int getDni() {
        return dni;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public Set<String> getServicesWanted() {
        return servicesWanted;
    }

    public String getIsInRoom() {
        return isInRoom;
    }

    public void setIsInRoom(String isInRoom) {
        this.isInRoom = isInRoom;
    }
}
