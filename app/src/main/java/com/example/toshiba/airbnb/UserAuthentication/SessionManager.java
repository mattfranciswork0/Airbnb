package com.example.toshiba.airbnb.UserAuthentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.toshiba.airbnb.Explore.MenuActivity;

/**
 * Created by TOSHIBA on 22/08/2017.
 */

public class SessionManager {
    private Context context;
    private SharedPreferences sessionSP;
    private SharedPreferences.Editor editor;
    public static final String SESSION_SP = "SESSION_SP";

    private final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_ID = "USER_ID";
    public static final String EMAIL = "EMAIL";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String PHONE_NUM = "PHONE_NUM";

    public SessionManager(Context context){
        this.context = context;
        sessionSP = this.context.getSharedPreferences(SESSION_SP, Context.MODE_PRIVATE);
        editor = sessionSP.edit();
    }

    public void createLoginSession(int id, String email, String firstName, String lastName, String phoneNum){
        editor.putBoolean(IS_LOGIN, true);

        editor.putInt(USER_ID, id);

        editor.putString(EMAIL, email);

        editor.putString(FIRST_NAME, firstName);

        editor.putString(LAST_NAME, lastName);

        editor.putString(PHONE_NUM, phoneNum);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return sessionSP.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.apply();

        // After logout redirect user to Loing Activity
        Intent intent = new Intent(context, WelcomeActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(intent);
    }

    public void checkLogin() {
        // Check login status
        if (isLoggedIn()) {
            Intent intent = new Intent(context, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
