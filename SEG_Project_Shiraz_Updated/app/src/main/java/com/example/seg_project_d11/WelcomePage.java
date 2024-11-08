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

    Button logOut, createEvent, viewEvents;

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



        TextView welcomeRoleText = findViewById(R.id.welcomeRoleText);
        TextView userNameDisplay = findViewById(R.id.userNameDisplay);
        TextView userRoleDisplay = findViewById(R.id.userRoleDisplay);

        // Set the user name and role
        userNameDisplay.setText(userName);

        if (role.equals("Organizer")) {
            welcomeRoleText.setText("Welcome, organizer!");
            userRoleDisplay.setText("Organizer");
        } else if (role.equals("Attendee")) {
            welcomeRoleText.setText("Welcome, attendee!");
            userRoleDisplay.setText("Attendee");
        }else{
            welcomeRoleText.setText("Welcome, guest!");
            userRoleDisplay.setText("Guest");
        }

        logOut = findViewById(R.id.logOut);
        createEvent = findViewById(R.id.createEvent_button);
        viewEvents = findViewById(R.id.seeEvents_button);

        logOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //clears user information from the machine if attendee
                Intent intentLogout = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(intentLogout);
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(WelcomePage.this, CreateEvent.class);
                intent.putExtra("user_name", userName);
                startActivity(intent);
            }
        });

        viewEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent new_intent = new Intent(WelcomePage.this, Event_list_2.class);
                new_intent.putExtra("user_name", userName);
                Log.i("UserName", userName);
                Log.i("Checkpoint", "onCLick-viewEvents");
                startActivity(new_intent);
            }
        });
    }
}