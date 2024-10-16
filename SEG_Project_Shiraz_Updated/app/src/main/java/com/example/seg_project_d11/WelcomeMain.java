package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeMain extends AppCompatActivity {

    Button logOut;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_attendee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
                Intent intentLogout = new Intent(WelcomeMain.this, MainActivity.class);
                startActivity(intentLogout);
            }
        });

    }}

