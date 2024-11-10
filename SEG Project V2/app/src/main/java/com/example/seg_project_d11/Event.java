package com.example.seg_project_d11;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
    private String title, description, date, startTime, endTime, eventAddress;
    private Integer eventID;
    private Integer choiceAccept;


    // Constructor for a new event, without eventID (since it's auto-incremented)
    public Event(String title, String description, String date, String startTime, String endTime, String eventAddress, int choiceAccept){
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventAddress = eventAddress;
        this.choiceAccept = choiceAccept;
    }
    // Constructor for retrieving events from the database (includes eventID)
    public Event(Integer eventID, String title, String description, String date, String startTime, String endTime, String eventAddress, int choiceAccept) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventAddress = eventAddress;
        this.choiceAccept = choiceAccept;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
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

    public int getAcceptChoice(){ return choiceAccept;}
}
