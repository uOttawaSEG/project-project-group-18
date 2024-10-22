package com.example.seg_project_d11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
}
