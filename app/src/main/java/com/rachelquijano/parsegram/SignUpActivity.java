package com.rachelquijano.parsegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends LoginActivity {

    EditText etCreateName;
    EditText etCreateEmail;
    EditText etCreateUsername;
    EditText etCreatePassword;
    Button btnSignUpFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etCreateName = findViewById(R.id.etCreateName);
        etCreateEmail = findViewById(R.id.etCreateEmail);
        etCreateUsername = findViewById(R.id.etCreateUsername);
        etCreatePassword = findViewById(R.id.etCreatePassword);
        btnSignUpFinish = findViewById(R.id.btnSignUpComplete);


        btnSignUpFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etCreateUsername.getText().toString();
                String password = etCreatePassword.getText().toString();
                String name = etCreateName.getText().toString();
                String email = etCreateEmail.getText().toString();
                signUpUser(username, password, email, name);
            }
        });
    }

    protected void signUpUser(String username, String password, String email, String name) {
        Log.i(TAG, "Attempting to create user " + username);
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("name", name);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(SignUpActivity.this, "Account creation successful!", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Issue with sign up!", e);
                    Toast.makeText(SignUpActivity.this, "Issue with sign up!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


}