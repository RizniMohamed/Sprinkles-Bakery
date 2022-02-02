package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {
    private Button btnRegister;
    private TextInputEditText txtEmail,txtPassword,txtConfirmPassword;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnRegister.setOnClickListener(v -> startActivity(new Intent(this,Home.class)));
        textLogin.setOnClickListener(v -> startActivity(new Intent(this,Login.class)));
    }

    private void initViews() {
        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        textLogin = findViewById(R.id.textLogin);
    }

    @Override
    public void onBackPressed() {}
}