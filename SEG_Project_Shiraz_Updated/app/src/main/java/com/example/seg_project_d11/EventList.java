package com.example.seg_project_d11;

import android.annotation.SuppressLint;
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

public class EventList extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listOfEvents;

    private ArrayList<Event> events;

    private EventAdapter adapter;
    Button backButton;

    //get the role and userName from the previous intent
    String username = getIntent().getStringExtra("user_name");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //fetch pending requests from database
        databaseHelper = new DatabaseHelper(this);
        events = databaseHelper.getEventList(username);


        //debugging
        Log.d("EventList", "Number of events: " + events.size());

        //assign value to listOfRequests
        listOfEvents = findViewById(R.id.ivPendingListEvents);


        //setting the adapter
        adapter = new EventAdapter(this, events, databaseHelper);
        listOfEvents.setAdapter(adapter);

        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent(EventList.this, WelcomePage.class);
                startActivity(intent);
            }
        });
    }
}
