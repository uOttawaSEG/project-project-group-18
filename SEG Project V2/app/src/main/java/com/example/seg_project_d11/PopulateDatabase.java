package com.example.seg_project_d11;

import android.util.Log;

public class PopulateDatabase {

    private DatabaseHelper databaseHelper;

    public PopulateDatabase(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void addSampleData(){
        //default organizer and attendee for testing cases
        Organizer org1 = new Organizer("Mary","Lamb","Maryhasalamb@gmail.com","1234a","343-345-8900","45 street","Ciena","Pending","Organizer");
        Organizer org2 = new Organizer("Brand","yellow","Brandyellow@gmail.com","1679ca","678-325-8900","50 street","Ciena","Pending", "Organizer");
        Attendee attendee1 = new Attendee("Jake","Pirate","Jake.p@gmail.com","123op","358- 777- 9045","30 street","Pending","Attendee");
        Attendee attendee2 = new Attendee("Caren","Red","Caren.r@yahoo.com","45683bh","678- 888- 9085","60 street","Pending", "Attendee");
        //add users to the database
        databaseHelper.addUser(org1);
        databaseHelper.addUser(org2);
        databaseHelper.addUser(attendee1);
        databaseHelper.addUser(attendee2);

        if (!databaseHelper.areEventsInitialized()) {
            //create events
            Event event1 = new Event("Concert","BEST concert ever", "11/30/2024", "15:00", "21:00", "stadium A" ,1);
            Event event2 = new Event("Basketball Game","Canada's best team basketball game", "21/05/2024", "12:00", "13:00", "TD place",1 );
            Event event3 = new Event("Bazaar","everything you can find bazaar", "25/06/2025", "16:00", "21:00", "Ottawa",0 );
            Event event4 = new Event("Bake Sale","biggest bake sale", "21/03/2024", "15:00", "21:00", "Ottawa",1 );
            Event event5 = new Event("Music Concert","live music with various bands", "21/03/2025", "15:00", "21:00", "StageB" ,0);
            Event event6 = new Event("Football Game","Canada's Football team games", "21/03/2025", "15:00", "21:00", "stadium C", 1);

            databaseHelper.addEvent(event1, org1.getEmail());
            databaseHelper.addEvent(event2, org1.getEmail());
            databaseHelper.addEvent(event3, org1.getEmail());
            databaseHelper.addEvent(event4, org1.getEmail());
            databaseHelper.addEvent(event5, org1.getEmail());
            databaseHelper.addEvent(event6, org1.getEmail());

        }




        databaseHelper.close();
    }



}
