package com.riznicreation.sprinklesbakery.entity;

public class User {

    private int authID;
    private String email;
    private String password;
    private int status;

    private int userID;
    private String name;
    private String picture;
    private int contact;
    private int orderID;

    public User() { }

    public User(int authID, String email, String password, int status, int userID, String name, String picture, int contact, int orderID) {
        this.authID = authID;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userID = userID;
        this.name = name;
        this.picture = picture;
        this.contact = contact;
        this.orderID = orderID;
    }

    public int getAuthID() {return authID;}
    public void setAuthID(int authID) {this.authID = authID;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

    public int getUserID() {return userID;}
    public void setUserID(int userID) {this.userID = userID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPicture() {return picture;}
    public void setPicture(String picture) {this.picture = picture;}

    public int getContact() {return contact;}
    public void setContact(int contact) {this.contact = contact;}

    public int getOrderID() {return orderID;}
    public void setOrderID(int orderID) {this.orderID = orderID;}

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
