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

public class Attendee_1 extends AppCompatActivity {

    Button goBack, goNext;
    TextView attendeeName, attendeeLastName, attendeePhone, attendeeAddress;
    String attenName, attenPhone, attenLastName, attenAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goBack = findViewById(R.id.backButton_01);
        goNext = findViewById(R.id.nextButton);

        //Associates each text field to TextView variable
        attendeeName = findViewById(R.id.attendeeName);
        attendeeLastName = findViewById(R.id.attendeeLastName);
        attendeePhone = findViewById(R.id.attendeePhone);
        attendeeAddress = findViewById(R.id.attendeeAddress);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendee_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });

        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stores the data using textview variables
                attenName = attendeeName.getText().toString();
                attenLastName = attendeeLastName.getText().toString();
                attenPhone = attendeePhone.getText().toString();
                attenAddress = attendeeAddress.getText().toString();

                //Makes sure the changes above stay--DOES NOT WORK
                attendeeName.setText(attenName);
                attendeeLastName.setText(attenLastName);
                attendeePhone.setText(attenPhone);
                attendeeAddress.setText(attenAddress);

                Intent intent = new Intent(Attendee_1.this, Attendee_2.class);
                startActivity(intent);
            }
        });
    }
}