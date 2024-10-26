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
    //User initialization, hold user information
    static Organizer user= new Organizer(null,null, null, null, null, null, null, null);


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
        orgName.setText(user.getFirstName()); //change
        orgLastName.setText(user.getLastName());//change
        orgPhone.setText(user.getPhoneNumber());//change
        orgAddress.setText(user.getAddress());//change

        //On click activity for back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user= new Organizer(null,null, null, null, null, null, null, null);
                //opens RegistrationMain
                Intent intent = new Intent(Organizer_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });
        //On click activity for Next button
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;
                String name = orgName.getText().toString().trim();
                String lastName = orgLastName.getText().toString().trim();
                String phone = orgPhone.getText().toString().trim();

                if (!UserValidator.validateName(name)){
                    orgName.setError("Invalid name! Only letters are allowed.");
                    allValid = false;
                }
                if (!UserValidator.validateName(lastName)){
                    orgLastName.setError("Invalid lastname! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validatePhoneNumber(phone)){
                    orgPhone.setError("Invalid phone number! Only numbers are allowed.");
                    allValid = false;
                }

                if (allValid){
                    user.setFirstName(name);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phone);
                    user.setAddress(orgAddress.getText().toString().trim());
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