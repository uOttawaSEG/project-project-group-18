package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
//attendees can see events they requested
public class Event_list_Attendee extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listOfEvents;
    List<Event> events;
    EventAdapter_Attendee adapter;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Checkpoint", "EventListAttendee created");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_list_attendee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //get the role and userName from the previous intent
        String username = getIntent().getStringExtra("user_name");
        String userRole = getIntent().getStringExtra("user_role");

        databaseHelper = new DatabaseHelper(this);
        events = databaseHelper.getAttendeeEvents(username);

        //assign value to listOfEvents
        listOfEvents = findViewById(R.id.lvMyEvents);

        //setting the adapter
        adapter = new EventAdapter_Attendee(this, events, databaseHelper, userRole, username);
        listOfEvents.setAdapter(adapter);

        Log.d("Event_list_Attendee", "User_name: "+ username);
        Log.d("Event_list_Attendee", "User_role: "+ userRole);

        //debugging
        Log.d("EventList", "Number of events: " + events.size());

        backButton = findViewById(R.id.button_goBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Event_list_Attendee.this, WelcomePage.class);
                intent.putExtra("user_name",username);
                intent.putExtra("user_role", databaseHelper.getUserRole(username));
                startActivity(intent);
            }
        });



    }
}