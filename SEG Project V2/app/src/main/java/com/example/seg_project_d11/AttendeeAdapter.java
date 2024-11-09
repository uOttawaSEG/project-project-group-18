package com.example.seg_project_d11;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class AttendeeAdapter extends BaseAdapter{

    private Context context;
    private List<Attendee> attendees; // Refers to the list of attendees
    private DatabaseHelper dbHelper;
    private Integer eventID;

    public AttendeeAdapter(Context context, List<Attendee> attendees, DatabaseHelper dbHelper, int eventID) {
        this.context = context;
        this.attendees = attendees;
        this.dbHelper = dbHelper;
        this.eventID= eventID;
    }

    @Override
    public int getCount() {
        return attendees.size(); // Returns the number of items in the list
    }

    @Override
    public Object getItem(int position) {
        return attendees.get(position); // Returns the Attendee at specified position
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View attendeeItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        attendeeItem = inflater.inflate(R.layout.attendee_item, parent, false);

        //get the Attendee object for the current position
        Attendee attendee = (Attendee) this.getItem(position);

        //initiaize views from attendee_item layout
        TextView tv_email = attendeeItem.findViewById(R.id.textview_attendeeEmail);
        Button btn_details = attendeeItem.findViewById(R.id.button_details);
        Button btn_approve = attendeeItem.findViewById(R.id.button_approve);
        Button btn_reject = attendeeItem.findViewById(R.id.button_reject);

        // Set the attendee's email
        tv_email.setText(attendee.getEmail());

        // Handle button clicks
        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show details about the attendee
         String username = attendee.getEmail();


String AttendeeInfo ="Firstname: "+ attendee.getFirstName()+"\n"+
        "Lastname: " + attendee.getLastName() +"\n"+
        "PhoneNumber: "+attendee.getPhoneNumber()+"\n"+
        "Address: "+attendee.getAddress() +"\n"+
         "UserRole: "+attendee.getUserRole();

                new AlertDialog.Builder(context)
                        .setMessage(AttendeeInfo)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Action when OK is clicked (if any)
                    dialog.dismiss();
                })
                        .show();

            }


        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: approve the attendee request for event
                String attendeeEmail = attendee.getEmail();
                dbHelper.updateEventRequestStatus(attendeeEmail,eventID ,"Approved");
                Toast.makeText(context, "Attendee's request has been approved!", Toast.LENGTH_SHORT).show();



            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Reject the attendee request for event
                String attendeeEmail = attendee.getEmail();
                dbHelper.updateEventRequestStatus(attendeeEmail,eventID ,"Rejected");
                Toast.makeText(context, "Attendee's request has been rejected!", Toast.LENGTH_SHORT).show();

            }
        });

        return attendeeItem;

    }
}
