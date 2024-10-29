package com.example.seg_project_d11;


import static com.example.seg_project_d11.Administrator.approveRequest;
import static com.example.seg_project_d11.Administrator.rejectRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<User> myUsers;
    private DatabaseHelper dbHelper;

    public UserAdapter(Context context, List<User> myUsers, DatabaseHelper dbHelper) {
        this.context = context;
        this.myUsers = myUsers;
        this.dbHelper = dbHelper;
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
        TextView tv_userRole = listItem.findViewById(R.id.textViewUserRole);


        User user = (User) this.getItem(position);

        Button approveButton = listItem.findViewById(R.id.buttonApprove);
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
                Toast.makeText(context, firstName + " " + lastname + "is rejected!", Toast.LENGTH_SHORT).show();
                rejectRequest(user, dbHelper);
                myUsers.remove(user);
                notifyDataSetChanged(); // Refresh the ListView
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

        return listItem;
    }
}
