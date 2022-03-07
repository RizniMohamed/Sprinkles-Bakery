package com.riznicreation.sprinklesbakery.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int id;
    private int productID;
    private String orderedDate;
    private int status;
    private long totPrice;
    private ArrayList<Product> productsList = new ArrayList<>();

    public Order() {}

    public Order(int id, int productID, String orderedDate, int status, long totPrice, ArrayList<Product> productsList) {
        this.id = id;
        this.productID = productID;
        this.orderedDate = orderedDate;
        this.status = status;
        this.totPrice = totPrice;
        this.productsList = productsList;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }

    public String getOrderedDate() { return orderedDate; }
    public void setOrderedDate(String orderedDate) { this.orderedDate = orderedDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public long getTotPrice() { return totPrice; }
    public void setTotPrice(long totPrice) { this.totPrice = totPrice; }

    public ArrayList<Product> getProductsList() { return productsList; }
    public void setProductsList(ArrayList<Product> productsList) { this.productsList = productsList; }}
