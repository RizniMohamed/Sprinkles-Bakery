package com.riznicreation.sprinklesbakery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextInputEditText txtEmail,txtPassword;
    private TextView textForgetPassword,textRegister;
    int otp = new Random().nextInt(999999);
    private String number;
    private DBHelper DB;

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

        textForgetPassword.setOnClickListener( v -> {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                textDialog();
            else
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},200);
        });

    }


    private void textDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        LayoutInflater factory = LayoutInflater.from(this);
        final View model = factory.inflate(R.layout.text_model, null);

        EditText txt = model.findViewById(R.id.txt);
        EditText txtNum = model.findViewById(R.id.txtNum);

        txt.setHint("Email");
        txtNum.setHint("OTP");


        Button btnApply = model.findViewById(R.id.btnApply);
        Button btnCancel = model.findViewById(R.id.btnCancel);

        AtomicBoolean firstLoad = new AtomicBoolean(true);
        btnApply.setOnClickListener(v1 -> {
            String txtUsername = txt.getText().toString();
            String txtOTP = txtNum.getText().toString();

            if(txt.getVisibility() == View.VISIBLE){
                if(!txtUsername.isEmpty()){
                    int status =  DB.auth().verify(txtUsername);
                    if(status == 0){
                        Message.error(this,"Account not found");
                    }else if(status == 1){
                        Message.error(this,"Phone number not found");
                    }else{
                        number = String.valueOf(status);
                        txt.setVisibility(View.GONE);
                        txtNum.setVisibility(View.VISIBLE);
                        sendMessage(number,"Your reset password OTP is " + otp);
                    }
                }else
                    Message.error(this,"Field cannot be empty");
            }

            if(txtNum.getVisibility() == View.VISIBLE){
                if(!txtNum.getText().toString().isEmpty()){
                    if( txtOTP.equals(String.valueOf(otp))){
                        DB.auth().setPassword("0000");
                        Message.success(this,"Password reset success");
                        Message.success(this,"Use 0000 to login");
                        Message.success(this,"Change your password on profile section");
                        String message = "Your password reset successfully.\n" +
                                "Use 0000 as your new password to login.\n" +
                                "Change your password in profile section";
                        sendMessage(number,message);
                        dialog.dismiss();
                    }else{
                        Message.error(this,"Invalid OTP");
                    }
                }else{
                    if(!firstLoad.get())
                        Message.error(this,"Field cannot be empty");
                    firstLoad.set(false);
                }
            }



        });
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setView(model);
        dialog.show();

    }

    private void sendMessage(String number, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number,null,message,null,null);
        Message.success(this,"Message send successfully");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            textDialog();
        }else{
            Message.error(this,"Permission denied");
        }
    }

    private boolean validate() {
        //Check empty fields
        String email = Objects.requireNonNull(txtEmail.getText()).toString();
        String password = Objects.requireNonNull(txtPassword.getText()).toString();

        if(email.isEmpty() || password.isEmpty()){
            if (email.isEmpty())
                Message.error(getBaseContext(),"Email cannot be empty");
            if (password.isEmpty())
                Message.error(getApplicationContext(),"Password cannot be empty");
        }else if (!(email.contains("@") && email.contains("."))){
            Message.error(getApplicationContext(),"Invalid email");
        }else{
            return validateInDB(email,password);
        }


        return false;
    }

    private boolean validateInDB(String email, String password) {
        if( !DB.auth().login(email,password))
            Message.error(getApplicationContext(),"Login failed");
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
        DB = new DBHelper(this);

    }

    @Override
    public void onBackPressed() {}

}