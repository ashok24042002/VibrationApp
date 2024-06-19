package com.example.vibrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;






public class MainActivity7 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView mTextView;
    private EditText machine;
    public float thresh=0;
    public String machineName;
    public boolean canCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        mTextView = findViewById(R.id.text_view);
        machine = findViewById(R.id.machineName);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("vibration");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference machinesRef = database.getReference("machines");

        Button setname = (Button) findViewById(R.id.setName);
        setname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canCheck=false;
                machineName = machine.getText().toString();
                Query query = machinesRef.orderByChild("name").equalTo(machineName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Loop through the results to find the machine with the specified name
                        for (DataSnapshot machineSnapshot : snapshot.getChildren()) {
                            // Retrieve the threshold value for the machine
                            Float threshold = machineSnapshot.child("threshold").getValue(Float.class);


                            if (threshold != null) {
                                // Do something with the threshold value
                                thresh = threshold != null ? threshold.floatValue() : 0.0f;
                                Log.d("Firebase", "Threshold value for machine " + machineName + ": " + threshold);
                                Toast.makeText(getApplicationContext(), "Threshhold is: "+thresh, Toast.LENGTH_SHORT).show();
                                canCheck=true;
                            } else {
                                // Handle the case where the threshold value is not present
                                Toast.makeText(getApplicationContext(), "Threshold value is not found for the given name", Toast.LENGTH_SHORT).show();
                                canCheck=false;
                                Log.d("Firebase", "Threshold value not found for machine " + machineName);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error case
                        canCheck=false;
                        Log.e("Firebase", "Error retrieving machine data", error.toException());

                    }
                });


            }
        });

        Button chkBtn = (Button) findViewById(R.id.checkVibr);
        chkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canCheck){
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            float value = dataSnapshot.getValue(Float.class);
                            mTextView.setText("Vibration: " + value);
                            if(value>thresh){
                                showAlert(MainActivity7.this, "Vibration Alert","The vibration is higher than threshold!");
                                //Toast.makeText(getApplicationContext(), "Value higher than Threshold!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("MainActivity", "onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });








    }
    public void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
