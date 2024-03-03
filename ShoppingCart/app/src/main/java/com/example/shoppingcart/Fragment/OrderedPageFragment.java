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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.Activity.LoginActivity;
import com.example.shoppingcart.Activity.MainActivity;
import com.example.shoppingcart.Adapter.CartListAdapter;
import com.example.shoppingcart.Adapter.OrderedListAdapter;
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
 * Use the {@link OrderedPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderedPageFragment extends Fragment {
    private static final String TAG="OrderedListPage";

    private static   RecyclerView.Adapter orderedListAdapter;
    private static RecyclerView orderedListRecyclerViewList;
    private ManagementCart managementCart;
    private OrderedListDao orderedListDao;
    private static ProgressBar orderedListProgressBar;
    private ArrayList<FoodDomain> listfood;
    TextView orderedListTotalFeeTxt,orderedListTaxTxt,orderedListDeliveryTxt,orderedListTotalTxt,orderedListEmptyTxt;
    private double orderedListTax;
    private ScrollView orderedListScrollView;
    private void initView(){
        orderedListProgressBar=getView().findViewById(R.id.orderedListProgressBar);
        orderedListRecyclerViewList=getView().findViewById(R.id.fragOrderedListRecyclerView);
        orderedListTotalFeeTxt=getView().findViewById(R.id.orderedListTotalFeeTxt);
//        orderedListTaxTxt=getView().findViewById(R.id.orderedListTaxTxt);
        orderedListDeliveryTxt=getView().findViewById(R.id.orderedListDeliveryTxt);
        orderedListTotalTxt=getView().findViewById(R.id.orderedListTotalTxt);
        orderedListEmptyTxt=getView().findViewById(R.id.fragEmptyTxt);
        orderedListScrollView=getView().findViewById(R.id.fragOrderedScrollView);



    }
    private void initList() throws SQLException {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        orderedListRecyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d(TAG, "initList: 下一步进行获取数据");
        // 新建线程获取数据
        new Thread() {
            @Override
            public void run() {
                int msg=0;
                try {
                    listfood = orderedListDao.getOrderedListFromDB(User.getUserInstance());
                    if (listfood.size() != 0) {
                        orderedListAdapter = new OrderedListAdapter(listfood, requireContext());
                        msg=1;

                        Log.d(TAG, "initList: 订单数据应该获取完了");
                    } else {
                        msg=0;
                        Log.d(TAG, "run: 获取订单数据失败");
                    }
                    if (handler != null) {
                        Log.d(TAG, "run: 向Handler发送了账号密码！当前msg值为" + msg);
                        handler.sendEmptyMessage(msg);


                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        orderedListAdapter=new OrderedListAdapter(orderedListDao.getOrderedListFromDB(User.getUserInstance()),requireContext());
//        Log.d(TAG, "initList: 应该获取完了");
//        orderedListRecyclerViewList.setAdapter(orderedListAdapter);
//        if(managementCart.getListCart().isEmpty()){
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        }
//        else {
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }
    }
//

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderedPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderedPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderedPageFragment newInstance(String param1, String param2) {
        OrderedPageFragment fragment = new OrderedPageFragment();
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
        return inflater.inflate(R.layout.fragment_ordered_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        orderedListDao=new OrderedListDao();
        initView();
        try {
            initList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        calculateCart();
        super.onViewCreated(view, savedInstanceState);
    }
    @SuppressLint("HandlerLeak")
    private static class OrderedListHandler extends Handler {
        private UserDao userDao;
        private final WeakReference<OrderedPageFragment> fragmentReference;

        OrderedListHandler(OrderedPageFragment fragment, Looper looper) {
            super(looper);
            this.fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "run: 现在进入了购物车获取信息");
            OrderedPageFragment fragment = fragmentReference.get();
            if (fragment == null) {
                // Activity 已经被回收，处理相应逻辑
                return;
            }

            // 处理消息
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    orderedListProgressBar.setVisibility(View.GONE);
                    orderedListRecyclerViewList.setAdapter(orderedListAdapter);
                    break;

            }


        }
    }

    private final Handler handler = new OrderedListHandler(this, Looper.getMainLooper());
}
//    private void calculateCart(){
//        double percentTax=0.02;
//        double delivery=10;
//        tax=Math.round((managementCart.getTotalFee()*percentTax)*100)/100;
//        double total=Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
//        if(total==10.0)
//        {
//            total=0.0;
//        }
//        double itemTotal=Math.round(managementCart.getTotalFee()*100)/100;
//        totalFeeTxt.setText("$"+itemTotal);
//        taxTxt.setText("$"+tax);
//        deliveryTxt.setText("$"+delivery);
//        totalTxt.setText("$"+total);
//
//    }