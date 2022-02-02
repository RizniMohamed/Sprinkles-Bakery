package com.riznicreation.sprinklesbakery.entity;

import androidx.annotation.NonNull;

import java.util.Date;

public class Order {
    private int id;
    private int productID;
    private Date orderedDate;
    private int quantity;
    private int status;

    public Order(int id, int productID, Date orderedDate, int quantity, int status) {
        this.id = id;
        this.productID = productID;
        this.orderedDate = orderedDate;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getProductID() {return productID;}
    public void setProductID(int productID) {this.productID = productID;}

    public Date getOrderedDate() {return orderedDate;}
    public void setOrderedDate(Date orderedDate) {this.orderedDate = orderedDate;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productID=" + productID +
                ", orderedDate=" + orderedDate +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
