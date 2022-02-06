package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.riznicreation.sprinklesbakery.entity.User;

public class Auth extends DBHelper{
    public Auth(@Nullable Context context) {
        super(context);
    }

    private final static User user = new User();

    public boolean checkLoginStatus(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT status  FROM Auth" ,null);
        if (c.moveToNext()){
            if(c.getString(0).equals("1")) {
                c.close();
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public User getUser(){
        return user;
    }


    public boolean login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *  FROM Auth WHERE email=? AND password=?" ,new String[]{email,password});
        if(c.moveToFirst()){
            ContentValues cv = new ContentValues();
            cv.put("status",1);
            db.update("Auth",cv,"email=?",new String[]{email});
            user.setAuthID(c.getInt(0));
            user.setPassword(c.getString(1));
            user.setEmail(c.getString(2));
            user.setStatus(c.getInt(3));
            c.close();
            return true;
        }
        return false;

    }

    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("password",password);
        cv.put("email",email);
        cv.put("status",1);

        return db.insert("Auth",null,cv) != -1;
    }

    public boolean accountAvailability(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *  FROM Auth WHERE email=?" ,new String[]{email});
        if(c.getCount() == 0){
            c.close();
            return true;
        }
        return false;
    }

    public boolean logout(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT *  FROM Auth WHERE status=? " ,new String[]{"1"}); c.moveToFirst();
        user.setAuthID(c.getInt(0));
        user.setPassword(c.getString(1));
        user.setEmail(c.getString(2));
        user.setStatus(c.getInt(3));
        c.close();

        ContentValues cv = new ContentValues();
        cv.put("status",0);
        return db.update("Auth",cv,"email=?",new String[]{user.getEmail()}) == 1;
    }

}
