package com.example.seg_project_d11;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<User> myUsers;

    public UserAdapter(Context context, List<User> myUsers) {
        this.context = context;
        this.myUsers = myUsers;
    }

    @Override
    public int getCount() {
        return myUsers.size(); //returns the number of items in the list
    }

    @Override
    public Object getItem(int position) {
        return myUsers.get(position); //returns the User at specified position
    }

    @Override
    public long getItemId(int position) {
        return 0;//don't care for now
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //send back a view that we can use in the individual list item

        View listItem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItem= inflater.inflate(R.layout.list_item, parent, false);

        TextView tv_firstName = listItem.findViewById(R.id.textViewFirstName);
        TextView tv_lastname = listItem.findViewById(R.id.textViewLastname);
        TextView tv_email = listItem.findViewById(R.id.textViewEmail);
        TextView tv_phoneNumber = listItem.findViewById(R.id.textViewPhoneNumber);
        TextView tv_address = listItem.findViewById(R.id.textViewAddress);
        TextView tv_status = listItem.findViewById(R.id.textViewStatus);
        TextView tv_organizationName= listItem.findViewById(R.id.textViewOrganizationName);


        User user = (User) this.getItem(position);

        if (user instanceof Attendee){
            tv_firstName.setText(user.getFirstName());
            tv_lastname.setText(user.getLastName());
            tv_email.setText(user.getEmail());
            tv_phoneNumber.setText(user.getPhoneNumber());
            tv_address.setText(user.getAddress());
            tv_status.setText(user.getStatus());

        } else if(user instanceof Organizer){
            tv_firstName.setText(user.getFirstName());
            tv_lastname.setText(user.getLastName());
            tv_email.setText(user.getEmail());
            tv_phoneNumber.setText(user.getPhoneNumber());
            tv_address.setText(user.getAddress());
            tv_status.setText(user.getStatus());
            tv_organizationName.setText(((Organizer) user).getOrganizationName());
        }



        return listItem;
    }
}
