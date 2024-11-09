package com.example.seg_project_d11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AttendeeAdapter extends BaseAdapter{

    private Context context;
    private List<Attendee> attendees; // Refers to the list of attendees
    private DatabaseHelper dbHelper;
    private String userType;
    private String userName;

    public AttendeeAdapter(Context context, List<Attendee> attendees, DatabaseHelper dbHelper, String userType, String userName) {
        this.context = context;
        this.attendees = attendees;
        this.dbHelper = dbHelper;
        this.userType = userType;
        this.userName = userName;
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


            }
        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: approve the attendee request for event

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Reject the attendee request for event

            }
        });

        return attendeeItem;

    }
}
