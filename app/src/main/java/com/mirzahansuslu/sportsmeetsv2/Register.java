package com.mirzahansuslu.sportsmeetsv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mfullName, mEmail, mPassword, mPhone;
    Button mRegisterButton;
    TextView mLoginHere;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mfullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterButton = findViewById(R.id.registerButton);
        mLoginHere = findViewById(R.id.alreadyRegisteredText);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser()!= null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                String fullName = mfullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                if(TextUtils.isEmpty(fullName)) {
                    mfullName.setError("Full Name is required");
                    mfullName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number is required");
                    mPhone.requestFocus();
                    return;
                }
                /*if(!Patterns.PHONE.matcher(phone).matches()) {
                    mfullName.setError("Phone number is required");
                    mfullName.requestFocus();
                    return;
                }*/
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Please input validate email! ");
                    mEmail.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Password is required!");
                    return;
                }
                if(password.length()<6) {
                    mPassword.setError("Password shouldn't be less than 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(Register.this, "Error is occured",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);


                        }
                    }
                });

            }
        });

        mLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

}