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
import android.widget.Toast;

import com.example.shoppingcart.dao.UserDao;
import com.example.shoppingcart.entity.User;


import com.example.shoppingcart.R;

import java.lang.ref.WeakReference;

public class SignUpActivity extends AppCompatActivity {
    //0失败1重复2成功

    private static final String TAG = "mysql-party-register";
    EditText userAccount = null;
    EditText userPassword = null;
    EditText userName = null;
    AppCompatButton signUpBtn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpBtn=findViewById(R.id.signUpBtn);
        userAccount = findViewById(R.id.signUpUserEdt);
        userPassword = findViewById(R.id.signUpPassEdt);
        userName = findViewById(R.id.signUpUserNameEdt);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

    }


    public void register(View view){

        String userAccount1 = userAccount.getText().toString();
        String userPassword1 = userPassword.getText().toString();
        String userName1 = userName.getText().toString();


        User user = new User();

        user.setUserAccount(userAccount1);
        user.setUserPassword(userPassword1);
        user.setUserName(userName1);
        user.setUserType(1);
        user.setUserState(0);
        user.setUserDel(0);

        new Thread(){
            @Override
            public void run() {

                int msg = 0;

                UserDao userDao = new UserDao();

                User uu = userDao.findUser(user.getUserAccount());
                if(uu != null){
                    msg = 1;
                }
                else{
                    boolean flag = userDao.register(user);
                    if(flag){
                        msg = 2;
                    }
                }
                hand.sendEmptyMessage(msg);

            }
        }.start();


    }
    @SuppressLint("HandlerLeak")
    private static class MyHandler extends Handler {
        private final WeakReference<SignUpActivity> activityReference;

        MyHandler(SignUpActivity activity, Looper looper) {
            super(looper);
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "run: 现在进入了注册判断");
            SignUpActivity activity = activityReference.get();
            if (activity == null) {
                // Activity has been garbage collected, handle it appropriately
                return;
            }

            // Handle the message based on its 'what' attribute
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(activity.getApplicationContext(), "该账号已经存在，请换一个账号", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(activity.getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(activity,MainActivity.class);
//                    intent.putExtra("a", "注册");

                    activity.setResult(RESULT_CANCELED, intent);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
            }
        }
    }

    // Declare the handler using the static inner class
    @SuppressLint("HandlerLeak")
    private final Handler hand = new MyHandler(this, Looper.getMainLooper());
}