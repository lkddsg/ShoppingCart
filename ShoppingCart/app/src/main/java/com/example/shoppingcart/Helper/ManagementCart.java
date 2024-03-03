package com.example.shoppingcart.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private static TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertFood(FoodDomain item){
        ArrayList<FoodDomain> listFood=getListCart();
                boolean existAlready=false;
                int n=0;
                for(int i=0;i<listFood.size();i++)
                {
                    if(listFood.get(i).getTitle().equals(item.getTitle()))
                    {
                        existAlready=true;
                        n=i;
                        break;
                    }
                }
                if(existAlready)
                {
                    Log.d("cart", "insertFood: 已经存在购物车了");
                    listFood.get(n).setNumberInCart(item.getNumberInCart()+listFood.get(n).getNumberInCart());
                }
                else {
                    Log.d("cart", "insertFood: 没存在购物车，现在加进去");
                    listFood.add(item);
                }
                tinyDB.putListObject("CartList",listFood);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
    }
    public  static ArrayList<FoodDomain> getListCart(){
        return tinyDB.getListObject("CartList");

    }
    public void plusNumberFood(ArrayList<FoodDomain>listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemsListener.changed();
    }
    public void minusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener)
    {
    if(listFood.get(position).getNumberInCart()==1){
        listFood.remove(position);
    }
    else {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()-1);

    }
    //这两行没有的话就会直接崩溃，为什么？？？？？
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemsListener.changed();
    }
    public Double getTotalFee(){
        ArrayList<FoodDomain> listFood=getListCart();
        double fee=0;
        for(int i=0;i<listFood.size();i++){
            fee=fee+(listFood.get(i).getFee()*listFood.get(i).getNumberInCart());
        }
        return fee;
    }
}
