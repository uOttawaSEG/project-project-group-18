package com.example.seg_project_d11;

import android.os.Bundle;
import android.util.Log;
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

   // private List<User> rejectedRequests;
    private List<User> pendingRequests;

    private UserAdapter adapter;


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
        //fetch pending requests from databse
        databaseHelper = new DatabaseHelper(this);
        pendingRequests = databaseHelper.getAllRequests("Pending");

        //debugging
        Log.d("AdminActivity", "Number of pending requests: " + pendingRequests.size());

        //assign value to listOfRequests
        listOfRequests = findViewById(R.id.lvPendingList);
        //listOfRequests = findViewById(R.id.lvRejectList);

        //setting the adapter
        adapter = new UserAdapter(this, pendingRequests, databaseHelper);
        listOfRequests.setAdapter(adapter);
    }
}