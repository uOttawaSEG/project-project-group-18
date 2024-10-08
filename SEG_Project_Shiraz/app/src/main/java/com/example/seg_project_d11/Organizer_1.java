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
import androidx.lifecycle.ViewModelProvider;

public class Organizer_1 extends AppCompatActivity {
    Button goBack, goNext;
    TextView orgName, orgLastName, orgPhone, orgAddress;
    String organizerName, organizerPhone, organizerLastName, organizerAddress;
    //OrganizationViewModelViewModel organizationViewModel;

    //ActivityResultLauncher<Intent> RegistrationMainLauncher, Organizer_2Launcher;
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

        //sets view model
        //organizationViewModel = new ViewModelProvider(this).get(OrganizationViewModel.class);
        //Associates each button to button variable
        goBack = findViewById(R.id.backButton_o1);
        goNext = findViewById(R.id.nextButton);

        //Associates each text field to TextView variable
        orgName = findViewById(R.id.organizerName);
        orgLastName = findViewById(R.id.organizerLastName);
        orgPhone = findViewById(R.id.organizerPhone);
        orgAddress = findViewById(R.id.organizerAddress);

        /*//For goBack
        RegistrationMainLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );*/
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Organizer_1.this, RegistrationMain.class);
                startActivity(intent);
            }
        });
        /*//For goNext
        Organizer_2Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );*/
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stores the data using textview variables
                organizerName = orgName.getText().toString();
                organizerLastName = orgLastName.getText().toString();
                organizerPhone = orgPhone.getText().toString();
                organizerAddress = orgAddress.getText().toString();

                //Makes sure the changes above stay--DOES NOT WORK
                orgName.setText(organizerName);
                orgLastName.setText(organizerLastName);
                orgPhone.setText(organizerPhone);
                orgAddress.setText(organizerAddress);

                //debug
                Log.i("CREATION", organizerName);
                Intent intent = new Intent(Organizer_1.this, Organizer_2.class);
                startActivity(intent);
            }
        });
    }
}