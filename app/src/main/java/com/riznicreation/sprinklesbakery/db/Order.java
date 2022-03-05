package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.riznicreation.sprinklesbakery.entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order extends DBHelper{

    public Order(@Nullable Context context) { super(context); }

    public boolean setOrder(String totalPrice, String userId, ArrayList<Product> products){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("date", String.valueOf(LocalDate.now()));
        cv.put("status", 0);
        cv.put("total_price", totalPrice);
        cv.put("total_price", totalPrice);
        cv.put("user_id", userId);

        long orderID = db.insert("Orders",null,cv);
        long success = -1;
        for ( Product p : products) {
            cv.clear();
            cv.put("quantity",p.getQuantity());
            cv.put("product_id",p.getProduct_id());
            cv.put("orders_id",orderID);
            success = db.insert("OrderProduct",null,cv);
        }

        return success != -1;
    }

}
