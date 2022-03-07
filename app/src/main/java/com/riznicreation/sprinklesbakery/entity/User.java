package com.riznicreation.sprinklesbakery.entity;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class User {

    private int authID;
    private String email;
    private String password;
    private int status;
    private int isAdmin;

    private int userID;
    private String name;
    private Bitmap picture;
    private int contact;
    private int orderID;
    private String address;

    public User() { }

    public User(int authID, String email, String password, int status, int isAdmin, int userID, String name, Bitmap picture, int contact, int orderID, String address) {
        this.authID = authID;
        this.email = email;
        this.password = password;
        this.status = status;
        this.isAdmin = isAdmin;
        this.userID = userID;
        this.name = name;
        this.picture = picture;
        this.contact = contact;
        this.orderID = orderID;
        this.address = address;
    }

    public int getAuthID() {return authID;}
    public void setAuthID(int authID) {this.authID = authID;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

    public int getIsAdmin() {return isAdmin;}
    public void setIsAdmin(int isAdmin) {this.isAdmin = isAdmin;}

    public int getUserID() {return userID;}
    public void setUserID(int userID) {this.userID = userID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Bitmap getPicture() {return picture;}
    public void setPicture(Bitmap picture) {this.picture = picture;}

    public int getContact() {return contact;}
    public void setContact(int contact) {this.contact = contact;}

    public int getOrderID() {return orderID;}
    public void setOrderID(int orderID) {this.orderID = orderID;}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "authID=" + authID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", contact=" + contact +
                ", orderID=" + orderID +
                '}';
    }


}
