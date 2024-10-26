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
    //User initialization, hold user information
    static Attendee user= new Attendee(null,null, null, null, null, null, null);


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

        //Associates each button to variable
        goBack = findViewById(R.id.backButton);
        goNext = findViewById(R.id.nextButton);

        //Associates each text field to TextView variable
        attendName = findViewById(R.id.attendeeName);
        attendLastName = findViewById(R.id.attendeeLastName);
        attendPhone = findViewById(R.id.attendeePhone);
        attendAddress = findViewById(R.id.attendeeAddress);

        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        attendName.setText(user.getFirstName()); //change
        attendLastName.setText(user.getLastName());//change
        attendPhone.setText(user.getPhoneNumber());//change
        attendAddress.setText(user.getAddress());//change

        //On click activity for back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user =new Attendee(null,null, null, null, null, null, null);

                //opens RegistrationMain
                Intent intent = new Intent(Attendee_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });
        //On click activity for Next button
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                String name = attendName.getText().toString().trim();
                String lastName = attendLastName.getText().toString().trim();
                String phone = attendPhone.getText().toString().trim();

                if (!UserValidator.validateName(name)){
                    attendName.setError("Invalid name! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validateName(lastName)){
                    attendLastName.setError("Invalid lastname! Only letters are allowed.");
                    allValid = false;
                }

                if (!UserValidator.validatePhoneNumber(phone)){
                    attendPhone.setError("Invalid phone number! Only numbers are allowed.");
                    allValid = false;
                }


                //Opens Attendee 2
                if (allValid){
                    //sets user info
                    user.setFirstName(name);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phone);
                    user.setAddress(attendAddress.getText().toString().trim());
                    //opens next page
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