package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateEvent extends AppCompatActivity {
    TextView newTitle, newDescription, newDate, newStartTime, newEndTime, newEventAddress;
    String title, description, date, startTime, endTime, eventAddress;
    Button cancelEvent;
    Button createEvent;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cancelEvent= findViewById(R.id.buttonCreate);
        createEvent = findViewById(R.id.buttonCreate);
        databaseHelper = new DatabaseHelper(this);

        //Associates each textField to TextView variable
        newTitle= findViewById(R.id.eventTitle);
        newDescription= findViewById(R.id.eventDesc);
        newDate= findViewById(R.id.eventDate);
        newStartTime= findViewById(R.id.eventStart);
        newEndTime= findViewById(R.id.eventEnd);
        newEventAddress= findViewById(R.id.eventAddress);

        //On click activity for cancel button
        cancelEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goes back to Organizer homepage
                Intent intent = new Intent(CreateEvent.this, WelcomePage.class);
                startActivity(intent);
            }
        });
        //On click activity for create button
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String organizerUserName = getIntent().getStringExtra("user_name");
                title= newTitle.getText().toString().trim();
                description= newDescription.getText().toString().trim();
                date= newDate.getText().toString().trim();
                startTime= newStartTime.getText().toString().trim();
                endTime= newEndTime.getText().toString().trim();
                eventAddress= newEventAddress.getText().toString().trim();
                Event event = new Event(title, description, date, startTime, endTime, eventAddress);
                databaseHelper.updateEventList(organizerUserName, event);

                AlertDialog.Builder message = new AlertDialog.Builder(CreateEvent.this);
                message.setCancelable(true);
                message.setTitle("Event created");
                message.setMessage("The event had been successfully created, please go back to the home page.");

                // Ok/Redirect  Button
                message.setPositiveButton("Return to main", (d,i) -> {
                    Intent intent = new Intent(CreateEvent.this, WelcomePage.class);
                    startActivity(intent);
                });
                message.show();

            }
        });
    }
}