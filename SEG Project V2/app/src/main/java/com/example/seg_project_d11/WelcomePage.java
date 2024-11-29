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

public class WelcomePage extends AppCompatActivity {

    Button logOut, createEvent, viewEvents, viewPastEvents, viewUpcomingEvents,viewMyEvents, searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get the role and userName from the previous intent
        String userName = getIntent().getStringExtra("user_name");
        String role = getIntent().getStringExtra("user_role");

        //for debugging purpose
        Log.d("WelcomePage", "User role received: " + role);
        Log.d("WelcomePage", "User name received: " + userName);


        TextView welcomeRoleText = findViewById(R.id.welcomeRoleText);
        TextView userNameDisplay = findViewById(R.id.userNameDisplay);
        TextView userRoleDisplay = findViewById(R.id.userRoleDisplay);

        // Set the user name and role
        userNameDisplay.setText(userName);

//initialize views
        logOut = findViewById(R.id.logOut);
        createEvent = findViewById(R.id.createEvent_button);
        viewEvents = findViewById(R.id.seeEvents_button);
        viewPastEvents = findViewById(R.id.pastEventsButton);
        viewUpcomingEvents = findViewById(R.id.upcomingEventsButton);
        viewMyEvents = findViewById(R.id.attendeeEventsButton);
        searchButton = findViewById(R.id.searchButton);

        if (role.equals("Organizer")) {
            welcomeRoleText.setText("Welcome, organizer!");
            userRoleDisplay.setText("Organizer");
            createEvent.setVisibility(View.VISIBLE);
            viewPastEvents.setVisibility(View.VISIBLE);
            viewUpcomingEvents.setVisibility(View.VISIBLE);

        } else if (role.equals("Attendee")) {
            welcomeRoleText.setText("Welcome, attendee!");
            userRoleDisplay.setText("Attendee");
            viewUpcomingEvents.setVisibility(View.VISIBLE);
            viewMyEvents.setVisibility(View.VISIBLE);
            searchButton.setVisibility(View.VISIBLE);
        }else{
            welcomeRoleText.setText("Welcome, guest!");
            userRoleDisplay.setText("Guest");
        }

        logOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //clears user information from the machine if attendee
                Intent intentLogout = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(intentLogout);
            }
        });
        //if userrole is organizer, display this button
        createEvent.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(WelcomePage.this, CreateEvent.class);
                intent.putExtra("user_name", userName);
                startActivity(intent);
            }
        });
        //if userrole is organizer, display this button
        viewUpcomingEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(WelcomePage.this, See_Events_Organizer.class);
                new_intent.putExtra("user_name", userName);
                new_intent.putExtra("type","Upcoming");
                new_intent.putExtra("user_role",role);

                startActivity(new_intent);
            }
        });

        //if userrole is organizer, display this button
        viewPastEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(WelcomePage.this, See_Events_Organizer.class);
                new_intent.putExtra("user_name", userName);
                new_intent.putExtra("type","Past");
                new_intent.putExtra("user_role",role);

                startActivity(new_intent);
            }
        });
        //if userrole is Attendee, display this button
        viewEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(WelcomePage.this, See_Events_Organizer.class);
                new_intent.putExtra("user_name", userName);
                new_intent.putExtra("user_role",role);

                Log.i("WelcomePage","UserName:"+userName);
                Log.i("WelcomePage","UserRole:"+ role);
                Log.i("WelcomePage", "onCLick-viewEvents");
                startActivity(new_intent);
            }
        });
        //if userrole is Attendee, display this button
        viewMyEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(WelcomePage.this, Event_list_Attendee.class);
                new_intent.putExtra("user_name", userName);
                new_intent.putExtra("user_role", role);

                Log.i("WelcomePage", "onCLick-viewMyEvents");

                startActivity(new_intent);
            }
        });
//change this
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //clears user information from the machine if attendee
                Intent intentLogout = new Intent(WelcomePage.this, SearchResultsActivity.class);
                intentLogout.putExtra("user_name", userName);
                intentLogout.putExtra("user_role", role);
                startActivity(intentLogout);
                startActivity(intentLogout);
            }
        });
    }
}