package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText myemail;
    EditText mypassword;
    TextView myusername;
    Button btnregister;

    FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myemail = findViewById(R.id.emailhere);
        btnregister = findViewById(R.id.signup);
        mypassword = findViewById(R.id.passwordhere);
        myusername= findViewById(R.id.usernamehere);

        auth = FirebaseAuth.getInstance();
        btnregister.setOnClickListener(view ->{
            createUser();
        });

    myusername.setOnClickListener(view->{
        startActivity(new Intent(SignUp.this, Login.class));
    });
    }
    private void createUser(){
        String email = myemail.getText().toString();
        String password = mypassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            myemail.setError("email cannot be empty");
            myemail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            mypassword.setError("Password cannot be empty");
            mypassword.requestFocus();
        }
        else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUp.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class));
                    }
                    else{
                        Toast.makeText(SignUp.this,"Error in registeration" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}