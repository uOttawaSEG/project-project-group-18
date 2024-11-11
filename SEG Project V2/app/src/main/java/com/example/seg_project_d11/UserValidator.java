package com.example.seg_project_d11;

import android.util.Log;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

public class UserValidator {

    //methods to validate user inputs
    /**
     * validates lastname entered by user. It has to contain only letters.
     * @param name lastname entered
     * @return true if lastname entered is valid, false otherwise
     */
    public static boolean validateName(String name){
        if (name.isBlank()){
            return false;
        }
        for (char c: name.toCharArray()){

            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;

    }

    /**
     * validates email entered by user. It should follow a format of --- @----.----
     * @param email email entered by user
     * @return true if email entered is valid, false otherwise
     */
    public static boolean validateEmail(String email){

        if(email == null || email.isBlank()){
            return false;
        }
        boolean containsAtSign = false;
        boolean containsDot = false;

        for (char c : email.toCharArray()){

            if( c == '@'){
                containsAtSign = true;
            }
            else if(c == '.'){
                containsDot = true;
            }
        }
        return containsAtSign && containsDot;

    }

    /**
     * validates password entered by user. password must contain at least one letter, and one number
     * @param password password entered by user
     * @return true if password entered is valid, false otherwise
     */
    public static boolean validatePassword(String password){

        if (password == null || password.isBlank()){
            return false;
        }

        boolean containsLetter = false;
        boolean containsNumber = false;


        for(char c: password.toCharArray()){

            //if (!Character.isLetterOrDigit(c)){
            //    return false;
            //}
            if (Character.isLetter(c)){
                containsLetter = true;
            }
            if (Character.isDigit(c)){
                containsNumber = true;
            }
        }
        return containsLetter && containsNumber;
    }

    /**
     * validates phone number entered by user. phone number must only contain numbers
     * @param phoneNumber phone number entered by user
     * @return true if phoneNumber entered is valid, false otherwise
     */
    public static boolean validatePhoneNumber(String phoneNumber){

        if (phoneNumber.isBlank()){
            return false;
        }

        for (char c: phoneNumber.toCharArray()){

            if (! Character.isDigit(c)){
                return false;
            }
        }

        return true;
    }

    public static boolean validateDate(String date){
        Log.i("Validate date", "YES");
        if (!date.contains("/")){
            return false;
        }
        String[] date_split = date.trim().split("/");

        if (date_split.length >3){
            return false;
        }

        String day = date_split[0];
        String month = date_split[1];
        String year = date_split[2];

        for (int i = 0; i < day.length(); i++) {
            if (!Character.isDigit(day.charAt(i))) {
                return false;
            }
        }
        for (int i = 0; i < month.length(); i++) {
            if (!Character.isDigit(month.charAt(i))) {
                return false;
            }
        }
        for (int i = 0; i < year.length(); i++) {
            if (!Character.isDigit(year.charAt(i))) {
                return false;
            }
        }

        int dayI= Integer.parseInt(day);
        int monthI= Integer.parseInt(month);
        int yearI= Integer.parseInt(year);

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(today);

        String[] formattedDate_split = formattedDate.split("-");
        int day_today = Integer.parseInt(formattedDate_split[2]);
        int month_today = Integer.parseInt(formattedDate_split[1]);
        int year_today = Integer.parseInt(formattedDate_split[0]);

        Log.i("Year Today", String.valueOf(year_today));
        Log.i("Year I", String.valueOf(yearI));


        if (dayI >31 || monthI > 12|| yearI > 2030 || yearI < year_today || (monthI < month_today && yearI <= year_today) || (dayI <= day_today && monthI <= month_today && yearI <= year_today)){
            return false;
        }

        return true;
    }

    public static boolean validateTime(String time){
        Log.i("Validate time", "YES");

        if (!time.contains(":")){
            return false;
        }
        String[] time_split = time.trim().split(":");

        if (time_split.length >2){
            return false;
        }

        String hour = time_split[0];
        String minute = time_split[1];

        for (int i = 0; i < hour.length(); i++) {
            if (!Character.isDigit(hour.charAt(i))) {
                return false;
            }
        }
        for (int i = 0; i < minute.length(); i++) {
            if (!Character.isDigit(minute.charAt(i))) {
                return false;
            }
        }

        if (minute.length()!=2){
            return false;
        }

        if (hour.length()!=2){
            return false;
        }

        int hourI= Integer.parseInt(hour);
        int minuteI= Integer.parseInt(minute);

        if (hourI > 23){
            return false;
        }

        if ( !(minuteI== 30 || minuteI== 00)){
            return false;
        }
        return true;

    }

}
