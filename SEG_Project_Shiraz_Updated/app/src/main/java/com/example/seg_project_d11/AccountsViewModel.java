package com.example.seg_project_d11;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class AccountsViewModel extends ViewModel{
    public static String organizerName, organizerPhone, organizerLastName, organizerAddress, organizationName, organizerEmail, organizerPassword = null;
    public static String attendeeName, attendeePhone, attendeeLastName, attendeeAddress, attendeeEmail, attendeePassword = null;
    Context context;
    public AccountsViewModel(Context c){
        this.context = c;
    }
    //Creates text file (if not created already), and writes onto it the user information separated by "//"
    public void saveUserInfo(String registrationInformation) {
        //creates file
        File userInfo = new File(context.getFilesDir(), "UserInfo.txt");

        try{
            //Checks if the file has already been created
            if (userInfo.createNewFile()) {
                Log.d("File creation", "File created: " + userInfo.getName());
            } else {
                Log.d("File creation", "File already exists, name: " + userInfo.getName());
            }

            //Initialized fileWriter and buffered writer, which serves to write the user info into the text file
            FileWriter fileWriter= new FileWriter(userInfo, true);
            BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);
            bufferedWriter.write(registrationInformation);
            //jumps to next line on file
            bufferedWriter.newLine();
            //closes writer
            bufferedWriter.close();

            //Tests file contents, prints everything on file currently
            try {
                Scanner myReader = new Scanner(userInfo);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    Log.d("File contents", data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                Log.d("FILE ERROR","An error occurred.");
            }

        }catch(Exception e){
            Log.d("File Creation", "Error creating file"+ e);
        }
    }

}
