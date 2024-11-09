package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OrganizerSeeAttendeeEventRequestsActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView listViewAttendeeRequests;
    ArrayList<Event> AttendeeEventRequests;
    EventAdapter adapter;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_see_attendee_event_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);


        listViewAttendeeRequests= findViewById(R.id.lvAttendeeRequests);

        //setting the adapter


        backButton = findViewById(R.id.button_goBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(OrganizerSeeAttendeeEventRequestsActivity.this, WelcomePage.class);
                startActivity(intent);
            }
        });
    }
}