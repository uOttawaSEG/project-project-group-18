package com.example.seg_project_d11;

public class Organizer extends User{

    private String organizationName;

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
}
