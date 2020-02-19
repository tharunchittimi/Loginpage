package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class SecondActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutUserName;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonRegister;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();


                    if (Email.isEmpty()) {
                        Snackbar.make(buttonRegister, "Please enter Email", Snackbar.LENGTH_LONG).show();
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                        Snackbar.make(buttonRegister, "Please enter valid Email", Snackbar.LENGTH_LONG).show();
                    } else if (!sqliteHelper.isEmailExists(Email)) {

                        if (MyApplication.getInstance().getNetworkConnected()) {
                            sqliteHelper.addUser(new User(null, UserName, Email, Password));
                            Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                            Handler handler = new Handler();
                            Runnable delayrunnable = new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            };
                            handler.postDelayed(delayrunnable, 3000);
                        } else {
                            Snackbar.make(buttonRegister, "Network not Connected", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initTextViewLogin() {
        TextView textViewLogin = findViewById(R.id.textView8);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        buttonRegister = (Button) findViewById(R.id.imageButton);

    }

    public boolean validate() {
        boolean valid = false;

        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setError(null);
// -----------------------------------------------------------------------

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    valid = false;
                    textInputLayoutEmail.setError("Please enter valid email!");
                } else {
                    valid = true;
                    textInputLayoutEmail.setError(null);
// -----------------------------------------------------------------------
                    if (Password.isEmpty()) {
                        valid = false;
                        textInputLayoutPassword.setError("Please enter valid password!");
                    } else if (!Password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")) {
                        textInputLayoutPassword.setError("password should contain Special Characters");
                    } else {
                        if (Password.length() > 7) {
                            valid = true;
                            textInputLayoutPassword.setError(null);
                        } else {
                            valid = false;
                            textInputLayoutPassword.setError("Password is to short!");
                        }
                    }
                }
            } else {
                valid = false;
                textInputLayoutUserName.setError("Username is to short!");
            }
        }
        return valid;
    }
}