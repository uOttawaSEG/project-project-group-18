package com.example.seg_project_d11;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
    private static final String DATABASE_NAME = "Userdata.db";

    //database version
    private static final int DATABASE_VERSION = 1;

    //user table
    private static final String USER_TABLE_ATTENDEES = "USER_TABLE_ATTENDEES";
    private static final String USER_TABLE_ORGANIZERS = "USER_TABLE_ORGANIZERS";

    private static final String COLUMN_FIRSTNAME = "firstName";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
    private static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ORGANIZATIONNAME = "organizationName";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Attendees table
        String CREATE_ATTENDEES_TABLE = "CREATE TABLE " + USER_TABLE_ATTENDEES + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                +COLUMN_PHONENUMBER + " TEXT, "
                +COLUMN_ADDRESS + " TEXT, "
                +COLUMN_PASSWORD + " TEXT, "
                +COLUMN_STATUS + " TEXT DEFAULT 'Pending');";

        db.execSQL(CREATE_ATTENDEES_TABLE);

        //create Organizers table
        String CREATE_ORGANIZERS_TABLE = "CREATE TABLE " + USER_TABLE_ORGANIZERS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                +COLUMN_PHONENUMBER + " TEXT, "
                +COLUMN_ADDRESS + " TEXT, "
                +COLUMN_PASSWORD + " TEXT, "
                +COLUMN_ORGANIZATIONNAME + " TEXT, "
                +COLUMN_STATUS + " TEXT DEFAULT 'Pending');";

        db.execSQL(CREATE_ORGANIZERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_ATTENDEES);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_ORGANIZERS);
        // Create tables again
        onCreate(db);

    }
    // add an attendee data to the database
    public boolean addAttendee(Attendee attendee){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, attendee.getEmail());
        cv.put(COLUMN_FIRSTNAME, attendee.getFirstName());
        cv.put(COLUMN_LASTNAME, attendee.getLastName());
        cv.put(COLUMN_PHONENUMBER, attendee.getPhoneNumber());
        cv.put(COLUMN_ADDRESS, attendee.getAddress());
        cv.put(COLUMN_PASSWORD, attendee.getPassword());
        cv.put(COLUMN_STATUS, attendee.getStatus());


        long insert = db.insert(USER_TABLE_ATTENDEES, null, cv); //if insert is -1, adding failed

        if(insert ==-1){
            return false;
        }else{
            return true;
        }
    }

    //add an organizer to the database
    public boolean addOrganizer(Organizer organizer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, organizer.getEmail());
        cv.put(COLUMN_FIRSTNAME, organizer.getFirstName());
        cv.put(COLUMN_LASTNAME, organizer.getLastName());
        cv.put(COLUMN_PHONENUMBER, organizer.getPhoneNumber());
        cv.put(COLUMN_ADDRESS, organizer.getAddress());
        cv.put(COLUMN_PASSWORD, organizer.getPassword());
        cv.put(COLUMN_STATUS, organizer.getStatus());

        long insert = db.insert(USER_TABLE_ORGANIZERS, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //fetch a list of users(attendees and organizers) from the database
    public List<User> getPendingRequests(){
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //fetch attendees
        //rawQuery returns a cursor
        Cursor cursorAttendees = db.rawQuery("SELECT * FROM " + USER_TABLE_ATTENDEES + " WHERE " + COLUMN_STATUS + " = 'Pending'", null);
        //move to the first result in the result set(cursor)
        if(cursorAttendees.moveToFirst()){
            //loop through the result and create a new attendee. put attendee in the list.
            do{

                String email = cursorAttendees.getString(0);
                String firstName = cursorAttendees.getString(1);
                String lastname = cursorAttendees.getString(2);
                String phoneNumber =cursorAttendees.getString(3);
                String address =cursorAttendees.getString(4);
                String password = cursorAttendees.getString(5);
                String status = cursorAttendees.getString(6);

                Attendee attendee = new Attendee(firstName, lastname, email, password, phoneNumber, address, status);
                users.add(attendee);

            } while(cursorAttendees.moveToNext());
        }else{
            //failure. do not add anything to the list.
        }

        cursorAttendees.close();

        //fetch organizers
        Cursor cursorOrganizers = db.rawQuery("SELECT * FROM " + USER_TABLE_ORGANIZERS + " WHERE " + COLUMN_STATUS + " = 'Pending'", null);

        if(cursorOrganizers.moveToFirst()){
            //loop through the result and create a new attendee. put attendee in the list.
            do{

                String email = cursorOrganizers.getString(0);
                String firstName = cursorOrganizers.getString(1);
                String lastname = cursorOrganizers.getString(2);
                String phoneNumber =cursorOrganizers.getString(3);
                String address =cursorOrganizers.getString(4);
                String password = cursorOrganizers.getString(5);
                String organizationName = cursorOrganizers.getString(6);
                String status = cursorOrganizers.getString(7);

                Organizer organizer = new Organizer(firstName, lastname, email, password, phoneNumber, address, organizationName, status);
                users.add(organizer);

            } while(cursorOrganizers.moveToNext());
        }else{
            //failure. do not add anything to the list.
        }

        cursorOrganizers.close();
        db.close();
        return users;

    }

    //method to check if User is accepted
    public boolean checkUserAccepted(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Check for user in the Attendee List
        Cursor findAttendee = db.rawQuery("SELECT * FROM " + USER_TABLE_ATTENDEES + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (findAttendee.getCount()>0){ //if greater than 0, than account exists
            findAttendee.close();
            return true;
        }
        findAttendee.close(); //Account isn't found so, close cursor

        //Check for user in the Organizer List
        Cursor findOrganizer = db.rawQuery("SELECT * FROM " + USER_TABLE_ORGANIZERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (findOrganizer.getCount() >0){//if greater than 0, than account exists
            findOrganizer.close();
            return true;
        }
        findOrganizer.close();//Account isn't found so, close cursor

        return false;

    }

    //method to check if user is pending (very similar to checking for accepted)
    public boolean checkUserPending(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Check for user in the Attendee List
        Cursor findAttendee = db.rawQuery("SELECT * FROM " + USER_TABLE_ATTENDEES + " WHERE " + COLUMN_STATUS + " = 'Pending'" + " = ? AND " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (findAttendee.getCount()>0){ //if greater than 0, than account exists
            findAttendee.close();
            return true;
        }
        findAttendee.close(); //Account isn't found so, close cursor

        //Check for user in the Organizer List
        Cursor findOrganizer = db.rawQuery("SELECT * FROM " + USER_TABLE_ORGANIZERS + " WHERE " + COLUMN_STATUS + " = 'Pending'" + " = ? AND " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (findOrganizer.getCount() >0){//if greater than 0, than account exists
            findOrganizer.close();
            return true;
        }
        findOrganizer.close();//Account isn't found so, close cursor
        return false;
    }


    //method to update the user's status
    public void updateUserStatus(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, user.getStatus());

        if (user instanceof Attendee){
            //changing the status of the user by finding the matching email in the database
            db.update(USER_TABLE_ATTENDEES, cv, COLUMN_EMAIL + " = ?", new String[]{user.getEmail()});
        } else if(user instanceof Organizer){
            db.update(USER_TABLE_ORGANIZERS, cv, COLUMN_EMAIL + " = ?", new String[]{user.getEmail()});
        }
    }
}
