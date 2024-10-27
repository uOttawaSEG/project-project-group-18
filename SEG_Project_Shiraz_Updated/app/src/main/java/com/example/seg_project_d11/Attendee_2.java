package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;



public class Attendee_2 extends AppCompatActivity {

    //go back and submit buttons
    Button goBack;
    Button submit;
    DatabaseHelper databaseHelper;

    //User input fields
    TextView attendEmail, attendPassword, attendConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initializes the Back and submit buttons
        submit= findViewById(R.id.submitButton);
        goBack = findViewById(R.id.backButton_A2);

        //reference to the databaseHelper
        databaseHelper = new DatabaseHelper(this);

        //Associates each textField to TextView variable
        attendEmail= findViewById(R.id.attendeeEmail);
        attendPassword= findViewById(R.id.attendeePassword);
        attendConfirmPassword= findViewById(R.id.attendeeConfirmPassword);

        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        attendEmail.setText(Attendee_1.user.getEmail());
        attendPassword.setText(Attendee_1.user.getPassword());

        //On click activity for Back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the data of this page into the viewModel class's static variables
                Attendee_1.user.setEmail(attendEmail.getText().toString().trim());
                Attendee_1.user.setPassword(attendPassword.getText().toString().trim());

                //goes back to Attendee_1 activity
                Intent intent = new Intent(Attendee_2.this, Attendee_1.class);
                startActivity(intent);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String attendeeEmail = attendEmail.getText().toString().trim();
                String attendeePassword = attendPassword.getText().toString().trim();
                String attendeeConfirmPassword = attendConfirmPassword.getText().toString().trim();

                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                if (!UserValidator.validateEmail(attendeeEmail)){
                    attendEmail.setError("Invalid email! email must contain at least one @ and dot.");
                    allValid = false;
                }

                if (!UserValidator.validatePassword(attendeePassword)){
                    attendPassword.setError("Invalid password! password must contain at lease one letter and one number.");
                    allValid = false;
                }
                if (!attendeePassword.equals(attendeeConfirmPassword)){
                    attendConfirmPassword.setError("Passwords must match!");
                    allValid = false;
                }


                if (allValid){
                    //creating attendee object
                    //Stores the data of this page into the viewModel class's static variables
                    Attendee_1.user.setEmail(attendeeEmail);
                    Attendee_1.user.setPassword(attendeePassword);
                    Attendee_1.user.setStatus("Pending");
                    //Moves info to DB
                    boolean success = databaseHelper.addUser(Attendee_1.user);
                    Toast.makeText(Attendee_2.this, "Success = " + success, Toast.LENGTH_SHORT).show();

                    Intent intent1= new Intent(Attendee_2.this, WelcomePage.class);
                    intent1.putExtra("user_name", attendeeEmail);
                    intent1.putExtra("user_role", "Attendee");
                    Attendee_1.user =new Attendee(null,null, null, null, null, null, null,null);

                    startActivity(intent1);

                }else{
                    //show a message to the user about fixing the errors
                    Toast.makeText(Attendee_2.this, "Please correct the errors before proceeding.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}