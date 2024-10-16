package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class WelcomePage extends AppCompatActivity {

    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
/*
        try {
            Scanner myReader = new Scanner(userInfo);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Log.d("File contents", data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            Log.d("FILE ERROR","An error occurred.");
        }*/

        //get the role and userName from the previous intent
        String role = getIntent().getStringExtra("user_role");
        //for debugging purpose
        Log.d("WelcomePage", "User role received: " + role);

        String userName = getIntent().getStringExtra("user_name");

        TextView welcomeRoleText = findViewById(R.id.welcomeRoleText);
        TextView userNameDisplay = findViewById(R.id.userNameDisplay);
        TextView userRoleDisplay = findViewById(R.id.userRoleDisplay);

        // Set the user name and role
        userNameDisplay.setText(userName);

        if (role.equals("organizer")) {
            welcomeRoleText.setText("Welcome, Organizer!");
            userRoleDisplay.setText("Organizer");
        } else {
            welcomeRoleText.setText("Welcome, Attendee!");
            userRoleDisplay.setText("Attendee");
        }

        Button logOut = findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //clears user information from the machine if attendee
                AccountsViewModel.attendeeName = null;
                AccountsViewModel.attendeeLastName = null;
                AccountsViewModel.attendeePhone = null;
                AccountsViewModel.attendeeAddress = null;
                AccountsViewModel.attendeeEmail = null;
                AccountsViewModel.attendeePassword = null;

                //clears user information from the machine if organizer
                AccountsViewModel.organizerName = null;
                AccountsViewModel.organizerLastName = null;
                AccountsViewModel.organizerPhone = null;
                AccountsViewModel.organizerAddress = null;
                AccountsViewModel.organizationName = null;
                AccountsViewModel.organizerEmail = null;
                AccountsViewModel.organizerPassword = null;
                Intent intentLogout = new Intent(WelcomePage.this, RegistrationMain.class);
                startActivity(intentLogout);
            }
        });
    }
}