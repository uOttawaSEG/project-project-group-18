package com.example.seg_project_d11;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;


import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
    private static final String DATABASE_NAME = "Userdata.db";

    //database version
    private static final int DATABASE_VERSION = 5;

    //user table and columns
    private static final String USER_TABLE = "USERS_TABLE";

    private static final String COLUMN_FIRSTNAME = "firstName";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email"; //primary key
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
    private static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ORGANIZATIONNAME = "organizationName";
    public static final String COLUMN_USERROLE = "userRole";

    //Events table and columns
    private static final String TABLE_EVENTS= "Events";
    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_TITLE = "title";
    private static final String COLUMN_EVENT_DESCRIPTION = "description";
    private static final String COLUMN_EVENT_DATE = "date";
    private static final String COLUMN_EVENT_START_TIME = "start_time";
    private static final String COLUMN_EVENT_END_TIME = "end_time";
    private static final String COLUMN_EVENT_ADDRESS = "address";
    private static final String COLUMN_ORGANIZER_EMAIL = "organizer_email"; // Foreign key to Users table


    //EventRequests table and columns
    private static final String TABLE_EVENT_REQUESTS = "EventRequests";
    private static final String COLUMN_REQUEST_ID = "request_id";
    private static final String COLUMN_ATTENDEE_EMAIL = "attendee_email";  // Foreign key to Users table for attendee
    private static final String COLUMN_REQUESTED_EVENT_ID = "requested_event_id";  // Foreign key to Events table
    private static final String COLUMN_REQUEST_STATUS = "status"; // "Pending", "Approved", "Rejected"


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                +COLUMN_PHONENUMBER + " TEXT, "
                +COLUMN_ADDRESS + " TEXT, "
                +COLUMN_PASSWORD + " TEXT, "
                +COLUMN_ORGANIZATIONNAME + " TEXT, "
                +COLUMN_USERROLE + " TEXT, "
                +COLUMN_STATUS + " TEXT DEFAULT 'Pending');";

        db.execSQL(CREATE_USERS_TABLE);

        //create Events table
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENT_TITLE + " TEXT, "
                + COLUMN_EVENT_DESCRIPTION + " TEXT, "
                + COLUMN_EVENT_DATE + " TEXT, "
                + COLUMN_EVENT_START_TIME + " TEXT, "
                + COLUMN_EVENT_END_TIME + " TEXT, "
                + COLUMN_EVENT_ADDRESS + " TEXT, "
                + COLUMN_ORGANIZER_EMAIL + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_ORGANIZER_EMAIL + ") REFERENCES " + USER_TABLE + "(" + COLUMN_EMAIL + ")"
                + " ON DELETE CASCADE" // If the organizer is deleted, their events will be deleted
                + ");";

        db.execSQL(CREATE_EVENTS_TABLE);

        //create EventRequest table
        String CREATE_EVENT_REQUESTS_TABLE = "CREATE TABLE " + TABLE_EVENT_REQUESTS + "("
                + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ATTENDEE_EMAIL + " TEXT, " // Foreign key to Users table (email) for attendee
                + COLUMN_REQUESTED_EVENT_ID + " INTEGER, " // Foreign key to Events table
                + COLUMN_REQUEST_STATUS + " TEXT DEFAULT 'Pending', " // Default to 'Pending'
                + "FOREIGN KEY(" + COLUMN_ATTENDEE_EMAIL + ") REFERENCES " + USER_TABLE + "(" + COLUMN_EMAIL + ")"
                + " ON DELETE CASCADE, "
                + "FOREIGN KEY(" + COLUMN_REQUESTED_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + ")"
                + " ON DELETE CASCADE"
                + ");";
        db.execSQL(CREATE_EVENT_REQUESTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    // add a user to the user table
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_FIRSTNAME, user.getFirstName());
        cv.put(COLUMN_LASTNAME, user.getLastName());
        cv.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
        cv.put(COLUMN_ADDRESS, user.getAddress());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_STATUS, user.getStatus());

        if (user instanceof Attendee){
            cv.put(COLUMN_ORGANIZATIONNAME, "");
            cv.put(COLUMN_USERROLE, "Attendee");

        }else if (user instanceof Organizer){
            cv.put(COLUMN_ORGANIZATIONNAME, ((Organizer) user).getOrganizationName());
            cv.put(COLUMN_USERROLE,"Organizer");
        }
        long insert = db.insert(USER_TABLE, null, cv); //if insert is -1, adding failed
        db.close();
        if(insert ==-1){
            return false;
        }else{
            return true;
        }
    }

    //fetch a list of users(attendees and organizers) from the database

    public List<User> getAllRequests(String stat){
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //fetch attendees
        //rawQuery returns a cursor
        Cursor cursorUsers = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_STATUS + " = ?", new String[] {stat});

        //move to the first result in the result set(cursor)
        if(cursorUsers.moveToFirst()){
            //loop through the result and create a new user and put user in the list.
            do{
                String email = cursorUsers.getString(0);
                String firstName = cursorUsers.getString(1);
                String lastname = cursorUsers.getString(2);
                String phoneNumber =cursorUsers.getString(3);
                String address =cursorUsers.getString(4);
                String password = cursorUsers.getString(5);
                String organizationName = cursorUsers.getString(6);
                String userRole = cursorUsers.getString(7);
                String status = cursorUsers.getString(8);

                User user;
                if(userRole.equals("Attendee")){
                    user = new Attendee(firstName, lastname, email, password, phoneNumber, address, status, userRole);
                }else{
                    user = new Organizer(firstName, lastname, email, password, phoneNumber, address, organizationName, status, userRole);
                }

                users.add(user);

            } while(cursorUsers.moveToNext());
        }else{
            //failure. do not add anything to the list.
        }

        cursorUsers.close();
        db.close();
        return users;
    }

    //add an event to the Event table
    public boolean addEvent(Event event, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_TITLE, event.getTitle());
        cv.put(COLUMN_EVENT_ADDRESS, event.getEventAddress());
        cv.put(COLUMN_EVENT_DATE, event.getDate());
        cv.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
        cv.put(COLUMN_EVENT_END_TIME, event.getStartTime());
        cv.put(COLUMN_EVENT_END_TIME, event.getEndTime());
        cv.put(COLUMN_ORGANIZER_EMAIL, email);

        long insert = db.insert(TABLE_EVENTS, null, cv); //if insert is -1, adding failed
        db.close();
        if(insert ==-1){
            return false;
        }else{
            return true;
        }

    }

    //get all events for a specific organizer by specifying their user name(email)
    public List<Event> getEventsForOrganizer(String email){
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorEvent = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ORGANIZER_EMAIL + " = ?", new String[]{email});

        if (cursorEvent.moveToFirst()){

            do{

                String title= cursorEvent.getString(1);
                String description= cursorEvent.getString(2);
                String date= cursorEvent.getString(3);
                String startTime= cursorEvent.getString(4);
                String endTime= cursorEvent.getString(5);
                String eventAddress= cursorEvent.getString(6);

                Event event = new Event(title, description, date, startTime, endTime, eventAddress);
                events.add(event);

            }while(cursorEvent.moveToNext());
        }else{
            //there is no events associated with the organizer email given
        }
        cursorEvent.close();
        db.close();
        return events;
    }


    //get all events created by all organizers
    public List<Event> getAllEvents(){
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                String title= cursor.getString(1);
                String description= cursor.getString(2);
                String date= cursor.getString(3);
                String startTime= cursor.getString(4);
                String endTime= cursor.getString(5);
                String eventAddress= cursor.getString(6);

                Event event = new Event(title, description, date, startTime, endTime, eventAddress);
                events.add(event);
            }while(cursor.moveToNext());
        }else{
            //no events have been created yet
        }
        cursor.close();
        db.close();
        return events;

    }



    /*

    public void updateEventList(String username, Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Checkpoint", "before cursor");
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ORGANIZATIONEVENTS + " FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{username});
        String updatedEventList = null;
        Log.i("Checkpoint", "after cursor");
        //check if there exists a staus/if user even exits:
        if (cursor.moveToFirst()){
            @SuppressLint("Range") String eventsJson = cursor.getString(cursor.getColumnIndex(COLUMN_ORGANIZATIONEVENTS));
            // Deserialize the existing event list (if any)
            ArrayList<Event> eventList = new ArrayList<>();
            if (eventsJson != null && !eventsJson.isEmpty()) {
                // Deserialize the JSON string into an ArrayList<Event>
                Gson gson = new Gson();
                eventList = gson.fromJson(eventsJson, new TypeToken<ArrayList<Event>>(){}.getType());
            }

            // Add the new event to the list
            eventList.add(event);

            // Serialize the updated list back to a JSON string
            updatedEventList = new Gson().toJson(eventList);

        }
        // Update the database with the new event list
        if (updatedEventList != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ORGANIZATIONEVENTS, updatedEventList);  // Store the updated event list as a JSON string

            // Update the user's event list in the database
            int rowsAffected = db.update(USER_TABLE, contentValues, COLUMN_EMAIL + " = ?", new String[]{username});

            if (rowsAffected > 0) {
                System.out.println("Event list updated successfully for user: " + username);
            } else {
                System.out.println("No user found with the email: " + username);
            }
        }

        cursor.close();
        db.close();
    }



    public ArrayList<Event> getAllUpcomingEvents(){
        ArrayList<Event> upcomingEvents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Query to get all organizers
        String query = "SELECT " + COLUMN_ORGANIZATIONEVENTS + " FROM " + USER_TABLE +
                " WHERE " + COLUMN_USERROLE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] { "Organizer" });

        if (cursor.moveToFirst()){
            do{
                String eventsJson = cursor.getString(0); // get the organizationEvents column
                // Deserialize the JSON string to ArrayList<Event>
                if (eventsJson != null && !eventsJson.isEmpty()) {
                    ArrayList<Event> events = new Gson().fromJson(eventsJson, new TypeToken<List<Event>>(){}.getType());
                    // Add each event to the combined list
                    if (events != null) {
                        upcomingEvents.addAll(events);
                    }
                }

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return upcomingEvents;

    }

    public ArrayList<Event> getEventList(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ORGANIZATIONEVENTS + " FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{username});
        //check if there exists a staus/if user even exits:
        ArrayList<Event> eventList = new ArrayList<>();
        if (cursor.moveToFirst()){
            @SuppressLint("Range") String eventsJson = cursor.getString(cursor.getColumnIndex(COLUMN_ORGANIZATIONEVENTS));
            // Deserialize the existing event list (if any)
            if (eventsJson != null && !eventsJson.isEmpty()) {
                // Deserialize the JSON string into an ArrayList<Event>
                Gson gson = new Gson();
                eventList = gson.fromJson(eventsJson, new TypeToken<ArrayList<Event>>() {
                }.getType());
            }
        }
        return eventList;
    }

     */

    //method to update the user's status
    public void updateUserStatus(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, user.getStatus());

        db.update(USER_TABLE, cv, COLUMN_EMAIL + " = ?", new String[]{user.getEmail()});

    }


    public String getUserStatus(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String status = null;

        Cursor findUser = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});


        //check if there exists a staus/if user even exits:
        if (findUser.moveToFirst()){
            status = findUser.getString(8);;

        }
        findUser.close();
        return status;

    }

    public String getUserRole(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String userRole = null;

        Cursor findUser = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});



        if (findUser.moveToFirst()){
            userRole = findUser.getString(7);;

        }
        findUser.close();
        return userRole;

    }


    //method to check if User is accepted
    /*public boolean checkUserAccepted(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Check for user in the Attendee List
        Cursor findUser = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (findUser.getCount()>0){ //if greater than 0, than account exists
            findUser.close();
            return true;
        }
        findUser.close(); //Account isn't found so, close cursor

        return false;

    }

    //method to check if user is pending (very similar to checking for accepted)
    public boolean checkUserPending(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //Check for user in the Attendee List
        Cursor findUser = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_STATUS + " = 'Pending'" + " = ? AND " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?");
        if (findUser.getCount()>0){ //if greater than 0, than account exists
            findUser.close();
            return true;
        }
        findUser.close(); //Account isn't found so, close cursor
        return false;
    }
*/


}
