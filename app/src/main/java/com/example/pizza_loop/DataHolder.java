package com.example.pizza_loop;

public class DataHolder {
    private static String serverIpAddress;
    private static String name;
    private static int cartCount;

    public static String getServerIpAddress() {
        return serverIpAddress;
    }

    public static void setServerIpAddress(String serverIpAddress) {
        DataHolder.serverIpAddress = serverIpAddress;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        DataHolder.name = name;
    }

    public static int getCartCount() {
        return cartCount;
    }

    public static void setCartCount(int cartCount) {
        DataHolder.cartCount = cartCount;
    }
}
