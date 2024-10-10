package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Attendee_2 extends AppCompatActivity {
    Button goBack, submit;
    TextView attendName, attendEmail, attendPassword, attendConfirmPassword;
    String attendeeName, attendeeEmail, attendeePassword, attendeeConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goBack = findViewById(R.id.backButton_o2);
        submit= findViewById(R.id.submitButton);

        //Associates each text field to TextView variable
        attendName= findViewById(R.id.attendeeName);
        attendEmail= findViewById(R.id.attendeeEmail);
        attendPassword= findViewById(R.id.attendeePassword);
        attendConfirmPassword= findViewById(R.id.attendeeConfirmPassword);

        //Stores the data using textview variables
        attendeeName = attendName.getText().toString();
        attendeeEmail = attendEmail.getText().toString();
        attendeePassword = attendPassword.getText().toString();
        attendeeConfirmPassword= attendConfirmPassword.getText().toString();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendee_2.this, Attendee_1.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            //  @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendee_2.this, Welcome_Organizer.class);
                intent.putExtra("attendeeEmail", attendeeEmail); //gives the organizer username to welcome screen
                startActivity(intent);
            }
        });
    }
}