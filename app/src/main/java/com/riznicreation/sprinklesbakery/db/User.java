package com.riznicreation.sprinklesbakery.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class User extends DBHelper{

    public User(@Nullable Context context) { super(context); }

    private final static com.riznicreation.sprinklesbakery.entity.User user = new com.riznicreation.sprinklesbakery.entity.User();

    public com.riznicreation.sprinklesbakery.entity.User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User LEFT JOIN Auth ON Auth.auth_id=User.auth_id WHERE Auth.status=1";
        Cursor c = db.rawQuery(query,null);

        //Change the cursor max reading length 2mb to 100mb for read blob data from sqlite
        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (c.moveToFirst()){
            user.setUserID(c.getInt(0));
            user.setName(c.getString(1));
            user.setContact(c.getInt(2));

            byte[] imgByte = c.getBlob(3);
            if(imgByte != null)
                user.setPicture(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));

            user.setAddress(c.getString(4));
            user.setOrderID(c.getInt(5));
            user.setAuthID(c.getInt(6));
            user.setPassword(c.getString(7));
            user.setEmail(c.getString(8));
            user.setStatus(c.getInt(9));
            user.setIsAdmin(c.getInt(10));
        }
        c.close();
        return user;
    }

    public boolean setName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        if(db.update("User",cv,"user_id=?",new String[]{String.valueOf(user.getUserID())}) == 1) {
            getUser();
            return true;
        }
        return false;
    }

    public boolean setAddress(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("address",name);
        if(db.update("User",cv,"user_id=?",new String[]{String.valueOf(user.getUserID())}) == 1) {
            getUser();
            return true;
        }
        return false;
    }


    public boolean setContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("contact",contact);

        if(db.update("User",cv,"user_id=?",new String[]{String.valueOf(user.getUserID())}) == 1) {
            getUser();
            return true;
        }
        return false;
    }

    public boolean storeImage(byte[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("image",data);
        if( db.update("User",cv,"user_id=?",new String[]{String.valueOf(user.getUserID())}) == 1) {
            getUser();
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     * 0 -> active counts <br>
     * 1 -> inactive counts <br>
     * 2 -> total counts <br>
     */
    public int[] getUserCounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        int active = 0, inactive, total = 0;
        Cursor c;

        c = db.rawQuery("SELECT COUNT(DISTINCT user_id)  FROM Orders",null);
        if(c.moveToNext())
            active = c.getInt(0);

        c = db.rawQuery("SELECT COUNT(DISTINCT user_id)  FROM User",null);
        if(c.moveToNext())
            total = c.getInt(0);

        inactive = total - active;
        c.close();
        return new int[]{active,inactive,total};
    }
}
