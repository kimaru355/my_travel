package com.example.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Visa extends AppCompatActivity {

    private static final int PICK_IMAGE_ID = 1;
    private static final int PICK_IMAGE_PASSPORT = 2;

    private EditText fullName, dateOfBirth, zipCode;
    private Spinner nationality, country;
    private ImageView idImageView, passportImageView;
    private Button uploadId, uploadPassport, submitApplication;
    private Uri idUri, passportUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa);

        fullName = findViewById(R.id.fullName);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        nationality = findViewById(R.id.nationality);
        zipCode = findViewById(R.id.zipCode);

        idImageView = findViewById(R.id.idImageView);
        passportImageView = findViewById(R.id.passportImageView);
        uploadId = findViewById(R.id.uploadId);
        uploadPassport = findViewById(R.id.uploadPassport);
        submitApplication = findViewById(R.id.submitApplication);

        setupDropdowns();

        uploadId.setOnClickListener(v -> selectImage(PICK_IMAGE_ID));
        uploadPassport.setOnClickListener(v -> selectImage(PICK_IMAGE_PASSPORT));
        submitApplication.setOnClickListener(v -> submitApplication());
    }

    private void setupDropdowns() {
        String[] countries = {"United States", "Canada", "United Kingdom", "Germany", "France",
                "Italy", "Spain", "Netherlands", "Australia", "India", "China", "Japan",
                "South Africa", "Brazil", "Mexico", "Kenya", "Nigeria", "Russia", "Sweden",
                "Norway", "Denmark", "Finland", "Switzerland", "New Zealand", "Singapore",
                "South Korea", "Indonesia", "Thailand", "Philippines", "Malaysia", "Egypt",
                "Argentina", "Colombia", "Chile", "Peru", "Saudi Arabia", "United Arab Emirates",
                "Vietnam", "Pakistan", "Bangladesh", "Ukraine", "Poland", "Belgium", "Portugal"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        nationality.setAdapter(adapter);
    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (requestCode == PICK_IMAGE_ID) {
                idUri = selectedImage;
                idImageView.setImageURI(idUri);
                Toast.makeText(this, "ID Uploaded Successfully", Toast.LENGTH_SHORT).show();
            } else if (requestCode == PICK_IMAGE_PASSPORT) {
                passportUri = selectedImage;
                passportImageView.setImageURI(passportUri);
                Toast.makeText(this, "Passport Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitApplication() {
        Toast.makeText(this, "Your visa application has been submitted successfully!", Toast.LENGTH_LONG).show();

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasVisa", true);
        editor.apply();
        Intent intent = new Intent(Visa.this, Flights.class);
        startActivity(intent);
    }
}