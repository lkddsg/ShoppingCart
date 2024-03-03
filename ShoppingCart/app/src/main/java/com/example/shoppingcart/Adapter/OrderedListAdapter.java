package com.example.shoppingcart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.Helper.ManagementCart;
import com.example.shoppingcart.Interface.ChangeNumberItemsListener;
import com.example.shoppingcart.R;

import java.util.ArrayList;

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.ViewHolder>{
    //应该已经完成了适配器？
    private ArrayList<FoodDomain> foodDomains;
    private ManagementCart managementCart;
//    private ChangeNumberItemsListener changeNumberItemsListener;

    public OrderedListAdapter(ArrayList<FoodDomain> foodDomains, Context context) {
        this.foodDomains = foodDomains;
        this.managementCart =new ManagementCart(context);
//        this.changeNumberItemsListener = changeNumberItemsListener;
    }
    @Override
    public OrderedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderedlist,parent,false);
        return new OrderedListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedListAdapter.ViewHolder holder, int position) {
        holder.orderedListTitleTxt.setText(foodDomains.get(position).getTitle());
        holder.feeOrderedListEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalOrderedListEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberInCart()*foodDomains.get(position).getFee())*100)/100));
        holder.orderedListNumberItemTxt.setText(String.valueOf(foodDomains.get(position).getNumberInCart()));
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(),
                "drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.picOrderedList);
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderedListNumberItemTxt,feeOrderedListEachItem,totalOrderedListEachItem,orderedListTitleTxt;
        ImageView picOrderedList;
//        TextView totalEachItem,num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderedListNumberItemTxt=itemView.findViewById(R.id.orderedListNumberItemTxt);
            feeOrderedListEachItem=itemView.findViewById(R.id.feeOrderedListEachItem);
            totalOrderedListEachItem=itemView.findViewById(R.id.totalOrderedListEachItem);
            orderedListTitleTxt=itemView.findViewById(R.id.orderedListTitleTxt);
            picOrderedList=itemView.findViewById(R.id.picOrderedList);
        }
    }
}
