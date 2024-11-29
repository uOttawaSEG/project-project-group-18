package com.example.seg_project_d11;

import android.util.Log;

import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<Event> events;

    //constructor without list of events
    public Attendee(String firstName, String lastName, String email, String password, String phoneNumber, String address, String status, String userRole) {
        super(firstName, lastName, email, password, phoneNumber, address, status, userRole);
    }
    //constructor with list of events
    public Attendee(String firstName, String lastName, String email, String password, String phoneNumber, String address, String status, String userRole, ArrayList<Event> events) {
        super(firstName, lastName, email, password, phoneNumber, address, status, userRole, events);
        //this.events = events;
    }

    /*public ArrayList<Event> getEventList() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }*/

    public void cancelEventReg (Event event){
        Log.d("Attendee", "removing event");
        events = getEventList();
        if( events != null){
            events.remove(event);
            setEventList(events);
            Log.d("Attendee", "success)");
        }else{
            Log.d("Attendee", "Events not created");
        }
    }
}
