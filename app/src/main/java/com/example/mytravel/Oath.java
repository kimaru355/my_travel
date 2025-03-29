package com.example.mytravel;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Oath extends AppCompatActivity {
    private CheckBox oathCheckbox;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oath);

        oathCheckbox = findViewById(R.id.oathCheckbox);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setEnabled(false);
        oathCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> submitButton.setEnabled(isChecked));
        submitButton.setOnClickListener(v -> {
            Toast.makeText(Oath.this, "Oath accepted. Proceeding with application...", Toast.LENGTH_LONG).show();
            // Navigate to the next activity or perform form submission
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}