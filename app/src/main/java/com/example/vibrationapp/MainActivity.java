package com.example.vibrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button Register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Vibration Monitor");
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.login_button);
        Register = (Button) findViewById(R.id.register_button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(Email.getText()) ){
                    Toast.makeText(MainActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    Email.setError( "Email is required!" );
                } else if(TextUtils.isEmpty(Password.getText())){
                    Toast.makeText(MainActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    Password.setError( "Password is required!" );
                }
                else{
                    login(Email.getText().toString(),Password.getText().toString());
                }

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(Email.getText()) ){
                    Toast.makeText(MainActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    Email.setError( "Email is required!" );
                } else if(TextUtils.isEmpty(Password.getText())){
                    Toast.makeText(MainActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    Password.setError( "Password is required!" );
                }
                else{
                    register(Email.getText().toString(),Password.getText().toString());
                }
            }
        });
    }

    private void register(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, redirect to MainActivity2
//                            Intent intent = new Intent(MainActivity.this, MainActivity2new.class);
//                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        } else {
                            // Registration failed
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, redirect to MainActivity2
                            Intent intent = new Intent(MainActivity.this, MainActivity2new.class);
                            startActivity(intent);
                        } else {
                            // Login failed
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}