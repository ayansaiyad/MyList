package com.example.mylist;
import android.content.Context;
import android.content.SharedPreferences;
//handles loging/logout state using private app storage
public class SessionPrefs{
    private static final String PREFS_NAME = "mylist_session"; // Preferences file name
    private static final String KEY_LOGGED_IN = "logged_in"; // Key for login status
    private static final String KEY_USERNAME = "username"; // Key for storing username
    private final SharedPreferences sp;
    public SessionPrefs(Context c){
        sp = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void login(){ sp.edit().putBoolean(KEY_LOGGED_IN, true).apply(); }
    public void logout(){ sp.edit().clear().apply(); }
    public boolean isLoggedIn(){ return sp.getBoolean(KEY_LOGGED_IN, false); }
}
