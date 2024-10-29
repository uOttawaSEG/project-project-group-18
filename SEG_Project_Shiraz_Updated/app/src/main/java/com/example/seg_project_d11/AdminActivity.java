package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listOfRequests;

    private List<User> pendingRequests;

    private UserAdapter adapter;
    Button backButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pendadmin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //fetch pending requests from database
        databaseHelper = new DatabaseHelper(this);
        pendingRequests = databaseHelper.getAllRequests("Pending");


        //debugging
        Log.d("AdminActivity", "Number of pending requests: " + pendingRequests.size());

        //assign value to listOfRequests
        listOfRequests = findViewById(R.id.lvPendingList);


        //setting the adapter
        adapter = new UserAdapter(this, pendingRequests, databaseHelper);
        listOfRequests.setAdapter(adapter);

        backButton1 = findViewById(R.id.backButton);

        backButton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent(AdminActivity.this, Admin_welcomePage_Activity.class);
                startActivity(intent);
            }
        });
    }
}