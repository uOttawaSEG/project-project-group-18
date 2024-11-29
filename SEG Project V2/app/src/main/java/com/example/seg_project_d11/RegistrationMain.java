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

public class RegistrationMain extends AppCompatActivity {

    Button goToOrgReg;
    Button goToAttendeeReg;
    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Assigns button to variable
        goToOrgReg = findViewById(R.id.amOrganizer);
        goToAttendeeReg = findViewById(R.id.amAttendee);

        //On button click, open activity Organization_1
        goToOrgReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationMain.this, Organizer_1.class);
                startActivity(intent);
            }
        });

        //On button click, open activity Attendee_1
        goToAttendeeReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                    Intent intent = new Intent(RegistrationMain.this, Attendee_1.class);
                    startActivity(intent);
            }

        });





    }
}