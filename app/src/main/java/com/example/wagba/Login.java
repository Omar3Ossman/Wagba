package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText myEmail;
    EditText mypassword;
    Button btnLogin;
    ImageView signUpBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myEmail=findViewById(R.id.myemailhere);
        mypassword=findViewById(R.id.passwordhere);
        btnLogin=findViewById(R.id.login);

         auth = FirebaseAuth.getInstance();
         btnLogin.setOnClickListener(view ->{
             loginUser();
         });
         signUpBtn=findViewById(R.id.imageView4);
         signUpBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Login.this,SignUp.class);
                 startActivity(intent);
             }
         });
    }
    private void loginUser(){
        String email = myEmail.getText().toString();
        String password = mypassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            myEmail.setError("email cannot be empty");
            myEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            mypassword.setError("Password cannot be empty");
            mypassword.requestFocus();
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(Login.this,"User Logged in successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(Login.this, HomePage.class));
                   }
                   else{
                       Toast.makeText(Login.this,"Log in registeration" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }
    }
}