package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText passWord;
    private EditText userName;

    private Button _login;

    private ImageButton _goBack;

    private FirebaseAuth loginAuth;

    private FirebaseAuth.AuthStateListener loginAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAuth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.user_name_input);
        if (userName.getText().toString().length() == 0) {
            userName.setError("User Name can't be empty!");
        }
        passWord = (EditText) findViewById(R.id.password_input);
        if (passWord.getText().toString().length() == 0)
            passWord.setError("Password can't be empty!");

        _login = (Button) findViewById(R.id.login_button);
        _goBack = (ImageButton) findViewById(R.id.go_back_button);

        loginAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (userName != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };
    }

    /**
     * Us to test the password and username
     */
    public void login(View view ) {

        String _username = userName.getText().toString();
        String _password = passWord.getText().toString();

        if (passWord == null || userName == null) {
            userName.setError("User Name can't be empty!");
            passWord.setError("Password can't be empty!");
        } else {
            loginAuth.signInWithEmailAndPassword(_username,_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intents = new Intent(LoginActivity.this, WelcomActivity.class);
                                startActivity(intents);
                            } else {
                                Toast.makeText(LoginActivity.this, "Email and Password are not match!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }


    }


    /**
     * when go back was click if sent it back to initial screen
     * @param view
     */
    public void goBack(View view) {
                Intent intents = new Intent(LoginActivity.this, InitialActivity.class);
                passWord = null;
                userName = null;
                startActivity(intents);

    }

    @Override
    public void onStart() {
        super.onStart();
        loginAuth.addAuthStateListener(loginAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginAuthListener != null) {
            loginAuth.removeAuthStateListener(loginAuthListener);
        }
    }

}
