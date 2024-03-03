package com.example.shoppingcart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingcart.Fragment.CartListPageFragment;
import com.example.shoppingcart.Fragment.MainPageFragment;
import com.example.shoppingcart.Fragment.OrderedPageFragment;
import com.example.shoppingcart.Fragment.ProfilePageFragment;
import com.example.shoppingcart.Fragment.SettingPageFragment;
import com.example.shoppingcart.Fragment.SupportPageFragment;
import com.example.shoppingcart.R;
import com.example.shoppingcart.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//private RecyclerView.Adapter recyclerViewCategoryAdapter,recyclerViewPopularAdapter;
//private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    private final static String TAG="MainActivity";

   private FragmentManager fragmentManager;
   private FragmentTransaction fragmentTransaction;
   private LinearLayout fragHomeBtn, fragProfileBtn, fragSupportBtn, fragSettingBtn;
   private ImageView ivHome,ivProfile,ivSupport,ivSetting;
   private TextView tvHome,tvProfile,tvSupport,tvSetting;
   private FloatingActionButton fragCartBtn;
   private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        setContentView(R.layout.activity_main_navigation_ver);
//        recyclerViewCategory();
//        recyclerViewPopular();
        initView();
        initEvent();
//        bottomNavigation();
    }
    private void initView(){
        fragHomeBtn =findViewById(R.id.fragHomeBtn);
        fragProfileBtn =findViewById(R.id.fragProfileBtn);
        fragSupportBtn =findViewById(R.id.fragSupportBtn);
        fragSettingBtn =findViewById(R.id.fragSettingBtn);
        fragCartBtn=findViewById(R.id.fragCartBtn);

        ivHome=findViewById(R.id.ivHome);
        ivProfile=findViewById(R.id.ivProfile);
        ivSupport=findViewById(R.id.ivSupport);
        ivSetting=findViewById(R.id.ivSetting);

        tvHome=findViewById(R.id.tvHome);
        tvProfile=findViewById(R.id.tvProfile);
        tvSupport=findViewById(R.id.tvOrdered);
        tvSetting=findViewById(R.id.tvMine);


    }
    private void initEvent() {
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            user = (User) receivedIntent.getSerializableExtra("user");
            if (user != null) {
                Log.d(TAG, "initEvent: 我获取到了User对象是："+user);
                // 在这里可以使用User对象
                // 添加Fragment
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //暂时不传东西，但是为什么要用到这个newInstance而不是直接new一个呢
                MainPageFragment mainPageFragment=MainPageFragment.newInstance();
                fragmentTransaction.replace(R.id.fcv_fragment,mainPageFragment).commit();
                //进行至此
            }
        }


        setBottomItemSelected(R.id.fragHomeBtn);

        fragHomeBtn.setOnClickListener(this);
        fragProfileBtn.setOnClickListener(this);
        fragSupportBtn.setOnClickListener(this);
        fragSettingBtn.setOnClickListener(this);
       fragCartBtn.setOnClickListener(this);

    }

    private void setBottomItemSelected(int id) {
        switch (id) {
            case R.id.fragHomeBtn:
                ivHome.setSelected(true);
                tvHome.setTextColor(getResources().getColor(R.color.bottomBtnSelected));
                break;
            case R.id.fragProfileBtn:
                ivProfile.setSelected(true);
                tvProfile.setTextColor(getResources().getColor(R.color.bottomBtnSelected));
                break;
            case R.id.fragSupportBtn:
                ivSupport.setSelected(true);
                tvSupport.setTextColor(getResources().getColor(R.color.bottomBtnSelected));
                break;
            case R.id.fragSettingBtn:
                ivSetting.setSelected(true);
                tvSetting.setTextColor(getResources().getColor(R.color.bottomBtnSelected));
            case R.id.fragCartBtn:
                break;

            default:
                break;
        }
    }
    private void resetBottomState() {
        ivHome.setSelected(false);
        tvHome.setTextColor(getResources().getColor(R.color.bottomBtnUnselected));
        ivProfile.setSelected(false);
        tvProfile.setTextColor(getResources().getColor(R.color.bottomBtnUnselected));
        ivSupport.setSelected(false);
        tvSupport.setTextColor(getResources().getColor(R.color.bottomBtnUnselected));
        ivSetting.setSelected(false);
        tvSetting.setTextColor(getResources().getColor(R.color.bottomBtnUnselected));
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        resetBottomState();
        setBottomItemSelected(v.getId());
        switch (id) {
            case R.id.fragHomeBtn:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //下一行New一个别的Frag添加进去，即可把别的加进去
                MainPageFragment mainPageFragment = MainPageFragment.newInstance();
                fragmentTransaction.replace(R.id.fcv_fragment,mainPageFragment).commit();
                break;
            case R.id.fragProfileBtn:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ProfilePageFragment profilePageFragment = ProfilePageFragment.newInstance("这是profile", "");
                fragmentTransaction.replace(R.id.fcv_fragment,profilePageFragment).commit();
                break;
            case R.id.fragSupportBtn:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
//                SupportPageFragment supportPageFragment =  SupportPageFragment.newInstance("这是支持页", "");
                OrderedPageFragment orderedPageFragment =OrderedPageFragment.newInstance("这是订单页","");
                fragmentTransaction.replace(R.id.fcv_fragment,orderedPageFragment).commit();
                break;
            case R.id.fragSettingBtn:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SettingPageFragment settingPageFragment = SettingPageFragment.newInstance("这是设置页", "");
                fragmentTransaction.replace(R.id.fcv_fragment,settingPageFragment).commit();
                break;
            case R.id.fragCartBtn:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CartListPageFragment cartListPageFragment = CartListPageFragment.newInstance("这是购物车页", "");
                fragmentTransaction.replace(R.id.fcv_fragment,cartListPageFragment).commit();
                break;

            default:
                break;
        }

    }
}
//    private void recyclerViewCategory() {
//        // 创建一个水平方向的LinearLayoutManager
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        // 获取RecyclerView的实例
//        recyclerViewCategoryList = findViewById(R.id.recyclerView);
//        // 设置RecyclerView的布局管理器为线性布局管理器
//        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
//        // 创建商品类别的数据源ArrayList<CategoryDomain>
//        ArrayList<CategoryDomain> category = new ArrayList<>();
//        category.add(new CategoryDomain("Pizza", "cat_1"));
//        category.add(new CategoryDomain("Burger", "cat_2"));
//        category.add(new CategoryDomain("HotDog", "cat_3"));
//        category.add(new CategoryDomain("Drink", "cat_4"));
//        category.add(new CategoryDomain("Donut", "cat_5"));
//        // 创建RecyclerView的适配器CategoryAdapter，将商品类别数据传递给适配器
//        recyclerViewCategoryAdapter = new CategoryAdapter(category);
//        // 设置RecyclerView的适配器
//        recyclerViewCategoryList.setAdapter(recyclerViewCategoryAdapter);
//
//    }
//    private void bottomNavigation(){//此为跳转页面
//        FloatingActionButton floatingActionButton=findViewById(R.id.cartBtn);
//        LinearLayout homeBtn=findViewById(R.id.homeBtn);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,CartListActivity.class));
//            }
//        });
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,MainActivity.class));
//            }
//        });
//
//    }
//    private void recyclerViewPopular()
//    {
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        recyclerViewPopularList=findViewById(R.id.recyclerViewPopular);
//        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
//
//        ArrayList<FoodDomain> foodList=new ArrayList<>();
//        foodList.add(new FoodDomain("Pepperoni pizza","pizza","slices pepperoni,mozzerella cheese,fresh oregano,yummy",9.76));
//        foodList.add(new FoodDomain("Cheese Burger","pizza","beef,Gouda Cheese,yummy",8.79));
//        foodList.add(new FoodDomain("Vegetable pizza","pizza","vegetable piiiiiiiiizzzzaaaa!yummy",5.76));
//
//        recyclerViewPopularAdapter=new PopularAdapter(foodList);
//        recyclerViewPopularList.setAdapter(recyclerViewPopularAdapter);
//
//    }