package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Organizer_2 extends AppCompatActivity {
    //go back and submit buttons
    Button goBack;
    Button submit;
    DatabaseHelper databaseHelper;

    //User input fields
    TextView organizationName, orgEmail, orgPassword, orgConfirmPassword;

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

        //initializes the Back and submit buttons
        submit= findViewById(R.id.submitButton);
        goBack = findViewById(R.id.backButton_o2);

        //reference to the databaseHelper
        databaseHelper = new DatabaseHelper(this);

        //Associates each textField to TextView variable
        organizationName= findViewById(R.id.organizationName);
        orgEmail= findViewById(R.id.organizerEmail);
        orgPassword= findViewById(R.id.organizerPassword);
        orgConfirmPassword= findViewById(R.id.organizerConfirmPassword);

        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        organizationName.setText(Organizer_1.user.getOrganizationName());
        orgEmail.setText(Organizer_1.user.getEmail());
        orgPassword.setText(Organizer_1.user.getPassword());


        //On click activity for Back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the data of this page into the viewModel class's static variables
                Organizer_1.user.setOrganizationName(organizationName.getText().toString().trim());
                Organizer_1.user.setEmail(orgEmail.getText().toString().trim());
                Organizer_1.user.setPassword(orgPassword.getText().toString().trim());

                //goes back to Organizer_1 activity
                Intent intent = new Intent(Organizer_2.this, Organizer_1.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                String email = orgEmail.getText().toString().trim();
                String password = orgPassword.getText().toString().trim();
                String confirmPassword = orgConfirmPassword.getText().toString().trim();

                if (!UserValidator.validateEmail(email)){
                    orgEmail.setError("Invalid email! email must contain @ and dot.");
                    allValid = false;
                }

                if (!UserValidator.validatePassword(password)){
                    orgPassword.setError("Invalid password! password must contain at least one letter and one number.");
                    allValid = false;
                }

                if (!password.equals(confirmPassword)){
                    orgConfirmPassword.setError("Passwords must match!");
                    allValid = false;
                }
                if (allValid){
                    //Stores the data of this page into the viewModel class's static variables
                    Organizer_1.user.setOrganizationName(organizationName.getText().toString().trim());
                    Organizer_1.user.setEmail(email);
                    Organizer_1.user.setPassword(password);
                    Organizer_1.user.setStatus("Pending");
                    //Moves info to DB
                    boolean success = databaseHelper.addUser(Organizer_1.user);
                    Intent intent = new Intent(Organizer_2.this, WelcomePage.class);
                    intent.putExtra("user_name", Organizer_1.user.getEmail());
                    intent.putExtra("user_role", "organizer");
                    Organizer_1.user= new Organizer(null, null,null, null, null, null, null, null, null);
                    startActivity(intent);
                }else{
                    //show a message to the user about fixing the errors
                    Toast.makeText(Organizer_2.this, "Please correct the errors before proceeding.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}