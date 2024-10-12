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

public class Attendee_1 extends AppCompatActivity {

    //Back and next buttons
    Button goBack, goNext;
    //User input fields
    TextView attendName, attendLastName, attendPhone, attendAddress;
    //ViewModel initialization, hold user information in static variables
    OrganizationViewModel organizationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //creates the view model
        organizationViewModel = new ViewModelProvider(this).get(OrganizationViewModel.class);
        //Associates each button to variable
        goBack = findViewById(R.id.backButton_A1);
        goNext = findViewById(R.id.nextButton);

        //Associates each text field to TextView variable
        attendName = findViewById(R.id.attendeeName);
        attendLastName = findViewById(R.id.attendeeLastName);
        attendPhone = findViewById(R.id.attendeePhone);
        attendAddress = findViewById(R.id.attendeeAddress);


        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        attendName.setText(OrganizationViewModel.attendeeName);
        attendLastName.setText(OrganizationViewModel.attendeeLastName);
        attendPhone.setText(OrganizationViewModel.attendeePhone);
        attendAddress.setText(OrganizationViewModel.attendeeAddress);


        //On click activity for back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resets the user info inputs since, thereby cancelling registration
                OrganizationViewModel.attendeeName = null;
                OrganizationViewModel.attendeeLastName = null;
                OrganizationViewModel.attendeePhone = null;
                OrganizationViewModel.attendeeAddress = null;
                OrganizationViewModel.attendeeEmail = null;
                OrganizationViewModel.attendeePassword = null;
                //opens RegistrationMain
                Intent intent = new Intent(Attendee_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });
        //On click activity for Next button
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stores the data of this page into the viewModel class's static variables
                OrganizationViewModel.attendeeName = attendName.getText().toString().trim();
                OrganizationViewModel.attendeeLastName = attendLastName.getText().toString().trim();
                OrganizationViewModel.attendeePhone = attendPhone.getText().toString().trim();
                OrganizationViewModel.attendeeAddress = attendAddress.getText().toString().trim();

                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                if (!UserValidator.validateName(OrganizationViewModel.attendeeName)){
                    attendName.setError("Invalid username! username must contain at least one letter and only letters, numbers and underscore are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validateLastname(OrganizationViewModel.attendeeLastName)){
                    attendLastName.setError("Invalid lastname! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validatePhoneNumber(OrganizationViewModel.attendeePhone)){
                    attendPhone.setError("Invalid phone number! Only numbers are allowed.");
                    allValid = false;
                }

                //Tester, ensures the content of organizer name is correct, prints on logCat
                Log.i("CREATION", OrganizationViewModel.attendeeName);

                //Opens Attendee 2
                if (allValid){
                    Intent intent = new Intent(Attendee_1.this, Attendee_2.class);
                    startActivity(intent);
                }else{
                    //show a message to the user about fixing the errors
                    Toast.makeText(Attendee_1.this, "Please correct the errors before proceeding.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}