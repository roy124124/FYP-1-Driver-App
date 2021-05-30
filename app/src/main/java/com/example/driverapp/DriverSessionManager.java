package com.example.driverapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class DriverSessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    //User Session Variables
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_HADDRESS = "haddress";
    public static final String KEY_AGE = "age";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PHONE_NO = "phone_No";
    public static final String KEY_PASSWORD = "password";

    //Remember Me Variables
    private static final String IS_REMEMBERME = "IsRememberMe";

    public static final String KEY_SESSIONPHONE_NO = "phone_No";
    public static final String KEY_SESSIONPASSWORD = "password";


    //Constructor
    public DriverSessionManager(Context _context, String sessionName) {
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    //Login Sessions
    public void createLoginSession(String fullName, String email, String haddress, String age, String gender, String phone_No, String password) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_HADDRESS, haddress);
        editor.putString(KEY_AGE, age);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_PHONE_NO, phone_No);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_NAME, userSession.getString(KEY_NAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_HADDRESS, userSession.getString(KEY_HADDRESS, null));
        userData.put(KEY_AGE, userSession.getString(KEY_AGE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONE_NO, userSession.getString(KEY_PHONE_NO, null));

        return userData;
    }

    public boolean checkLogin() {

        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }


    //RememberMe Sessions
    public void createRememberMeSession(String phone_No, String password) {
        editor.putBoolean(IS_REMEMBERME, true);

        editor.putString(KEY_SESSIONPHONE_NO, phone_No);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();


        userData.put(KEY_SESSIONPHONE_NO, userSession.getString(KEY_SESSIONPHONE_NO, null));
        userData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe() {

        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }
}
