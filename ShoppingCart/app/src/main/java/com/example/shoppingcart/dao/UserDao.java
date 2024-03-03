package com.example.shoppingcart.dao;

import com.example.shoppingcart.entity.User;
import com.example.shoppingcart.Helper.JDBCUtils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * author: yan
 * date: 2022.02.17
 **/
public class UserDao {

    private static final String TAG = "mysql-shopping-UserDao";
    public User getUserByCredentials(String userAccount, String userPassword) {
        Connection connection = JDBCUtils.getConn();
        User user = null;

        try {
            String sql = "SELECT * FROM user WHERE userAccount = ? AND userPassword = ?";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, userAccount);
                    ps.setString(2, userPassword);

                    ResultSet rs = ps.executeQuery();

                    // 如果存在匹配的记录，创建User对象
                    if (rs.next()) {
                        user = new User(
                                rs.getInt("id"),
                                rs.getString("userAccount"),
                                rs.getString("userPassword"),
                                rs.getString("userName"),
                                rs.getInt("userType"),
                                rs.getInt("userState"),
                                rs.getInt("userDel")
                        );
                    }

                    connection.close();
                    ps.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常getUserByCredentials：" + e.getMessage());
        }

        return user;
    }


    /**
     * function: 登录
     */
    public int login(String userAccount, String userPassword) {
        String userInstanceAccount = null;
        String userInstancePassword=null;
        String userInstanceName=null;
        int userInstanceType=0;
        int userInstanceState=0;
        int userInstanceDel=0;
        int id = 0;

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Log.d(TAG, "login: 下一步要建立连接了");
        Connection connection = JDBCUtils.getConn();
        int msg = 0;
        Log.d(TAG, "login: 即将进入数据库登录逻辑");
        try {
            // mysql简单的查询语句。这里是根据user表的userAccount字段来查询某条记录
            String sql = "select * from user where userAccount = ?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    Log.e(TAG, "账号：" + userAccount);
                    //根据账号进行查询
                    ps.setString(1, userAccount);
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();


                    //将查到的内容储存在map里
                    if (rs.next()) {

                        // 查询数据库后得到字段值
                        id = rs.getInt("id");
                        userInstanceAccount = rs.getString("userAccount");
                        userInstancePassword = rs.getString("userPassword");
                        userInstanceName = rs.getString("userName");
                        userInstanceType = rs.getInt("userType");
                        userInstanceState = rs.getInt("userState");
                        userInstanceDel = rs.getInt("userDel");

// 实例化User对象

                        // 注意：下标是从1开始的
                        for (int i = 1; i <= count; i++) {
                            String field = rs.getMetaData().getColumnName(i);
                            map.put(field, rs.getString(field));
                        }
                    }
                    connection.close();
                    Log.d(TAG, "login: 已经关闭数据库连接");
                    ps.close();

                    if (map.size() != 0) {
                        StringBuilder s = new StringBuilder();
                        //寻找密码是否匹配
                        for (String key : map.keySet()) {
                            if (key.equals("userPassword")) {
                                if (userPassword.equals(map.get(key))) {
                                    msg = 1;            //密码正确
                                    Log.d(TAG, "login: 密码正确！！！传回msg=1");
                                    User userInstance =User.getUserInstance();
                                    userInstance.setId(id);
                                    userInstance.setUserAccount(userInstanceAccount);
                                    userInstance.setUserPassword(userInstancePassword);
                                    userInstance.setUserName(userInstanceName);
                                    userInstance.setUserType(userInstanceType);
                                    userInstance.setUserState(userInstanceState);
                                    userInstance.setUserDel(userInstanceDel);

                                } else
                                    msg = 2;            //密码错误
                                break;
                            }
                        }
                    } else {
                        Log.e(TAG, "查询结果为空");
                        msg = 3;
                    }
                } else {
                    msg = 0;
                }
            } else {
                Log.d(TAG, "login: 数据库创建连接失败");
                msg = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常login：" + e.getMessage());
            msg = 0;
        }
        return msg;
    }


    /**
     * function: 注册
     */
    public boolean register(User user) {
        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();

        try {
            String sql = "insert into user(userAccount,userPassword,userName,userType,userState,userDel) values (?,?,?,?,?,?)";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {

                    //将数据插入数据库
                    ps.setString(1, user.getUserAccount());
                    ps.setString(2, user.getUserPassword());
                    ps.setString(3, user.getUserName());
                    ps.setInt(4, user.getUserType());
                    ps.setInt(5, user.getUserState());
                    ps.setInt(6, user.getUserDel());

                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs > 0)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常register：" + e.getMessage());
            return false;
        }

    }

    /**
     * function: 根据账号进行查找该用户是否存在
     */
    public User findUser(String userAccount) {

        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        User user = null;
        try {
            String sql = "select * from user where userAccount = ?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, userAccount);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        //注意：下标是从1开始
                        int id = rs.getInt(1);
                        String userAccount1 = rs.getString(2);
                        String userPassword = rs.getString(3);
                        String userName = rs.getString(4);
                        int userType = rs.getInt(5);
                        int userState = rs.getInt(6);
                        int userDel = rs.getInt(7);
                        user = new User(id, userAccount1, userPassword, userName, userType, userState, userDel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常findUser：" + e.getMessage());
            return null;
        }
        return user;
    }

}
