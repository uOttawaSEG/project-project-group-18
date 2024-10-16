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
    AccountsViewModel organizationViewModel;

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
        organizationViewModel = new AccountsViewModel(this);
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
        orgName.setText(AccountsViewModel.organizerName);
        orgLastName.setText(AccountsViewModel.organizerLastName);
        orgPhone.setText(AccountsViewModel.organizerPhone);
        orgAddress.setText(AccountsViewModel.organizerAddress);

        //On click activity for back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resets the user info inputs since, thereby cancelling registration
                AccountsViewModel.organizerName = null;
                AccountsViewModel.organizerLastName = null;
                AccountsViewModel.organizerPhone = null;
                AccountsViewModel.organizerAddress = null;
                AccountsViewModel.organizationName = null;
                AccountsViewModel.organizerEmail = null;
                AccountsViewModel.organizerPassword = null;
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
                AccountsViewModel.organizerName = orgName.getText().toString().trim();
                AccountsViewModel.organizerLastName = orgLastName.getText().toString().trim();
                AccountsViewModel.organizerPhone = orgPhone.getText().toString().trim();
                AccountsViewModel.organizerAddress = orgAddress.getText().toString().trim();

                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                if (!UserValidator.validateName(AccountsViewModel.organizerName)){
                    orgName.setError("Invalid username! username must contain at least one letter and only letters, numbers and underscore are allowed.");
                    allValid = false;
                }


                if (!UserValidator.validateLastname(AccountsViewModel.organizerLastName)){
                    orgLastName.setError("Invalid lastname! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validatePhoneNumber(AccountsViewModel.organizerPhone)){
                    orgPhone.setError("Invalid phone number! Only numbers are allowed.");
                    allValid = false;
                }



                //Tester, ensures the content of organizer name is correct, prints on logCat
                Log.i("CREATION", AccountsViewModel.organizerName);

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