package com.example.shoppingcart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.shoppingcart.Activity.ShowDetailActivity;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.viewholder> {
    ArrayList<FoodDomain> items;
    Context context;

    public FoodListAdapter(ArrayList<FoodDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FoodListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(context).inflate(R.layout.viewholder_list_food,parent,false);
        return new viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.viewholder holder, int position) {
        if(items!=null){
            holder.titleTxt.setText(items.get(position).getTitle());
            holder.priceTxt.setText("$"+items.get(position).getFee());
//        Glide.with(context)
//                .load(items.get(position).get)
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(items.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .transform(new CenterCrop(),new RoundedCorners(30))
                    .into(holder.pic);
            holder.itemListFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                    intent.putExtra("object",items.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt,priceTxt;
        ImageView pic;
        CardView itemListFood;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleListFood);
            priceTxt=itemView.findViewById(R.id.priceTxtListFood);
            pic=itemView.findViewById(R.id.imgListFood);
            itemListFood=itemView.findViewById(R.id.itemListFood);
        }
    }
}
