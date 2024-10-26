package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectionActivity extends AppCompatActivity {
    Button signAttendee;
    Button signOrganizer;
    Button signAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signAttendee = findViewById(R.id.buttonAttendee);
        signOrganizer = findViewById(R.id.buttonOrganizer);
        signAdmin = findViewById(R.id.buttonAdministrator);

        signAttendee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectionActivity.this, SignInPage.class);
                startActivity(intent);
            }
        });

        signOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectionActivity.this, SignInPage.class);
                startActivity(intent);
            }
        });

        signAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectionActivity.this,Admin_SignIn_Activity.class);
                startActivity(intent);

            }
        });

    }

}