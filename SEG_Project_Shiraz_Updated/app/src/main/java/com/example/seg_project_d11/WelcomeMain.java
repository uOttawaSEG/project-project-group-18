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
                OrganizationViewModel.attendeeName = null;
                OrganizationViewModel.attendeeLastName = null;
                OrganizationViewModel.attendeePhone = null;
                OrganizationViewModel.attendeeAddress = null;
                OrganizationViewModel.attendeeEmail = null;
                OrganizationViewModel.attendeePassword = null;

                //clears user information from the machine if organizer
                OrganizationViewModel.organizerName = null;
                OrganizationViewModel.organizerLastName = null;
                OrganizationViewModel.organizerPhone = null;
                OrganizationViewModel.organizerAddress = null;
                OrganizationViewModel.organizationName = null;
                OrganizationViewModel.organizerEmail = null;
                OrganizationViewModel.organizerPassword = null;
            }
        });

    }}

