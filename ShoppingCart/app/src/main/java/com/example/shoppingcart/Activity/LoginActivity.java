package com.example.shoppingcart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.R;
import com.example.shoppingcart.dao.UserDao;
import com.example.shoppingcart.databinding.ActivityLoginBinding;
import com.example.shoppingcart.entity.User;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {
    //0失败1成功2密码错误3账号不存在
    private static final String TAG = "mysql-shopping-Login";
    private static EditText EditTextAccount ;
   private static EditText EditTextPassword ;
    //    private volatile boolean isThreadRunning = true;
    private ActivityLoginBinding binding;
    private AppCompatButton loginBtn;
    private TextView forgetPassBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        EditTextAccount = findViewById(R.id.signUpUserEdt);
        EditTextPassword = findViewById(R.id.signUpPassEdt);
        loginBtn = findViewById(R.id.loginBtn);
        forgetPassBtn = findViewById(R.id.newAccountBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 点击了登录！");
                login(view);
            }
        });

        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里跳转注册页面要不要销毁页面？还没搞
                Log.d(TAG, "onClick: 点击了忘记密码？");
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    //    public void reg(View view){
//        startActivity(new Intent(getApplicationContext(),register.class));
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }


    /**
     * function: 登录
     */
    public void login(View view) {
        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "run: 创建了登录线程！");

                UserDao userDao = new UserDao();
                if (handler != null) {
                    Log.d(TAG, "run: handler已经创建");
                } else {
                    Log.d(TAG, "run: handler并没有创建");
                }
                if (userDao != null) {
                    Log.d(TAG, "run: 成功创建userDao");
                }

                Log.d(TAG, "run: 当前账号栏填的是" + EditTextAccount.getText().toString());
                Log.d(TAG, "run: 当前密码栏填的是" + EditTextPassword.getText().toString());

                int msg = userDao.login(EditTextAccount.getText().toString(), EditTextPassword.getText().toString());
//                int msg=0;
                Log.d(TAG, "run: msg当前值为" + msg);
                if (handler != null) {
                    Log.d(TAG, "run: 向Handler发送了账号密码！当前msg值为" + msg);
                    handler.sendEmptyMessage(msg);


                } else {
                    Log.d(TAG, "run: handler是他妈的null？");
                    // 处理消息队列已经被销毁的情况
                    // ...
                }
                Log.d(TAG, "run: 当前登录线程执行完毕");
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    private static class MyHandler extends Handler {
        private UserDao userDao;
        private final WeakReference<LoginActivity> activityReference;

        MyHandler(LoginActivity activity, Looper looper) {
            super(looper);
            this.activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "run: 现在进入了登录判断");
            LoginActivity activity = activityReference.get();
            if (activity == null) {
                // Activity 已经被回收，处理相应逻辑
                return;
            }

            // 处理消息
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(activity.getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
//                    userDao=new UserDao();
//                    User user=userDao.getUserByCredentials(EditTextAccount.getText().toString(),EditTextPassword.getText().toString());
                    Log.d(TAG, "当前登录成功后，获取到的用户信息如下："+User.getUserInstance());
                    Intent intent = new Intent(activity, MainActivity.class);
                    //将用户传过去
                    intent.putExtra("user",User.getUserInstance());
                    activity.startActivity(intent);
                    activity.finish();//销毁本页
//                        activity.findViewById(R.id.fcv_fragment).requestLayout();
                    break;
                case 2:
                    Toast.makeText(activity.getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(activity.getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
                    break;
            }


        }
    }

    private final Handler handler = new MyHandler(this, Looper.getMainLooper());


}