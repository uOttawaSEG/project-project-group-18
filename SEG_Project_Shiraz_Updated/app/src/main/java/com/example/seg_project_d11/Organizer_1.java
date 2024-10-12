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

public class Organizer_1 extends AppCompatActivity {
    //Back and next buttons
    Button goBack, goNext;
    //User input fields
    TextView orgName, orgLastName, orgPhone, orgAddress;
    //ViewModel initialization, hold user information in static variables
    OrganizationViewModel organizationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //creates the view model
        organizationViewModel = new ViewModelProvider(this).get(OrganizationViewModel.class);
        //Associates each button to variable
        goBack = findViewById(R.id.backButton_o1);
        goNext = findViewById(R.id.nextButton);

        //Associates each text field to TextView variable
        orgName = findViewById(R.id.organizerName);
        orgLastName = findViewById(R.id.organizerLastName);
        orgPhone = findViewById(R.id.organizerPhone);
        orgAddress = findViewById(R.id.organizerAddress);

        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        orgName.setText(OrganizationViewModel.organizerName);
        orgLastName.setText(OrganizationViewModel.organizerLastName);
        orgPhone.setText(OrganizationViewModel.organizerPhone);
        orgAddress.setText(OrganizationViewModel.organizerAddress);

        //On click activity for back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resets the user info inputs since, thereby cancelling registration
                OrganizationViewModel.organizerName = null;
                OrganizationViewModel.organizerLastName = null;
                OrganizationViewModel.organizerPhone = null;
                OrganizationViewModel.organizerAddress = null;
                OrganizationViewModel.organizationName = null;
                OrganizationViewModel.organizerEmail = null;
                OrganizationViewModel.organizerPassword = null;
                //opens RegistrationMain
                Intent intent = new Intent(Organizer_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });
        //On click activity for Next button
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stores the data of this page into the viewModel class's static variables
                OrganizationViewModel.organizerName = orgName.getText().toString().trim();
                OrganizationViewModel.organizerLastName = orgLastName.getText().toString().trim();
                OrganizationViewModel.organizerPhone = orgPhone.getText().toString().trim();
                OrganizationViewModel.organizerAddress = orgAddress.getText().toString().trim();

                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                if (!UserValidator.validateName(OrganizationViewModel.organizerName)){
                    orgName.setError("Invalid username! username must contain at least one letter and only letters, numbers and underscore are allowed.");
                    allValid = false;
                }


                if (!UserValidator.validateLastname(OrganizationViewModel.organizerLastName)){
                    orgLastName.setError("Invalid lastname! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validatePhoneNumber(OrganizationViewModel.organizerPhone)){
                    orgPhone.setError("Invalid phone number! Only numbers are allowed.");
                    allValid = false;
                }



                //Tester, ensures the content of organizer name is correct, prints on logCat
                Log.i("CREATION", OrganizationViewModel.organizerName);

                if (allValid){
                    //Opens Organizer 2
                    Intent intent = new Intent(Organizer_1.this, Organizer_2.class);
                    startActivity(intent);
                }else{
                    //show a message to the user about fixing the errors
                    Toast.makeText(Organizer_1.this, "Please correct the errors before proceeding.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}