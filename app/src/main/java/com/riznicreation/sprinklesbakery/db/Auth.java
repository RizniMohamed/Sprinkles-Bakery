package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.riznicreation.sprinklesbakery.entity.User;

public class Auth extends DBHelper{
    public Auth(@Nullable Context context) { super(context); /*this.context = context;*/ }

    private static User user = new User();
//    private final Context context;

    public boolean checkLoginStatus(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT status  FROM Auth WHERE status=1",null);
        while (c.moveToNext()){
            if(c.getString(0).equals("1")) {
                c.close();
                user = user().getUser();
                return true;
            }
        }
        return false;
    }


    public boolean login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *  FROM Auth WHERE email=? AND password=?" ,new String[]{email,password});
        if(c.moveToFirst()){
            ContentValues cv = new ContentValues();
            cv.put("status",1);
            db.update("Auth",cv,"email=?",new String[]{email});
            user = user().getUser();
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
        cv.put("admin",0);
        long authResult = db.insert("Auth",null,cv);

        cv.clear();
        cv.put("auth_id",authResult);
        long userResult = db.insert("User",null,cv);

        if ( (authResult != -1) && (userResult != -1)) {
            user = user().getUser();
            return true;
        }else
            return false;

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
        ContentValues cv = new ContentValues();
        cv.put("status",0);
        return db.update("Auth",cv,"auth_id=?",new String[]{String.valueOf(user.getAuthID())}) == 1;
    }

    public boolean setPassword(String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password",password);
        if(db.update("Auth",cv,"auth_id=?",new String[]{String.valueOf(user.getAuthID())}) == 1) {
            user = user().getUser();
            return true;
        }
        return false;
    }

}
