package com.example.shoppingcart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.shoppingcart.Adapter.FoodListAdapter;
import com.example.shoppingcart.Domain.CategoryDomain;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.R;
import com.example.shoppingcart.databinding.ActivityListFoodBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {
    ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    private ArrayList<FoodDomain> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        initList();
    }

    private void initList() {
        binding.progressBar.setVisibility(View.GONE);
        list = new ArrayList<>();
        if (categoryName.equals("披萨")) {
            list.add(new FoodDomain("披萨1", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨2", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨3", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨4", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨5", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨1", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨2", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨3", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨4", "cat_1", "yummy", 1.11));
            list.add(new FoodDomain("披萨5", "cat_1", "yummy", 1.11));
        } else if (categoryName.equals("汉堡包")) {
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
            list.add(new FoodDomain("汉堡包", "cat_2", "yummy", 2.22));
        } else if (categoryName.equals("热狗")) {
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
            list.add(new FoodDomain("HotDog", "cat_3", "yummy", 3.33));
        } else if (categoryName.equals("饮料")) {
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
            list.add(new FoodDomain("Drink", "cat_4", "yummy", 4.44));
        } else if (categoryName.equals("甜甜圈")) {
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
            list.add(new FoodDomain("Donut", "cat_5", "yummy", 5.55));
        }
        if (list.size() > 0) {
            binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodActivity.this, 2));
            adapterListFood = new FoodListAdapter(list);
            binding.foodListView.setAdapter(adapterListFood);
        }
        binding.progressBar.setVisibility(View.GONE);
    }

    private void getIntentExtra() {
//        categoryId=getIntent().getIntExtra("CategoryId",0);
        categoryName = getIntent().getStringExtra("CategoryName");
//        searchText=getIntent().getStringExtra("text");
//        isSearch=getIntent().getBooleanExtra("isSearch",false);

        binding.titleCartList.setText(categoryName);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}