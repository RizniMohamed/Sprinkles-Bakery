package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextInputEditText txtEmail,txtPassword;
    private TextView textForgetPassword,textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        startActivity(new Intent(this, Home.class));

    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        textForgetPassword = findViewById(R.id.textForgetPassword);
        textRegister = findViewById(R.id.textRegister);
    }


}