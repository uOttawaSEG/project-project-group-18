package com.example.seg_project_d11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private ListView eventListView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private EditText searchBar; // Search bar input field
    private Button searchButton; // Button to trigger search



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

// Initialize ListView
        eventListView = findViewById(R.id.eventListView);
        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        //get the userName and role from the previous intent
        String username = getIntent().getStringExtra("user_name");
        String userRole = getIntent().getStringExtra("user_role");

        dbHelper = new DatabaseHelper(this);
        eventList = new ArrayList<>();
        // Set the custom adapter
        eventAdapter = new EventAdapter(this, eventList, dbHelper, userRole, username);
        eventListView.setAdapter(eventAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the keyword from the search bar
                String keyword = searchBar.getText().toString().trim();

                // Perform the search and update the list
                eventList.clear(); // Clear the old results
                eventList.addAll(dbHelper.searchEvents(keyword)); // Add new results
                eventAdapter.notifyDataSetChanged(); // Notify adapter to refresh the ListView
            }
        });
    }
}
