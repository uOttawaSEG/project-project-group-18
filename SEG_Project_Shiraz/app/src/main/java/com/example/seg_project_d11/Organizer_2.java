package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Organizer_2 extends AppCompatActivity {
    Button goBack; //submit
    TextView orgName, orgEmail, orgPassword, orgConfirmPassword;
    String organizationName, organizerEmail, organizerPassword, organizerConfirmPassword;
    //ActivityResultLauncher<Intent> Organizer_1Launcher; //homepageLauncher
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        goBack = findViewById(R.id.backButton_o2);
        //submit= findViewById(R.id.submit);

        //Associates each text field to TextView variable
        orgName= findViewById(R.id.organizationName);
        orgEmail= findViewById(R.id.organizerEmail);
        orgPassword= findViewById(R.id.organizerPassword);
        orgConfirmPassword= findViewById(R.id.organizerConfirmPassword);

        //Stores the data using textview variables
        organizationName = orgName.getText().toString();
        organizerEmail = orgEmail.getText().toString();
        organizerPassword = orgPassword.getText().toString();
        organizerConfirmPassword = orgConfirmPassword.getText().toString();

        /*//For goBack
        Organizer_1Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );*/
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Organizer_2.this, Organizer_1.class);
                startActivity(intent);
            }
        });

        /* //Implement with team
        submit = findViewById(R.id.submitButton);
        //For submit
        homepageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Organizer_2.this, Homepage.class);
                homepageLauncher.launch(intent);
            }
        });
         */
    }
}