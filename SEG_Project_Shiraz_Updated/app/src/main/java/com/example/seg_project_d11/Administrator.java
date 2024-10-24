package com.example.seg_project_d11;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends AppCompatActivity {

    //using a private static instance of the Administrator

    private static final String ADMIN_USERNAME = "admin@example.com";
    private static final String ADMIN_PASSWORD = "password";

    private Administrator(){}

    //method to approve the registration request from attendee or organizer
    public static void approveRequest(User user, DatabaseHelper databaseHelper){
        user.setStatus("Approved");
        databaseHelper.updateUserStatus(user); //update the status in the database
    }

    //method to reject the registration request
    public static void rejectRequest(User user, DatabaseHelper databaseHelper){
        user.setStatus("Rejected");
        databaseHelper.updateUserStatus(user);

    }


}
