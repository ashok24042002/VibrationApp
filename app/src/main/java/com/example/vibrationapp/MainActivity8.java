package com.example.vibrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity8 extends AppCompatActivity {

    private EditText editText;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("vibration");

        Button btn = findViewById(R.id.btnSet);
        editText = findViewById(R.id.editTextNumberDecimal);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float number = Float.parseFloat(editText.getText().toString());
                    myRef.setValue(number);
                } catch (NumberFormatException e) {
                    // Handle invalid input here
                }
            }
        });
    }
}
