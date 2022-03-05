package com.riznicreation.sprinklesbakery.entity;

import android.graphics.Bitmap;

public class Product {
    private int product_id;
    private String product_name;
    private String cream;
    private String flavour;
    private float unit_price;
    private Bitmap image;

    private int category_id;
    private String category_name;
    private int discount;

    private int quantity;

    public Product(){}

    public Product(int product_id, String product_name, String cream, String flavour, float unit_price, Bitmap image, int category_id, String category_name, int discount, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.cream = cream;
        this.flavour = flavour;
        this.unit_price = unit_price;
        this.image = image;
        this.category_id = category_id;
        this.category_name = category_name;
        this.discount = discount;
        this.quantity = quantity;
    }

    public int getProduct_id() { return product_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public String getCream() { return cream; }
    public void setCream(String cream) { this.cream = cream; }

    public String getFlavour() { return flavour; }
    public void setFlavour(String flavour) { this.flavour = flavour; }

    public float getUnit_price() { return unit_price; }
    public void setUnit_price(float unit_price) { this.unit_price = unit_price; }

    public Bitmap getImage() { return image; }
    public void setImage(Bitmap image) { this.image = image; }

    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    public String getCategory_name() { return category_name; }
    public void setCategory_name(String category_name) { this.category_name = category_name; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
