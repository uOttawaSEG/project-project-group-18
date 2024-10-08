package com.example.seg_project_d11;
import androidx.lifecycle.ViewModel;

public class OrganizationViewModel extends ViewModel{
    private String organizerName, organizerPhone, organizerLastName, organizerAddress, organizationName, organizerEmail, organizerPassword;

    public void setOrganizerName(String name){
        this.organizerName = name;
    }
    public String getOrganizerName(){
        return organizerName;
    }

    public void setOrganizerPhone(String phone){
        this.organizerPhone = phone;
    }
    public String getOrganizerPhone(){
        return organizerPhone;
    }

    public void setOrganizerLastName(String lastName){
        this.organizerLastName = lastName;
    }
    public String getOrganizerLastName(){
        return organizerLastName;
    }

    public void setOrganizerAddress(String address){
        this.organizerAddress = address;
    }
    public String getOrganizerAddress(){
        return organizerAddress;
    }

    public void setOrganizationName(String orgName){
        this.organizationName = orgName;
    }
    public String getOrganizationName(){
        return organizationName;
    }

    public void setOrganizerEmail(String email){
        this.organizerEmail = email;
    }
    public String getOrganizerEmail(){
        return organizerEmail;
    }

    public void setOrganizerPassword(String password){
        this.organizerPassword = password;
    }
    public String getOrganizerPassword(){
        return organizerPassword;
    }

}
