package com.example.mylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
// Decide  where user starts : Either login or main tasks
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionPrefs session = new SessionPrefs(this);

        if (session.isLoggedIn()) {
            // Already logged in -> go to Main
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // Not logged in -> go to Login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
