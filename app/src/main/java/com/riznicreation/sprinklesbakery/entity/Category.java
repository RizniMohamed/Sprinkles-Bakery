package com.riznicreation.sprinklesbakery.entity;

//Category entity class for hold all the information about each category
public class Category {
    private int id;
    private String name;
    private int iconID;
    private int discount;

    public Category(int id, String name, int iconID, int discount) {
        this.id = id;
        this.name = name;
        this.iconID = iconID;
        this.discount = discount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public int getIconID() { return iconID; }
    public void setIconID(int iconID) { this.iconID = iconID; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconID=" + iconID +
                ", discount=" + discount +
                '}';
    }
}
