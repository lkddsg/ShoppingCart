package com.example.shoppingcart.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.Activity.ShowDetailActivity;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    // 数据源，即商品类别的集合
    private ArrayList<FoodDomain> popularFood;

    // 构造方法，接收商品类别的数据源
    public PopularAdapter(ArrayList<FoodDomain> popularFood) {
        this.popularFood = popularFood;
    }

    // 创建ViewHolder
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 使用LayoutInflater加载viewholder_category布局文件，并创建ViewHolder

        //        LayoutInflater.from(parent.getContext()): LayoutInflater 是用来实例化 XML 文件到相应的 View 对象的类。
//        from(parent.getContext()) 是通过 parent 参数获取其上下文（Context），
//        然后通过 LayoutInflater.from(...) 创建一个 LayoutInflater 实例。
//        inflate(R.layout.viewholder_category, parent, false):
//        inflate 方法用于将 XML 布局文件转换成相应的 View 对象。
//        R.layout.viewholder_category 是指定要加载的 XML 布局文件的资源 ID，parent 是RecyclerView的父布局，
//        false 表示在加载布局时不将其附加到 parent 上，因为 RecyclerView 会在适当的时候将这个 ViewHolder 添加到布局中。
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);

    }

    // 绑定ViewHolder的数据
    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
//        onBindViewHolder 方法对每个 ViewHolder 都会执行一次。
//        当 RecyclerView 需要显示一个新的 item 时，它会调用 onBindViewHolder 方法，
//        该方法会将数据绑定到特定位置的 ViewHolder 上。在 RecyclerView 滚动时，
//        会动态地调用 onBindViewHolder 来更新屏幕上的可见项。

        // 获取当前位置的商品类别数据
        FoodDomain currentFood = popularFood.get(position);

        // 设置商品类别名称
        holder.title.setText(currentFood.getTitle());
        holder.fee.setText(String.valueOf(currentFood.getFee()));

        // 通过Glide加载商品类别图片

//
//        holder.itemView.getContext(): 获取 ViewHolder 所在的上下文，即 itemView 所在的界面上下文。
//        .getResources(): 获取资源管理器，用于获取应用的资源。
//        .getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName()): 通过资源名字字符串（picUrl）获取对应的资源ID。
//        picUrl: 这是一个字符串，用于构造资源名字，例如 "cat_1"。
//        "drawable": 表示要获取的资源类型是 drawable。
//        holder.itemView.getContext().getPackageName(): 获取应用的包名，确保在多个应用之间没有冲突。
//        这一系列的调用最终得到了 picUrl 对应的 drawable 资源的资源ID，将其保存在 drawableResourceId 变量中。
//
//        Glide.with(holder.itemView.getContext()): 创建一个 Glide 对象，传入 ViewHolder 所在的上下文。
//        .load(drawableResourceId): 指定要加载的图片资源的路径，这里是通过资源ID指定的。
//        .into(holder.categoryPic): 将加载的图片设置到 categoryPic，即 ImageView 中。
//        这样，整个过程就是使用 Glide 库加载通过资源名字字符串构造的 drawable 资源，
//        并将加载的图片显示在 ViewHolder 中的 categoryPic（ImageView）上。这样，每个 item 中的商品类别图片都会被正确加载和显示。
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularFood.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object",popularFood.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    // 获取RecyclerView中的item数量
    @Override
    public int getItemCount() {
        return popularFood.size();
    }

    // 内部类ViewHolder，用于保存item中的视图组件的引用。这是每一个列表项的视图
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,fee,addBtn;
        ImageView pic;


        // 构造方法，初始化视图组件
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            fee=itemView.findViewById(R.id.fee);
            pic=itemView.findViewById(R.id.pic);
            addBtn=itemView.findViewById(R.id.addBtn);

        }
    }
}


