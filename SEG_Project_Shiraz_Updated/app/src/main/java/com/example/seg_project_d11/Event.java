package com.example.seg_project_d11;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
    String title, description, date, startTime, endTime, eventAddress;
    ArrayList<Attendee> attendeeList = new ArrayList<Attendee>();


    public Event(String title, String description, String date, String startTime, String endTime, String eventAddress){
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventAddress = eventAddress;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public String getStartTime(){
        return startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    public String getEndTime(){
        return endTime;
    }

    public void setEventAddress(String eventAddress){
        this.eventAddress = eventAddress;
    }
    public String getEventAddress(){
        return eventAddress;
    }

    public void addAttendee(Attendee attendee){
        attendeeList.add(attendee);
    }
    public ArrayList<Attendee> getAttendeeList(){
        return attendeeList;
    }

}
