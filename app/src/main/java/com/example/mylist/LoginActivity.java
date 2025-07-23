package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

// handles user authentication UI and logic

public class LoginActivity extends AppCompatActivity {

    // Hardcoded (demo) credentials
    private static final String DEMO_USERNAME = "ayan";
    private static final String DEMO_PASSWORD = "ayan123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEt = findViewById(R.id.edit_username);
        EditText passwordEt = findViewById(R.id.edit_password);
        Button loginBtn = findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(v -> {
            String username = usernameEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (username.equals(DEMO_USERNAME) && password.equals(DEMO_PASSWORD)) {
                // Save session (using SessionPrefs)
                SessionPrefs session = new SessionPrefs(this);
                session.login();

                // Go to MainActivity (pending tasks)
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Prevent returning to login with back button
            } else {
                // Show error message (user feedback)
                Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
