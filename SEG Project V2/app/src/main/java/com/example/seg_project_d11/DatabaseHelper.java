package com.example.seg_project_d11;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
    private static final String DATABASE_NAME = "Userdata.db";

    //database version
    private static final int DATABASE_VERSION = 6;

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
    private static final String COLUMN_EVENT_CHOICE = "choice";

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
                + COLUMN_EVENT_CHOICE + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_ORGANIZER_EMAIL + ") REFERENCES " + USER_TABLE + "(" + COLUMN_EMAIL + ")"
                + " ON DELETE CASCADE" // If the organizer is deleted, their events will be deleted
                + ");";

        db.execSQL(CREATE_EVENTS_TABLE);

        //create EventRequest table
        //each time an attendee wants to request for an event, it gets saved to this table
        //so for each event request there can be zero to many attendees and it is also linked to a unique event ID
        String CREATE_EVENT_REQUESTS_TABLE = "CREATE TABLE " + TABLE_EVENT_REQUESTS + "("
                + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ATTENDEE_EMAIL + " TEXT, " // Foreign key to Users table (email) for attendee
                + COLUMN_REQUESTED_EVENT_ID + " INTEGER, " // Foreign key to Events table
                + COLUMN_REQUEST_STATUS + " TEXT DEFAULT 'Pending' , " // Default to 'Pending'
                + "FOREIGN KEY(" + COLUMN_ATTENDEE_EMAIL + ") REFERENCES " + USER_TABLE + "(" + COLUMN_EMAIL + ")"
                + " ON DELETE CASCADE, "
                + "FOREIGN KEY(" + COLUMN_REQUESTED_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + ")"
                + " ON DELETE CASCADE"
                + ");";
        db.execSQL(CREATE_EVENT_REQUESTS_TABLE);


    }
    public boolean checkEventExists(String eventId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUsers = db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_REQUESTED_EVENT_ID + " = ?", new String[] {eventId});
        if(cursorUsers.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
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

    //fetch a list of users(attendees and organizers) from the database based on their status
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

    //add an event to the Event table by specifying the event object and the organizer email
    public boolean addEvent(Event event, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_TITLE, event.getTitle());
        cv.put(COLUMN_EVENT_ADDRESS, event.getEventAddress());
        cv.put(COLUMN_EVENT_DATE, event.getDate());
        cv.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
        cv.put(COLUMN_EVENT_START_TIME, event.getStartTime());
        cv.put(COLUMN_EVENT_END_TIME, event.getEndTime());
        cv.put(COLUMN_ORGANIZER_EMAIL, email);
        cv.put(COLUMN_EVENT_CHOICE, event.getAcceptChoice());

        // Insert the event into the database and get the generated eventID
        long insertID = db.insert(TABLE_EVENTS, null, cv); // insertID is the generated eventID

        db.close();

        // If the insertion failed (insertID is -1), return false
        if (insertID == -1) {
            return false;
        } else {
            // If insertion is successful, set the eventID on the Event object
            event.setEventID((int) insertID); // Ensure eventID is updated with the auto-incremented ID
            return true;
        }

    }

    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the event based on the eventId
        int rowsDeleted = db.delete(TABLE_EVENTS, COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(eventId)});

        db.close();

        return rowsDeleted != 1;
    }


    //add an event request to EventRequests table by specifying an attendee email and the requested Event ID
    public void addEventRequest(RequestEvent request){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ATTENDEE_EMAIL, request.getAttendeeEmail());
        cv.put(COLUMN_REQUESTED_EVENT_ID, request.getEventID());
        cv.put(COLUMN_REQUEST_STATUS, request.getStatus());

        db.insert(TABLE_EVENT_REQUESTS, null, cv);
        db.close();
    }


    //return the status of the event request specified by the attendee email and the event request ID
    public String getEventRequesStatus(String email, Integer eventRequestID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_ATTENDEE_EMAIL + " = ? AND " + COLUMN_REQUESTED_EVENT_ID + " = ?", new String[] {email, String.valueOf(eventRequestID)});
        String eventRequestStatus ="null";

        if(cursor.moveToFirst()){
            do{
                eventRequestStatus = cursor.getString(3);

            }while(cursor.moveToNext());
        }else{
            //no event request found with the specified email and event request id

        }
        cursor.close();
        db.close();
        return eventRequestStatus;
    }

    /*public boolean checkEventRegistration(String attendeeEmail, int eventId){
        SQLiteDatabase db = this.getReadableDatabase();
        String curDate = null;
        String curStart = null;
        String curEnd = null;

        Cursor cursorEvent= db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_REQUESTED_EVENT_ID + " = ?", new String[] {String.valueOf(eventId)});

        if (cursorEvent.moveToFirst()){
            curStart = cursorEvent.getString(4);
            curEnd = cursorEvent.getString(5);
            curDate = cursorEvent.getString(3);
        }
        //Log.d("databaseHelper: ", "Event Start: "+ curStart + ", Event End: " + curEnd );
        cursorEvent.close();

        if (curDate==null||curStart==null||curEnd==null){
            return false;
        }

        Cursor cursorRegistered = db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_ATTENDEE_EMAIL + " = ?", new String[]{attendeeEmail});
        if (conflictChecker(cursorRegistered, curDate,curStart,curEnd)){
            cursorRegistered.close();
            return false;
        }
        cursorRegistered.close();


        Cursor cursorRequested = db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_ATTENDEE_EMAIL + " = ?", new String[]{attendeeEmail});
        if (conflictChecker(cursorRequested, curDate,curStart,curEnd)){
            cursorRegistered.close();
            return false;
        }
        cursorRequested.close();
        return false;
    }
*/

    public boolean checkEventRegistration(String attendee, Event event) {
        String date = event.getDate();
        String startTime = event.getStartTime();
        String endTime = event.getStartTime();


        ArrayList<Event> registeredEvents = getAttendeeEvents(attendee);

        for (Event registeredevent: registeredEvents){
            String eventDate = registeredevent.getDate();
            String eventStartTime = registeredevent.getStartTime();
            //String eventEndTime = registeredevent.getStartTime();

            if(date.equals(eventDate) && (startTime.equals(eventStartTime) || (Integer.parseInt(eventStartTime) >= Integer.parseInt(startTime) && Integer.parseInt(eventStartTime) <= Integer.parseInt(endTime)))){
                return false;
            }
        }
        return true;
    }


    /*public boolean conflictChecker(Cursor c, String curDate, String curStart, String curEnd){
        while (c.moveToNext()){
            String otherDate =c.getString(3);
            String otherStart =c.getString(4);
            String otherEnd =c.getString(5);

            if (otherDate.equals(curDate)){
                if (!(curEnd.compareTo(otherStart)<=0 || curStart.compareTo(otherEnd)>=0)){
                    return true;
                }
            }
        }
        return false;
    }*/



    //get all events for a specific organizer by specifying their user name(email)
    public List<Event> getEventsForOrganizer(String email, String type){
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Event event;

        Cursor cursorEvent = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ORGANIZER_EMAIL + " = ?", new String[]{email});

        if (cursorEvent.moveToFirst()){

            do{
                int eventID = cursorEvent.getInt(0);
                String title= cursorEvent.getString(1);
                String description= cursorEvent.getString(2);
                String date= cursorEvent.getString(3);
                String startTime= cursorEvent.getString(4);
                String endTime= cursorEvent.getString(5);
                String eventAddress= cursorEvent.getString(6);
                int choice = cursorEvent.getInt(8);

                if (type.equals("Upcoming")){
                    Log.d("DBHelper: Type", "upcoming");
                    if(UserValidator.validateDate(date)){
                        event = new Event(eventID, title, description, date, startTime, endTime, eventAddress,choice);
                        events.add(event);
                    }
                }else if(type.equals("Past")){
                    Log.d("DBHelper: Type", "past");
                    if(!UserValidator.validateDate(date)){
                        event = new Event(eventID, title, description, date, startTime, endTime, eventAddress,choice);
                        events.add(event);
                    }
                }else{
                    Log.d("ERROR", "Enter Upcoming/past");
                }

            }while(cursorEvent.moveToNext());
        }else{
            Log.d("ERROR", "No events for organizer");
        }
        cursorEvent.close();
        db.close();
        return events;
    }


    //get all events created by all organizers
    public List<Event> getAvailableEvents(){
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                int eventID = cursor.getInt(0);
                String title= cursor.getString(1);
                String description= cursor.getString(2);
                String date= cursor.getString(3);
                String startTime= cursor.getString(4);
                String endTime= cursor.getString(5);
                String eventAddress= cursor.getString(6);
                int choice = cursor.getInt(8);

                if(UserValidator.validateDate(date)){
                    Event event = new Event(eventID, title, description, date, startTime, endTime, eventAddress,choice);
                    events.add(event);
                }

            }while(cursor.moveToNext());
        }else{
            //no events have been created yet
        }
        cursor.close();
        db.close();
        return events;

    }

    public void deleteEventRequest(String username, int eventID){
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the event based on the eventId
        int rowsDeleted = db.delete(
                TABLE_EVENT_REQUESTS,
                COLUMN_REQUESTED_EVENT_ID + " = ? AND " + COLUMN_ATTENDEE_EMAIL + " = ?",
                new String[]{String.valueOf(eventID), username}
        );
        db.close();

    }
    //returns a list of Attendees who have requested for a specific event (passed as eventID)
    public List<Attendee> getAllAttendeeEventRequests(Integer eventID, String status){
        List<Attendee> attendees= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_REQUESTED_EVENT_ID + " = ? AND status = ?",
                new String[]{String.valueOf(eventID), status}
        );

        if (cursor.moveToFirst()){

            do{
                String attendeeUserName = cursor.getString(1);
                Attendee attendee = getAttendee(attendeeUserName);
                attendees.add(attendee);

            }while(cursor.moveToNext());
        }else{
            //there is no event requests yet made for this specific event
        }
        cursor.close();
        db.close();
        return attendees;

    }
    // takes in an Attendee email, return list of their requested events
    public ArrayList<Event> getAttendeeEvents(String email) {
        Log.i("DatabaseHelper", "begginig " +  email);
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Integer> eventsID = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorRequest = db.rawQuery("SELECT * FROM " + TABLE_EVENT_REQUESTS + " WHERE " + COLUMN_ATTENDEE_EMAIL + " = ?", new String[]{email});
        Log.i("DatabaseHelper", String.valueOf(cursorRequest.moveToFirst()));
        if (cursorRequest.moveToFirst()) {
            Log.i("DatabaseHelper", "first part" );
            do {
                int requestEventID = cursorRequest.getInt(2);
                eventsID.add(requestEventID);

            } while (cursorRequest.moveToNext());
        } else {
            // No events found for the attendee
        }
        cursorRequest.close();

        for (int i = 0; i < eventsID.size(); i++){
            Log.i("DatabaseHelper", "eventsID" );
            String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EVENT_ID + " = ?";
            Cursor cursorEvent = db.rawQuery(query, new String[]{String.valueOf(eventsID.get(i))});

            if (cursorEvent.moveToFirst()) {
                do {
                    int eventID = cursorEvent.getInt(0);
                    String title= cursorEvent.getString(1);
                    String description= cursorEvent.getString(2);
                    String date= cursorEvent.getString(3);
                    String startTime= cursorEvent.getString(4);
                    String endTime= cursorEvent.getString(5);
                    String eventAddress= cursorEvent.getString(6);
                    int choice = cursorEvent.getInt(8);
                    Event event = new Event(eventID, title, description, date, startTime, endTime, eventAddress, choice);
                    events.add(event);

                } while (cursorRequest.moveToNext());
            } else {
                // No events found for the attendee
            }
            cursorEvent.close();
        }
        db.close();

        Log.i("DatabaseHelper", "GetAttendeeEvents" );
        for (Event event: events){
            Log.i("Requested Event", event.getTitle());
        }


        return events;
    }
    // returns a list of upcoming events an attendee may request for
    /* TO DISCUSS
    public List<Event> getUpcomingEvents(String email){
        List<Event> upcomingEvents = getAllEvents();
        List<Event> requestedEvents = getAttendeeEvents(email);
        if (requestedEvents.size() == 0){
            return upcomingEvents;
        }
        for (int i = 0; i < requestedEvents.size(); i++){
            if (upcomingEvents.contains(requestedEvents.get(i))){
                upcomingEvents.remove(requestedEvents.get(i));
            }
        }

        return upcomingEvents;
    }*/

    /* Enah needs help here:

    // updates an Attendee's list of requested events
    public List<Event> updateAttendeeEvents(String email) {

    }

    public deleteAttendeeEvents(Event event) {

    }

    */


    //returns an Attendee objbect by specifying the attendee username(email)
    public Attendee getAttendee(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE+ " WHERE " + COLUMN_EMAIL + " = ?", new String[]{userName});

        if (cursor.moveToFirst()){
            String email = cursor.getString(0);
            String firstName = cursor.getString(1);
            String lastname = cursor.getString(2);
            String phoneNumber =cursor.getString(3);
            String address =cursor.getString(4);
            String password = cursor.getString(5);
            String userRole = cursor.getString(7);
            String status = cursor.getString(8);

            Attendee attendee = new Attendee(firstName, lastname, email, password, phoneNumber, address, status, userRole);
            return attendee;
        }else{
            //attendee with this username does not exist in the database
            return null;
        }

    }

    public Organizer getOrganizer(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE+ " WHERE " + COLUMN_EMAIL + " = ?", new String[]{userName});

        if (cursor.moveToFirst()){
            String email = cursor.getString(0);
            String firstName = cursor.getString(1);
            String lastname = cursor.getString(2);
            String phoneNumber =cursor.getString(3);
            String address =cursor.getString(4);
            String password = cursor.getString(5);
            String organizationName = cursor.getString(6);
            String userRole = cursor.getString(7);
            String status = cursor.getString(8);

            Organizer organizer = new Organizer(firstName, lastname, email, password, phoneNumber, address,organizationName, status, userRole);
            return organizer;
        }else{
            //attendee with this username does not exist in the database
            return null;
        }

    }


    //updates the status of the event request given an attendee Email and the eventID and the new Status
    public void updateEventRequestStatus(String attendeeEmail, Integer eventID, String newStatus){
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_EVENT_REQUESTS + " SET " + COLUMN_REQUEST_STATUS + " = ? "
                + "WHERE " + COLUMN_ATTENDEE_EMAIL + " = ? AND " + COLUMN_REQUESTED_EVENT_ID + " = ?";

        // Execute the query
        //method bindString() binds a string value to corresponding ? placeholder in SQL query
        SQLiteStatement statement = db.compileStatement(updateQuery);
        statement.bindString(1, newStatus); // Bind the new status (Accepted or Rejected)
        statement.bindString(2, attendeeEmail); // Bind the attendee's email
        statement.bindLong(3, eventID); // Bind the event

        statement.executeUpdateDelete(); // Execute the update query

        db.close();

    }
    //checks if there is any events created or not/ used in populate database class
    public boolean areEventsInitialized() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_EVENTS + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        boolean hasData = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return hasData;
    }



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

        /*//check if there exists a staus/if user even exits:
        if (findUser.moveToFirst()){
            status = findUser.getString(8);;

        }*/
        findUser.moveToFirst();
        status = findUser.getString(8);
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
        Log.d("databaseHelper: ", "UserRole:"+ userRole);
        findUser.close();
        return userRole;

    }

    public void approveAllAttendees(int eventID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put( COLUMN_REQUEST_STATUS , "Accepted");
        db.update(TABLE_EVENT_REQUESTS, cv, COLUMN_REQUESTED_EVENT_ID + " = ?", new String[]{String.valueOf(eventID)});
        db.close();
    }

    //search for events

    /**
     * @param keyword
     * @return a list of events that match keyword
     */
    public List<Event> searchEvents(String keyword) {
        List<Event> events = new ArrayList<>();
       SQLiteDatabase db = this.getReadableDatabase();

        // Query to search for the keyword in title or description
        String query = "SELECT * FROM events WHERE title LIKE ? OR description LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            do {

                // Get the data from the cursor
               String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
               String description= cursor.getString(cursor.getColumnIndexOrThrow("description"));
               String date =cursor.getString(cursor.getColumnIndexOrThrow("date"));
               String startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
               String endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
               String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
               Integer choice = cursor.getColumnIndexOrThrow("choice");

                // Create Event objects from information
                Event event = new Event(title, description, date, startTime, endTime, address,choice);
                events.add(event); // Add to the list of events
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

}
