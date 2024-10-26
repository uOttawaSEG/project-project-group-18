package com.example.seg_project_d11;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInPage extends AppCompatActivity {
    //go back and submit buttons
    Button goBack;
    Button submit;

    //User input fields
    TextView userName,passWord;

    //Initiate database variable
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initializes the Back and submit buttons
        submit= findViewById(R.id.nextButton);
        goBack = findViewById(R.id.backButton);

        //initializing the database
        db = new DatabaseHelper(this);

        //Associates each textField to TextView variable
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.password);



        //On click activity for Back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goes back to Organizer_1 activity
                Intent intent = new Intent(SignInPage.this, SelectionActivity.class);
                startActivity(intent);
            }
        });

        submit = findViewById(R.id.nextButton);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String username = userName.getText().toString().trim();
                String password = passWord.getText().toString().trim();

                // Use the database helper to check if the user exists
                if (db.checkUserAccepted(username,password)) {
                    Intent intent = new Intent(SignInPage.this, WelcomePage.class);
                    startActivity(intent);
                }else{
                    if (db.checkUserPending(username,password)){

                        AlertDialog.Builder message = new AlertDialog.Builder(SignInPage.this);
                        message.setCancelable(true);
                        message.setTitle("Attention!");
                        message.setMessage("Your account request is pending. Please contact the Administrator: admin@example.com");

                        // Cancel Button
                        message.setNegativeButton("Cancel", (d, i) -> d.cancel());

                        // Ok/Redirect  Button
                        message.setPositiveButton("Return to main", (d,i) -> {
                            Intent intent = new Intent(SignInPage.this, MainActivity.class);
                            startActivity(intent);
                        });
                        message.show();

                    } else{

                        AlertDialog.Builder message = new AlertDialog.Builder(SignInPage.this);
                        message.setCancelable(true);
                        message.setTitle("Attention!");
                        message.setMessage("Your account has been rejected.");

                        // Cancel Button
                        message.setNegativeButton("Cancel", (d, i) -> d.cancel());

                        // Ok/Redirect  Button
                        message.setPositiveButton("Return to main", (d,i) -> {
                            Intent intent = new Intent(SignInPage.this, MainActivity.class);
                            startActivity(intent);
                        });
                        message.show();


                    }

                }
            }
        });

    }
}