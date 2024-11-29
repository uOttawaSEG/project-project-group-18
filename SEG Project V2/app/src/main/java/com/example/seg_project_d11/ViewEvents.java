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

import java.util.List;
//organizer can see events they create
public class ViewEvents extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listOfEvents;
    List<Event> events, pastEvents;
    EventAdapter adapter;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Checkpoint", "EventList2 created");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_list2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //get the role and userName from the previous intent
        String username = getIntent().getStringExtra("user_name");
        String userRole = getIntent().getStringExtra("user_role");
        String type = getIntent().getStringExtra("type");


        Log.d("EventList2", "User_name: "+ username);
        Log.d("EventList2", "User_role: "+ userRole);
        Log.d("EventList2", "Type: "+ type);

        databaseHelper = new DatabaseHelper(this);

        if(type.equals("Upcoming") || type.equals("Past")){
            events = databaseHelper.getEventsForOrganizer(username, type);
        }else{
            events = databaseHelper.getAvailableEvents();
        }

        //assign value to listOfRequests
        listOfEvents = findViewById(R.id.lvPendingListEvents);


        //setting the adapter
        adapter = new EventAdapter(this, events, databaseHelper, userRole, username);
        listOfEvents.setAdapter(adapter);

        backButton = findViewById(R.id.button_goBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(ViewEvents.this, WelcomePage.class);
                intent.putExtra("user_name",username);
                intent.putExtra("user_role", databaseHelper.getUserRole(username));
                startActivity(intent);
            }
        });

    }
}