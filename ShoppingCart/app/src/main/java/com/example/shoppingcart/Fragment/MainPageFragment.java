package com.example.shoppingcart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoppingcart.Adapter.CategoryAdapter;
import com.example.shoppingcart.Adapter.PopularAdapter;
import com.example.shoppingcart.Domain.CategoryDomain;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.R;
import com.example.shoppingcart.entity.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {
    private RecyclerView.Adapter recyclerViewCategoryAdapter,recyclerViewPopularAdapter;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    private TextView titleHi;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void initView(View view) {
        titleHi=view.findViewById(R.id.titleHi);
        titleHi.setText(User.getUserInstance().getUserName());
    }

    private void recyclerViewPopular()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=getView().findViewById(R.id.recyclerViewPopular);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList=new ArrayList<>();
        foodList.add(new FoodDomain("帕帕罗尼披萨","pizza","slices pepperoni,mozzerella cheese,fresh oregano,yummy",9.76));
        foodList.add(new FoodDomain("芝士汉堡","pizza","beef,Gouda Cheese,yummy",8.79));
        foodList.add(new FoodDomain("蔬菜披萨","pizza","vegetable piiiiiiiiizzzzaaaa!yummy",5.76));
        foodList.add(new FoodDomain("榴莲披萨","pizza","vegetable piiiiiiiiizzzzaaaa!yummy",9.99));

        recyclerViewPopularAdapter=new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(recyclerViewPopularAdapter);

    }
    private void recyclerViewCategory() {
        // 创建一个水平方向的LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        // 获取RecyclerView的实例
        recyclerViewCategoryList =getView(). findViewById(R.id.recyclerView);
        // 设置RecyclerView的布局管理器为线性布局管理器
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        // 创建商品类别的数据源ArrayList<CategoryDomain>
        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("披萨", "cat_1"));
        category.add(new CategoryDomain("汉堡包", "cat_2"));
        category.add(new CategoryDomain("热狗", "cat_3"));
        category.add(new CategoryDomain("饮料", "cat_4"));
        category.add(new CategoryDomain("甜甜圈", "cat_5"));
        // 创建RecyclerView的适配器CategoryAdapter，将商品类别数据传递给适配器
        recyclerViewCategoryAdapter = new CategoryAdapter(category);
        // 设置RecyclerView的适配器
        recyclerViewCategoryList.setAdapter(recyclerViewCategoryAdapter);

    }
}