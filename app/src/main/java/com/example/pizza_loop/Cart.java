package com.example.pizza_loop;

public class Cart {
    int cartId;
    String pizzaName;
    String pizzaImageurl;
    float pizzaPrice;
    int pizzaQuantity;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getPizzaImageurl() {
        return pizzaImageurl;
    }

    public void setPizzaImageurl(String pizzaImageurl) {
        this.pizzaImageurl = pizzaImageurl;
    }

    public float getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(float pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public int getPizzaQuantity() {
        return pizzaQuantity;
    }

    public void setPizzaQuantity(int pizzaQuantity) {
        this.pizzaQuantity = pizzaQuantity;
    }
}
