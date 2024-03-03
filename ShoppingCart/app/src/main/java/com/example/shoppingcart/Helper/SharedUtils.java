package com.example.shoppingcart.Helper;

public class SharedUtils {
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public static void incrementCounter() {
        counter++;
    }
//    public static int getAndAddCounterAtfer(){}
}
