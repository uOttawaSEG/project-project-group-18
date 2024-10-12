package com.example.seg_project_d11;

import android.util.Log;

public class UserValidator {

    //methods to validate user inputs

    /**
     * validates the username entered by user. It has to contain at least one letter and all the characters
     * are either letters, numbers or underscore
     * @param  username username entered by user
     * @return true if name entered is valid, false otherwise
     */
    public static boolean validateName(String username){

        if (username.isBlank()){
            return false;
        }

        boolean containsLetter = false;
        for (char c: username.toCharArray()){

            if (Character.isLetter(c)){
                containsLetter = true;
            }
            if (!Character.isLetterOrDigit(c) && c != '_'){
                return false;
            }
        }
        Log.i("validateName",Boolean.toString(containsLetter));
        return containsLetter;

    }

    /**
     * validates lastname entered by user. It has to contain only letters.
     * @param lastname lastname entered
     * @return true if lastname entered is valid, false otherwise
     */
    public static boolean validateLastname(String lastname){
        if (lastname.isBlank()){
            return false;
        }
        for (char c: lastname.toCharArray()){

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

        if(email.isBlank()){
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

        if (password.isBlank()){
            return false;
        }

        boolean containsLetter = false;
        boolean containsNumber = false;


        for(char c: password.toCharArray()){

            if (!Character.isLetterOrDigit(c)){
                return false;
            }
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

}
