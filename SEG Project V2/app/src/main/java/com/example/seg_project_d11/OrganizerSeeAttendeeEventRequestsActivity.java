package com.example.seg_project_d11;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
//shows the list of attendees
public class OrganizerSeeAttendeeEventRequestsActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView listViewAttendeeRequests;
    List<Attendee> attendees;
    AttendeeAdapter adapter;
    Button backButton;
    Button approveAll;

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


        //get the userName and role from the previous intent
        String username = getIntent().getStringExtra("user_name");
        String userRole = getIntent().getStringExtra("user_role");
        Integer eventID = getIntent().getIntExtra("eventID", -1); // Default value is -1

        dbHelper = new DatabaseHelper(this);
        attendees = dbHelper.getAllAttendeeEventRequests(eventID, "Pending");

        Log.d("OrganizerSeeEventRequest", "eventID: "+eventID);


        listViewAttendeeRequests= findViewById(R.id.lvAttendeeRequests);

        //setting the adapter
        adapter = new AttendeeAdapter(this, attendees, dbHelper, eventID);
        listViewAttendeeRequests.setAdapter(adapter);


        backButton = findViewById(R.id.button_goBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(OrganizerSeeAttendeeEventRequestsActivity.this, WelcomePage.class);
                intent.putExtra("user_name", username);
                intent.putExtra("user_role", userRole);
                startActivity(intent);
            }
        });

        // Button initialization
        Button approveAll = findViewById(R.id.button_approveAll);

        approveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First, approve all attendees in bulk using the existing method
                dbHelper.approveAllAttendees(eventID);

                // Clear and refresh the attendee list
                attendees.clear();
                adapter.notifyDataSetChanged();

                // Optional: Show success message to user
                Toast.makeText(adapter.getContext(), "All attendees approved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}