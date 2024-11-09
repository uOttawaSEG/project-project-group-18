package com.example.seg_project_d11;

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

        databaseHelper.close();
    }



}
