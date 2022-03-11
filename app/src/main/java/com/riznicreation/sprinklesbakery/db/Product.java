package com.riznicreation.sprinklesbakery.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

public class Product extends DBHelper{

    private final Context context;

    public final static ArrayList<com.riznicreation.sprinklesbakery.entity.Product> products = new ArrayList<>();

    public Product(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Product> getAllProducts() {
        products.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from product inner join  category on product.category_id = category.category_id";
        Cursor c = db.rawQuery(query,null);

        //Change the cursor max reading length 2mb to 100mb for read blob data from sqlite
        try {
            @SuppressLint("DiscouragedPrivateApi") Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (c.moveToNext()){
            com.riznicreation.sprinklesbakery.entity.Product product = new com.riznicreation.sprinklesbakery.entity.Product();

            product.setProduct_id(c.getInt(0));

            product.setProduct_name(c.getString(1));
            product.setCream(c.getString(2));
            product.setFlavour(c.getString(3));
            product.setUnit_price((float) c.getDouble(4));

            //Get image from resource file
            if(c.getType(5) == Cursor.FIELD_TYPE_BLOB){
                //get newly added images by user
                byte[] imgByte = c.getBlob(5);
                product.setImage(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
            }else{
                int imageFromResource = context.getResources().getIdentifier(c.getString(5), "mipmap", context.getPackageName());
                BitmapDrawable drawable = (BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(context.getResources(), imageFromResource, null));
                product.setImage(drawable.getBitmap());
            }



            product.setCategory_id(c.getInt(6));
            product.setCategory_name(c.getString(8));
            product.setDiscount(c.getInt(9));

            products.add(product);
        }

        c.close();
        return products;
    }

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Product> getDiscountProducts(){
        ArrayList<com.riznicreation.sprinklesbakery.entity.Product> discountProducts = new ArrayList<>();

        for (com.riznicreation.sprinklesbakery.entity.Product p: products) {
            if(p.getDiscount() != 0) discountProducts.add(p);
        }

        return discountProducts;
    }

    public ArrayList<com.riznicreation.sprinklesbakery.entity.Product> getProducts(int id) {
        ArrayList<com.riznicreation.sprinklesbakery.entity.Product> categorized_products = new ArrayList<>();
        for (com.riznicreation.sprinklesbakery.entity.Product p: products) {
            if(p.getCategory_id() == id) categorized_products.add(p);
        }
        return categorized_products;
    }

    public boolean addProduct(String name,String cream ,String flavour,String unit_price,byte[] image, int category_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name",name);
        cv.put("cream",cream);
        cv.put("flavour",flavour);
        cv.put("unit_price",unit_price);
        cv.put("image",image);
        cv.put("category_id",category_id);

        return db.insert("product", null, cv) != -1;
    }

    public boolean updateProduct(String name,String cream ,String flavour,String unit_price,byte[] image, int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name",name);
        cv.put("cream",cream);
        cv.put("flavour",flavour);
        cv.put("unit_price",unit_price);
        cv.put("image",image);

        return db.update("product", cv, "product_id=?",new String[]{String.valueOf(productID)}) == 1;
    }

    public boolean deleteProduct(int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("product","product_id=?",new String[]{String.valueOf(productID)}) == 1;
    }
}
