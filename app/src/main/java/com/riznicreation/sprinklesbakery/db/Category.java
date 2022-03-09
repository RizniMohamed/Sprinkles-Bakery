package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.riznicreation.sprinklesbakery.R;

import java.util.ArrayList;

public class Category extends DBHelper {
    public Category(@Nullable Context context) {
        super(context);
    }

    public int insertCategory(String name, int discount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name",name);
        cv.put("discount",discount);

        if(categoryAvailability(name)){
            if( db.insert("category",null,cv) != -1)
                return 1;
            else
                return 0;
        }else {
            return 2;
        }

    }

    public boolean categoryAvailability(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *  FROM category WHERE name=?" ,new String[]{name});
        if(c.getCount() == 0){
            c.close();
            return true;
        }
        return false;
    }

    public boolean deleteCategory(int categoryID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("category", "category_id=?", new String[]{String.valueOf(categoryID)}) == 1;
    }

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Category> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.riznicreation.sprinklesbakery.entity.Category> categories = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Category" ,null);
        while (c.moveToNext()){
            categories.add(new com.riznicreation.sprinklesbakery.entity.Category(
                    c.getInt(0),
                    c.getString(1),
                    R.drawable.ic_cake_,
                    c.getInt(2)
            ));
        }
        c.close();
        return categories;
    }


    public int UpdateCategory(int id, String name, int discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name",name);
        cv.put("discount",discount);

        if( db.update("category",cv,"category_id=?",new String[]{String.valueOf(id)}) != -1)
            return 1;
        else
            return 0;
    }
}
