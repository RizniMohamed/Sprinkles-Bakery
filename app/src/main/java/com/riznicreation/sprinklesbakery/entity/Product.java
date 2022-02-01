package com.riznicreation.sprinklesbakery.entity;

public class Product {
    private int id;
    private String name;
    private float price;
    private String image;
    private int discount;
    private int weight;

    public Product(int id, String name, float price, String image, int discount, int weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.discount = discount;
        this.weight = weight;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", discount=" + discount +
                ", weight=" + weight +
                '}';
    }
}
