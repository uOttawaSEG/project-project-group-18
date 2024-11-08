package com.example.seg_project_d11;

import android.content.Context;
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
    private ArrayList<Event> events;
    private DatabaseHelper dbHelper;

    public EventAdapter(Context context, ArrayList<Event> events, DatabaseHelper dbHelper) {
        this.context = context;
        this.events = events;
        this.dbHelper = dbHelper;
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
            return 0;//don't care for now
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        //send back a view that we can use in the individual list item

        View eventItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        eventItem= inflater.inflate(R.layout.event_item, parent, false);
        Event event = (Event) this.getItem(position);

        TextView tv_title = eventItem.findViewById(R.id.textview_eventTitle);
        TextView tv_description = eventItem.findViewById(R.id.textview_eventDescription);
        TextView tv_date = eventItem.findViewById(R.id.textview_eventDate);

        String title = event.getTitle();
        String description = event.getDescription();
        String date = event.getDate();

        tv_title.setText("Event Title: " + title);
        tv_description.setText("Event description: " + description);
        tv_date.setText("Event date: " + date);

        return eventItem;
    }
}
