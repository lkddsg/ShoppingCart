package com.example.shoppingcart.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.Activity.LoginActivity;
import com.example.shoppingcart.Activity.MainActivity;
import com.example.shoppingcart.Adapter.CartListAdapter;
import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.Helper.ManagementCart;
import com.example.shoppingcart.Interface.ChangeNumberItemsListener;
import com.example.shoppingcart.R;
import com.example.shoppingcart.dao.OrderedListDao;
import com.example.shoppingcart.dao.UserDao;
import com.example.shoppingcart.entity.User;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartListPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartListPageFragment extends Fragment {
    private static final String TAG="CartListPage";
    private  TextView checkOutBtn;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt,taxTxt,deliveryTxt,totalTxt,emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private void initView(){
        recyclerViewList=getView().findViewById(R.id.fragCartRecyclerView);
        totalFeeTxt=getView().findViewById(R.id.fragTotalFeeTxt);
//        taxTxt=getView().findViewById(R.id.fragTaxTxt);
        deliveryTxt=getView().findViewById(R.id.fragDeliveryTxt);
        totalTxt=getView().findViewById(R.id.fragTotalTxt);
        emptyTxt=getView().findViewById(R.id.fragEmptyTxt);
        scrollView=getView().findViewById(R.id.fragCartScrollView);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter=new CartListAdapter(managementCart.getListCart(),requireContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
//        if(managementCart.getListCart().isEmpty()){
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        }
//        else {
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }
    }
    private void calculateCart(){
        double percentTax=0.02;
        double delivery=10.0;
//        tax=Math.round((managementCart.getTotalFee()*percentTax)*100)/100;
        double total=Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
        if(total==10.0)
        {
            total=0.0;
        }
        double itemTotal=Math.round(managementCart.getTotalFee()*100)/100;
        totalFeeTxt.setText("$"+itemTotal);
//        taxTxt.setText("$"+tax);
        Log.d(TAG, "Delivery value: " + delivery);
        deliveryTxt.setText("$" + delivery);

        totalTxt.setText("$"+total);

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartListPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartListPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartListPageFragment newInstance(String param1, String param2) {
        CartListPageFragment fragment = new CartListPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_cart_list_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        managementCart=new ManagementCart(requireContext());
        initView();
        initList();
        calculateCart();
        super.onViewCreated(view, savedInstanceState);
         checkOutBtn=view.findViewById(R.id.checkOutBtn);
         checkOutBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                 OrderedListDao orderedListDao=new OrderedListDao();
                 Log.d(TAG, "onClick:点击了下单按钮。 ");
                 //接下来要传入各种信息给这个，不然无法加入
                 //这里还没有食物清单使用单例手法，因为可能不止一个？
                 ArrayList<FoodDomain> listFood=ManagementCart.getListCart();
                 addTo(User.getUserInstance(),listFood);
//                     orderedListDao.addToOrderedListDB(User.getUserInstance(),listFood);
                 Log.d(TAG, "onClick: 应该已经放到了数据库里才对。");
             }
         });

    }
    private boolean addTo(User user, ArrayList<FoodDomain> listFood){
        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "run: 创建了放订单线程！");
                OrderedListDao orderedListDao=new OrderedListDao();
                try {
                    orderedListDao.addToOrderedListDB(User.getUserInstance(),listFood);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        return false;
    };
//    @SuppressLint("HandlerLeak")
//    private static class CartListHandler extends Handler {
//        private UserDao userDao;
//        private final WeakReference<CartListPageFragment> fragmentReference;
//
//        private CartListHandler(UserDao userDao, WeakReference<CartListPageFragment> fragmentReference,Looper looper) {
//            super(looper);
//            this.userDao = userDao;
//            this.fragmentReference = fragmentReference;
//        }

//        CartListHandler(LoginActivity activity, Looper looper) {
//            super(looper);
//            this.activityReference = new WeakReference<>(activity);
//        }

//        @Override
//        public void handleMessage(Message msg) {
//            Log.d(TAG, "run: 现在进入了登录判断");
//            CartListPageFragment fragment = fragmentReference.get();
//            if (fragmentReference == null) {
//                // Activity 已经被回收，处理相应逻辑
//                return;
//            }
//
//            // 处理消息
//            switch (msg.what) {
//                case 0:
//                    Toast.makeText(activity.getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
//                    break;
//                case 1:
//                    Toast.makeText(activity.getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
////                    userDao=new UserDao();
////                    User user=userDao.getUserByCredentials(EditTextAccount.getText().toString(),EditTextPassword.getText().toString());
//                    Log.d(TAG, "当前登录成功后，获取到的用户信息如下："+User.getUserInstance());
//                    Intent intent = new Intent(activity, MainActivity.class);
//                    //将用户传过去
//                    intent.putExtra("user",User.getUserInstance());
//                    activity.startActivity(intent);
//                    activity.finish();//销毁本页
////                        activity.findViewById(R.id.fcv_fragment).requestLayout();
//                    break;
//                case 2:
//                    Toast.makeText(activity.getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
//                    break;
//                case 3:
//                    Toast.makeText(activity.getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
//                    break;
//            }
//
//
//        }
//    }
//
//    private final Handler handler = new CartListHandler(this, Looper.getMainLooper());
}