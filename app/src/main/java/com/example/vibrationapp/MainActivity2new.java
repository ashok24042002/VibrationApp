package com.example.vibrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2new extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MachineAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2new);
        mRecyclerView = findViewById(R.id.machine_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Machine> machineList = new ArrayList<>();
        // Populate machineList with data from Firebase
        // Create a reference to the "machines" node in the Firebase database
        DatabaseReference machinesRef = FirebaseDatabase.getInstance().getReference("machines");

        // Add a ValueEventListener to the machinesRef reference
        machinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the existing list
                machineList.clear();

                // Loop through the machines in the snapshot
                for (DataSnapshot machineSnapshot : snapshot.getChildren()) {
                    // Get the name, threshold, and condition values from the machine snapshot
                    String name = machineSnapshot.child("name").getValue(String.class);
                    float threshold = machineSnapshot.child("threshold").getValue(Float.class);
                    String condition = machineSnapshot.child("condition").getValue(String.class);

                    // Create a new Machine object and add it to the machineList
                    Machine machine = new Machine(name, threshold, condition);
                    machineList.add(machine);
                }

                // Notify the adapter that the data set has changed
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error case
                Log.e("Firebase", "Error retrieving machine data", error.toException());
            }
        });

        mAdapter = new MachineAdapter(machineList);
        mRecyclerView.setAdapter(mAdapter);




    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                moveAct9();
                return true;
            case R.id.menu_item2:
                moveAct8();
                return true;
            case R.id.menu_item3:
                moveAct7();
                return true;
            case R.id.menu_item4:
                moveAbtUs();
                return true;
            case R.id.menu_item5:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void moveAct7(){
        Intent intent = new Intent(this,MainActivity7.class);
        startActivity(intent);
    }
    private void moveAct8(){
        Intent intent = new Intent(this,MainActivity8.class);
        startActivity(intent);
    }
    private void moveAct9(){
        Intent intent = new Intent(this,MainActivity9.class);
        startActivity(intent);
    }
    private void moveAbtUs(){
        Intent intent = new Intent(this,MainActivity6.class);
        startActivity(intent);
    }
}