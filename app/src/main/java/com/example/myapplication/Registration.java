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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText number;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth fAuth;
    private TextView loginpage;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.editText_Name);
        email =  findViewById(R.id.editText_reg_email);
        password =  findViewById(R.id.editText_reg_password);
        number=findViewById(R.id.editText_phonenumber);
        buttonSignup = findViewById(R.id.button_reg);
        loginpage=findViewById(R.id.textView_login);
        progressDialog = new ProgressDialog(this);
        database=FirebaseDatabase.getInstance();



        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name_S = name.getText().toString().trim();
                final String email_S = email.getText().toString().trim();
                final String number_S=number.getText().toString().trim();
                String password_S  = password.getText().toString().trim();

                ref=database.getReference("users");

                if(TextUtils.isEmpty(email_S)||TextUtils.isEmpty(password_S)||
                        TextUtils.isEmpty(name_S)||TextUtils.isEmpty(number_S)) {
                    if (TextUtils.isEmpty(email_S))
                        Toast.makeText(Registration.this, "Please enter email", Toast.LENGTH_LONG).show();

                   else if (TextUtils.isEmpty(password_S))
                         password.setError("Enter password");

                   else if (TextUtils.isEmpty(password_S) && TextUtils.isEmpty(email_S))
                         Toast.makeText(Registration.this, "Please email and password", Toast.LENGTH_LONG).show();

                   else
                         Toast.makeText(Registration.this, "Fill all details", Toast.LENGTH_LONG).show();
                }

                else {
                       if(password_S.length() < 6)
                           Toast.makeText(Registration.this, "Password is too small", Toast.LENGTH_LONG).show();
                       if(!email_S.contains("@"))
                           Toast.makeText(Registration.this, "Email is invalid", Toast.LENGTH_LONG).show();

                    final userclass user=new userclass(name_S,email_S,number_S);

                    fAuth.createUserWithEmailAndPassword(email_S, password_S).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.setMessage("Registering Please Wait...");
                                progressDialog.show();
                                ref.child(number_S).setValue(user);
                                Toast.makeText(Registration.this, "Success", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(Registration.this,Home.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(Registration.this,"error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(Registration.this,MainActivity.class);
                startActivity(j);
            }
        });
    }
}
