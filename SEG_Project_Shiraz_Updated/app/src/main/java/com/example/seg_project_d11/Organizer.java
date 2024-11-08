package com.example.seg_project_d11;

import java.util.ArrayList;

public class Organizer extends User{

    private String organizationName;
    private ArrayList<Event> events = new ArrayList<Event>();

    public Organizer(String firstName, String lastName, String email, String password, String phoneNumber, String address, String organizationName, String status, String userRole) {
        super(firstName, lastName, email, password, phoneNumber, address, status, userRole);
        this.organizationName = organizationName;
    }

    //getter and setter for organizationName attribute


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    public ArrayList<Event> getEventList() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
