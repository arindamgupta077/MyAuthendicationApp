package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView createAC;
    private TextView forgotPassword;
    private TextView login;
    private EditText email;
    private EditText password;
    private FirebaseAuth fAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAC=findViewById(R.id.textView_createAC);
        email =  findViewById(R.id.editText_log_email);
        password =  findViewById(R.id.editText_log_pass);
        forgotPassword=findViewById(R.id.textView_forgetPassword);
        login=findViewById(R.id.button_login);


        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_S = email.getText().toString().trim();
                String password_S  = password.getText().toString().trim();
                if (TextUtils.isEmpty(email_S) && TextUtils.isEmpty(password_S))
                    Toast.makeText(MainActivity.this, "Please enter the details", Toast.LENGTH_LONG).show();

                if (TextUtils.isEmpty(password_S))
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();

                if (TextUtils.isEmpty(email_S))
                    Toast.makeText(MainActivity.this, "Please enter emailID", Toast.LENGTH_LONG).show();
                if(!(TextUtils.isEmpty(password_S)&&TextUtils.isEmpty(email_S))) {
                    fAuth.signInWithEmailAndPassword(email_S, password_S).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "wait", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MainActivity.this, Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                }
            }
        });

        createAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });

    }
}
