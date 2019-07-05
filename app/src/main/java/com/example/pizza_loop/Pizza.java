package com.example.pizza_loop;

public class Pizza {
    int pizzaId;
    String name;
    String details;
    float price;
    String imageUrl;
    int pizzaImage;

    public Pizza() {
    }

    public Pizza(String name, int pizzaImage) {
        this.name = name;
        this.pizzaImage = pizzaImage;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPizzaImage() {
        return pizzaImage;
    }
}
