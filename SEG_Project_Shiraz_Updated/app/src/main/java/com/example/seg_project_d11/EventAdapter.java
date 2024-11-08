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

        View listItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItem= inflater.inflate(R.layout.list_item, parent, false);

        TextView tv_title = listItem.findViewById(R.id.textview_eventTitle);
        TextView tv_description = listItem.findViewById(R.id.textview_eventDescription);
        TextView tv_date = listItem.findViewById(R.id.textview_eventDate);


        Event event = (Event) this.getItem(position);

        /*Button approveButton = listItem.findViewById(R.id.buttonApprove);
        Button rejectButton = listItem.findViewById(R.id.buttonReject);
        String firstName = user.getFirstName();
        String lastname = user.getLastName();

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, firstName + " " + lastname + " is approved!", Toast.LENGTH_SHORT).show();
                approveRequest(user, dbHelper);
                myUsers.remove(user);
                notifyDataSetChanged(); // Refresh the ListView
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(user.getStatus().equals("Pending")) {
                    Toast.makeText(context, firstName + " " + lastname + "is rejected!", Toast.LENGTH_SHORT).show();
                    rejectRequest(user, dbHelper);
                    myUsers.remove(user);
                    notifyDataSetChanged(); // Refresh the ListView
                }
                else{
                    Toast.makeText(context, "This request has been rejected already", Toast.LENGTH_SHORT).show();}
                }
            });

            tv_firstName.setText("firstName: "+user.getFirstName());
            tv_lastname.setText("lastName: "+user.getLastName());
            tv_email.setText("Email: "+user.getEmail());
            tv_phoneNumber.setText("PhoneNumber: "+user.getPhoneNumber());
            tv_address.setText("Address: "+user.getAddress());
            tv_status.setText("Status: "+user.getStatus());
            tv_userRole.setText("UserRole: "+user.getUserRole());

            if (user instanceof Organizer){
                tv_organizationName.setText(((Organizer) user).getOrganizationName());
            }


        }*/
        return listItem;
    }
}
