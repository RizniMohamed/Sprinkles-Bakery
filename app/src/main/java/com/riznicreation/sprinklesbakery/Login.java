package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.*;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextInputEditText txtEmail,txtPassword;
    private TextView textForgetPassword,textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnLogin.setOnClickListener(v -> {
            if(validate()) {
                startActivity(new Intent(this, Home.class));
                finish();
            }
        });
        textRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
            finish();
        });
    }

    private boolean validate() {
        //Check empty fields
        String email = Objects.requireNonNull(txtEmail.getText()).toString();
        String password = Objects.requireNonNull(txtPassword.getText()).toString();

        if(email.isEmpty() || password.isEmpty()){
            if (email.isEmpty())
                Message.error(this,"Email cannot be empty");
            if (password.isEmpty())
                Message.error(this,"Password cannot be empty");
        }else if (!(email.contains("@") && email.contains("."))){
            Message.error(this,"Invalid email");
        }else{
            return validateInDB(email,password);
        }


        return false;
    }

    private boolean validateInDB(String email, String password) {
        DBHelper db = new DBHelper(this);
        if( !db.auth().login(email,password))
            Message.error(this,"Login failed");
        else
            return true;
        return false;
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        textForgetPassword = findViewById(R.id.textForgetPassword);
        textRegister = findViewById(R.id.textRegister);
    }

    @Override
    public void onBackPressed() {}

}