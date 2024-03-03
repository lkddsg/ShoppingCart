package com.example.shoppingcart.entity;

import com.example.shoppingcart.Domain.FoodDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderedList implements Serializable {
    //单例模式
    //这个的初始化待定
    private static OrderedList instance;

    private User user;
    private List<FoodDomain> cartItems;
//    private static int orderedListId;

    // Private constructor to prevent instantiation from outside
    private OrderedList(User user,List<FoodDomain> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
//        this.orderedListId=orderedListId;
    }

    // Public method to get the singleton instance
    public static synchronized OrderedList getInstance(User user,List<FoodDomain> cartItems,int orderedListId) {
        if (instance == null) {
            instance = new OrderedList(user,cartItems);
        }
        return instance;
    }

    @Override
    public String toString() {
        return "OrderedList{" +
                "user=" + user +
                ", cartItems=" + cartItems +
                '}';
    }

    public User getUser() {
        return user;
    }

    public List<FoodDomain> getCartItems() {
        return cartItems;
    }

    public void addToCart(FoodDomain food) {
        cartItems.add(food);
    }

    public void removeFromCart(FoodDomain food) {
        cartItems.remove(food);
    }

    public void clearCart() {
        cartItems.clear();
    }

    // You can add more methods as needed
}
