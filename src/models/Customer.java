package models;

import java.util.*;

public class Customer {
    private int dni;
    private int numCustomers;
    private Set<String> servicesWanted;
    private Set<String> servicesRequest;

    public Customer(int dni, int numCustomers, Set<String> servicesWanted) {
        this.dni = dni;
        this.numCustomers = numCustomers;
        this.servicesWanted = servicesWanted;
    }
    public void setOneServiceRequest(String service) {
        this.servicesRequest.add(service);
    }
    public Set<String> getServicesRequest() {
        return servicesRequest;
    }

    public void setServicesRequest(Set<String> servicesRequest) {
        this.servicesRequest = servicesRequest;
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

}
