package com.riznicreation.sprinklesbakery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.riznicreation.sprinklesbakery.entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Order extends DBHelper{

    Context context;
    public Order(@Nullable Context context) { super(context); this.context = context; }

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

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Order> getAllOrders(){
        ArrayList<com.riznicreation.sprinklesbakery.entity.Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Orders.orders_id,Orders.date,status,total_price FROM Orders ";

        Cursor c = db.rawQuery(query,null);

        while (c.moveToNext()){
            com.riznicreation.sprinklesbakery.entity.Order order = new com.riznicreation.sprinklesbakery.entity.Order();
            order.setId(c.getInt(0));
            order.setOrderedDate(c.getString(1));
            order.setStatus(c.getInt(2));
            order.setTotPrice(c.getLong(3));
            orders.add(order);
        }
        c.close();
        return orders;
    }

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Order> getOrder(){
        ArrayList<com.riznicreation.sprinklesbakery.entity.Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Orders.orders_id,Orders.date,status,total_price FROM Orders " +
                "WHERE user_id =  " + this.user().getUser().getUserID();

        Cursor c = db.rawQuery(query,null);

        while (c.moveToNext()){
            com.riznicreation.sprinklesbakery.entity.Order order = new com.riznicreation.sprinklesbakery.entity.Order();
            order.setId(c.getInt(0));
            order.setOrderedDate(c.getString(1));
            order.setStatus(c.getInt(2));
            order.setTotPrice(c.getLong(3));
            orders.add(order);
        }
        c.close();
        return orders;
    }

    public ArrayList<Product> getOrderItems(int id) {
        ArrayList<com.riznicreation.sprinklesbakery.entity.Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p.name,p.image, p.unit_price, quantity FROM OrderProduct INNER JOIN Product AS p  ON p.product_id = OrderProduct.product_id " +
                "WHERE OrderProduct.orders_id =  " + id;

        Cursor c = db.rawQuery(query,null);

        while (c.moveToNext()){
            com.riznicreation.sprinklesbakery.entity.Product product = new com.riznicreation.sprinklesbakery.entity.Product();
            product.setProduct_name(c.getString(0));
            product.setUnit_price(c.getLong(2));
            product.setQuantity(c.getInt(3));

            //Get image from resource file
            if (c.getString(1).length() == 3) {
                int imageFromResource = context.getResources().getIdentifier(c.getString(1), "mipmap", context.getPackageName());
                BitmapDrawable drawable = (BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(context.getResources(), imageFromResource, null));
                product.setImage(drawable.getBitmap());
            } else {
                //get newly added images by user
                byte[] imgByte = c.getBlob(51);
                product.setImage(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
            }

            products.add(product);
        }
        c.close();
        return products;
    }

    public boolean setStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status",status);
        return db.update("orders",cv,"orders_id=?",new String[]{String.valueOf(id)}) == 1;
    }
}
