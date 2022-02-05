package com.riznicreation.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.*;

import java.util.Objects;

public class Register extends AppCompatActivity {
    private Button btnRegister;
    private TextInputEditText txtEmail,txtPassword,txtConfirmPassword;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnRegister.setOnClickListener(v -> {
            if(validate()) {
                startActivity(new Intent(this, Home.class));
                finish();
            }

        });
        textLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }

    private boolean validate() {
        //Check empty fields
        String email = Objects.requireNonNull(txtEmail.getText()).toString();
        String password = Objects.requireNonNull(txtPassword.getText()).toString();
        String confirmPassword = Objects.requireNonNull(txtConfirmPassword.getText()).toString();

        if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            if (email.isEmpty())
                Message.error(this,"Email cannot be empty");
            if (password.isEmpty())
                Message.error(this,"Password cannot be empty");
            if (confirmPassword.isEmpty())
                Message.error(this,"Confirm password cannot be empty");
        }else if (!(email.contains("@") && email.contains("."))) {
            Message.error(this, "Invalid email");
        }else if (!passwordCharValidation(password) ){
            Message.error(this,"Password must contain at least 1 number,lower case letter, upper case letter, special character and minimum 8 characters");
        }else if (!password.equals(confirmPassword)){
            Message.error(this,"Password mismatched");
        }else{
            return insertInDB(email,password);
        }


        return false;
    }

    public boolean passwordCharValidation(@NonNull String password) {
        /*
            ^                 # start-of-string
            (?=.*[0-9])       # a digit must occur at least once
            (?=.*[a-z])       # a lower case letter must occur at least once
            (?=.*[A-Z])       # an upper case letter must occur at least once
            (?=.*[@#$%^&+=.]) # a special character must occur at least once
            (?=\S+$)          # no whitespace allowed in the entire string
            .{8,}             # anything, at least eight places though
            $                 # end-of-string
         */
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";
        return (password.matches(pattern));
    }

    private boolean insertInDB(String email, String password) {
        DBHelper db = new DBHelper(this);
        if(db.auth().accountAvailability(email)){
            if(!db.auth().registerUser(email,password))
                Message.error(this,"Error on user registration " );
            else
                return true;
        }else{
            Message.error(this,"Already have an account in given email" );
        }
        return false;
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