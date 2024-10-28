package com.example.seg_project_d11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Admin_welcomePage_Activity extends AppCompatActivity {

    Button goToPendingRequest;
    Button goToRejectedRequests;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_welcomepage);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });



            goToPendingRequest = findViewById(R.id.pendingButton);
            goToRejectedRequests = findViewById(R.id.RejectButton);
            logout = findViewById(R.id.logOut);

        goToPendingRequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent(Admin_welcomePage_Activity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        goToRejectedRequests.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent(Admin_welcomePage_Activity.this, RejectAdminActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_welcomePage_Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });



        //Todo: repeat same idea for the rejected requests button

    }


}