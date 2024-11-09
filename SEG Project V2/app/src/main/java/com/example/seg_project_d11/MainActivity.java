package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//This activity will probably be removed, it is only a placeholder until the project is merged
public class MainActivity extends AppCompatActivity {
    //only the registration button works
    Button goToRegPage;
    Button signIn;
    DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ///add some attendees and organizers to the database to populate it
        databaseHelper = new DatabaseHelper(this);
        PopulateDatabase populateDatabase = new PopulateDatabase(databaseHelper);
        populateDatabase.addSampleData();

        //Creates button variable
        goToRegPage= findViewById(R.id.registerButton);
        signIn= findViewById(R.id.buttonSignIn);

        //On click, opens Registration_main activity
        goToRegPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationMain.class);
                startActivity(intent);
            }
        });

        //On click, opens Registration_main activity
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                    startActivity(intent);
            }
        });

    }
}