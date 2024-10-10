package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome_Organizer extends AppCompatActivity {

    TextView userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_screen_organizer);

        userName = findViewById(R.id.userNameDisplay);

        Intent intent = getIntent();
        String organizerEmail = intent.getStringExtra("organizerEmail");

        if (organizerEmail!=null){
            userName.setText(organizerEmail);
        } else{
            userName.setText("No User Name Found");
        }

    }
}