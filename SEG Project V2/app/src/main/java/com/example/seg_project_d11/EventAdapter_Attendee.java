package com.example.seg_project_d11;

import static com.example.seg_project_d11.Administrator.approveRequest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter_Attendee extends BaseAdapter {
    private Context context;
    private List<Event> events; //refers to the list of events for an attendee
    private DatabaseHelper dbHelper;
    private String userType;
    private String userName;


    public EventAdapter_Attendee(Context context,List<Event> events, DatabaseHelper dbHelper, String userType, String userName) {
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
        return events.get(position);
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
        eventItem= inflater.inflate(R.layout.event_item_attendee, parent, false);
        Event event = (Event) this.getItem(position);


        TextView tv_title = eventItem.findViewById(R.id.textview_eventTitle);
        TextView tv_description = eventItem.findViewById(R.id.textview_eventDescription);
        TextView tv_date = eventItem.findViewById(R.id.textview_eventDate);
        TextView tv_requestStatus = eventItem.findViewById(R.id.textview_eventRequestStatus);
        Button details = eventItem.findViewById(R.id.button_details);

        String title = event.getTitle();
        String description = event.getDescription();
        String date = event.getDate();
        Integer eventID= event.getEventID();
        int acceptChoice = event.getAcceptChoice();
        Log.d("EventAdapter", "Event ID: " + eventID + " - Accept Choice: " + acceptChoice);

        String requestStatus = dbHelper.getEventRequesStatus(userName, eventID);

        Button cancelButton = eventItem.findViewById(R.id.button_cancel_event);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: complete this
            }
        });


        tv_title.setText("Event Title: " + title);
        tv_description.setText("Event description: " + description);
        tv_date.setText("Event date: " + date);
        tv_requestStatus.setText("Status of your request: "+ requestStatus);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogue_eventdetails);

                TextView title, description, date, startTime, endTime, address;

                title = dialog.findViewById(R.id.textView_Title);
                description = dialog.findViewById(R.id.textView_Desc);
                date = dialog.findViewById(R.id.textView_Date);
                startTime = dialog.findViewById(R.id.textView_STime);
                endTime = dialog.findViewById(R.id.textView_ETime);
                address = dialog.findViewById(R.id.textView_Address);

                title.setText("     Title: " + event.getTitle());
                description.setText("     Description: " + event.getDescription());
                date.setText("     Date: " + event.getDate());
                startTime.setText("     Start time: " + event.getStartTime());
                endTime.setText("     End time: " + event.getEndTime());
                address.setText("     Address: " + event.getEventAddress());

                Button dismiss = dialog.findViewById(R.id.button_dismiss);

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);

                dialog.show();
            }
        });

        return eventItem;
    }
}
