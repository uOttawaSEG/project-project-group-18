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
import java.util.List;
//show all events to attendee
public class AttendeeUpcomingEvent_Activity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView lvUpcomingEvents;
    List<Event> upcomingEventsList;
    EventAdapter adapter;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_upcomingevents);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //get the userName and role from the previous intent
        String username = getIntent().getStringExtra("user_name");
        String userRole = getIntent().getStringExtra("user_role");

        dbHelper = new DatabaseHelper(this);
        upcomingEventsList = dbHelper.getUpcomingEvents(username); // enah: why isn't this working?

        lvUpcomingEvents= findViewById(R.id.lvUpcomingEvents);

        //setting the adapter
        adapter = new EventAdapter(this, upcomingEventsList, dbHelper, userRole, username);
        lvUpcomingEvents.setAdapter(adapter);

        backButton = findViewById(R.id.button_goBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(AttendeeUpcomingEvent_Activity.this, WelcomePage.class);
                intent.putExtra("user_name",username);
                intent.putExtra("user_role", dbHelper.getUserRole(username));
                startActivity(intent);
            }
        });




    }



}