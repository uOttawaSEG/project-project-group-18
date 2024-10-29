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
    private static final int DATABASE_VERSION = 2;

    //user table
    private static final String USER_TABLE = "USERS_TABLE";

    private static final String COLUMN_FIRSTNAME = "firstName";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
    private static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ORGANIZATIONNAME = "organizationName";
    public static final String COLUMN_USERROLE = "userRole";



    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Attendees table
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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Attendees");
        db.execSQL("DROP TABLE IF EXISTS Organizers");
        onCreate(db);

    }

    // add an attendee data to the database
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (user instanceof Attendee){

            cv.put(COLUMN_EMAIL, user.getEmail());
            cv.put(COLUMN_FIRSTNAME, user.getFirstName());
            cv.put(COLUMN_LASTNAME, user.getLastName());
            cv.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
            cv.put(COLUMN_ADDRESS, user.getAddress());
            cv.put(COLUMN_PASSWORD, user.getPassword());
            cv.put(COLUMN_ORGANIZATIONNAME, "");
            cv.put(COLUMN_STATUS, user.getStatus());
            cv.put(COLUMN_USERROLE, "Attendee");

        }else if (user instanceof Organizer){
            cv.put(COLUMN_EMAIL, user.getEmail());
            cv.put(COLUMN_FIRSTNAME, user.getFirstName());
            cv.put(COLUMN_LASTNAME, user.getLastName());
            cv.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
            cv.put(COLUMN_ADDRESS, user.getAddress());
            cv.put(COLUMN_PASSWORD, user.getPassword());
            cv.put(COLUMN_ORGANIZATIONNAME, ((Organizer) user).getOrganizationName());
            cv.put(COLUMN_STATUS, user.getStatus());
            cv.put(COLUMN_USERROLE,"Organizer");

        }
        long insert = db.insert(USER_TABLE, null, cv); //if insert is -1, adding failed

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
