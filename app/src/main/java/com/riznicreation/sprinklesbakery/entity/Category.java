package com.riznicreation.sprinklesbakery.entity;

//Category entity class for hold all the information about each category
public class Category {
    private int id;
    private String name;
    private int iconID;

    public Category(int id, String name, int iconID) {
        this.id = id;
        this.name = name;
        this.iconID = iconID;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public int getIconID() { return iconID; }
    public void setIconID(int iconID) { this.iconID = iconID; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconID=" + iconID +
                '}';
    }
}
