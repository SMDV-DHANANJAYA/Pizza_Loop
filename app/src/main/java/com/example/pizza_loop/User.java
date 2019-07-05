package com.example.pizza_loop;

public class User {
    public static String userName;
    public static String email;
    public static Long phone;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static Long getPhone() {
        return phone;
    }

    public static void setPhone(Long phone) {
        User.phone = phone;
    }
}
