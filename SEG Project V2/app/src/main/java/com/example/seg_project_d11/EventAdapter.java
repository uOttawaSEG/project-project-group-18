package com.example.seg_project_d11;

import static com.example.seg_project_d11.Administrator.approveRequest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends BaseAdapter {
    private Context context;
    private List<Event> events; //refers to the list of events for an organizer
    private DatabaseHelper dbHelper;
    private String userType;
    private String userName;

    public EventAdapter(Context context,List<Event> events, DatabaseHelper dbHelper, String userType, String userName) {
        this.context = context;
        this.events = events;
        this.dbHelper = dbHelper;
        this.userType = userType;
        this.userName = userName;
    }


    @Override
    public int getCount() {
        return events.size(); //returns the number of items in the list

    }
    @Override
    public Object getItem(int position) {
        return events.get(position); //returns the User at specified position
    }

    @Override
    public long getItemId(int position) {
            return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //send back a view that we can use in the individual list item

        View eventItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        eventItem= inflater.inflate(R.layout.event_item, parent, false);
        Event event = (Event) this.getItem(position);


        TextView tv_title = eventItem.findViewById(R.id.textview_eventTitle);
        TextView tv_description = eventItem.findViewById(R.id.textview_eventDescription);
        TextView tv_date = eventItem.findViewById(R.id.textview_eventDate);
        Button actionButton = eventItem.findViewById(R.id.button_attendees);

        String title = event.getTitle();
        String description = event.getDescription();
        String date = event.getDate();
        Integer eventID= event.getEventID();


        tv_title.setText("Event Title: " + title);
        tv_description.setText("Event description: " + description);
        tv_date.setText("Event date: " + date);

        //set Action button based on user role
        if ("Attendee".equals(userType)) {
            actionButton.setText("Request Registration for Event");
        } else if ("Organizer".equals(userType)) {
            actionButton.setText("See Attendee Requests");
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("Attendee")){
                    Log.d("EventAdapter" , "eventID: "+ eventID);
                    dbHelper.addEventRequest(userName, eventID);
                    Toast.makeText(context, "Your request is now sent!", Toast.LENGTH_SHORT).show();


                }else if (userType.equals("Organizer")){
                    Intent new_intent = new Intent(context, OrganizerSeeAttendeeEventRequestsActivity.class);
                    new_intent.putExtra("user_name", userName);
                    new_intent.putExtra("user_role",userType);
                    new_intent.putExtra("eventID",eventID);
                    Log.d("EventAdapterNextActivity" , "eventID: "+ eventID);

                    context.startActivity(new_intent);
                }
                notifyDataSetChanged(); // Refresh the ListView
            }
        });

        return eventItem;
    }
}
