package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.riznicreation.sprinklesbakery.R;

import java.util.ArrayList;
import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sparklesBakery.db";
    private static final int  DATABASE_VERSION = 1;
    private final Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        System.out.print("");
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        //Create all tables
        initDatabase(db,R.string.create_tables);
        //Inset all data
        initDatabase(db,R.string.insert_data);

    }

    private void initDatabase(@NonNull SQLiteDatabase db, int text) {
        //Get the all table creation queries from the string store which will be a single string
        String rawText = Objects.requireNonNull(context).getString(text);
        //Split the strings by ';' to create multiple strings as string array
        String[] queries = rawText.split(";");
        //run each string by execSQL function with ';'
        for (String query : queries) db.execSQL(query + ";");
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( " DROP TABLE IF EXISTS 'Auth'");
        db.execSQL( " DROP TABLE IF EXISTS 'Category'");
        db.execSQL( " DROP TABLE IF EXISTS 'Order'");
        db.execSQL( " DROP TABLE IF EXISTS 'User'");
        db.execSQL( " DROP TABLE IF EXISTS 'Product'");
        onCreate(db);
    }

    public Auth auth(){
        return new Auth(context);
    }
    public User user(){
        return new User(context);
    }

    public Category category(){
        return new Category(context);
    }


}
