package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private TextView textView;
    private Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(fAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
        FirebaseUser user = fAuth.getCurrentUser();

        //initializing views
        textView = findViewById(R.id.textView_home);
        buttonLogout = findViewById(R.id.button_logout);
        textView.setText("Welcome "+user.getEmail());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(Home.this,MainActivity.class);
                startActivity(j);
            }
        });

    }
}
