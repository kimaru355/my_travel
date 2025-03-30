package com.example.mytravel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Retrieve saved credentials
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Redirect to Flights page

            // Save to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            boolean hasVisa = sharedPreferences.getBoolean("hasVisa", false);

            // Redirect to main page or dashboard
            if (hasVisa) {
                Intent intent = new Intent(MainActivity.this, Flights.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, Visa.class);
                startActivity(intent);
            }
            finish();
        } else {
            // Redirect to Login page
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // Close MainActivity
        }
    }
}
