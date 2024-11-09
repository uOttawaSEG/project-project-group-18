package com.example.seg_project_d11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Admin_SignIn_Activity extends AppCompatActivity {

    Button signIn;
    Button back;
    EditText username;
    EditText password;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signIn = findViewById(R.id.SignInButton);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.userPassword);
        back = findViewById(R.id.buttonback);




        signIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String adminUsername = username.getText().toString().trim();
                String adminPassword = password.getText().toString().trim();

                if ( Administrator.checkCredentials(adminUsername, adminPassword )){
                    Intent intent = new Intent(Admin_SignIn_Activity.this, Admin_welcomePage_Activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Admin_SignIn_Activity.this, "Username and/or Password are not correct!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Admin_SignIn_Activity.this, SelectionActivity.class);
                startActivity(intent);


            }
        });

    }
}