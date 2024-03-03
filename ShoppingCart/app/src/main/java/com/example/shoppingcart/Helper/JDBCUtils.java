package com.example.shoppingcart.Helper;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * function： 数据库工具类，连接数据库用
 */
public class JDBCUtils {
    private static final String TAG = "mysql-shopping-JDBC";

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

    private static String dbName = "ShoppingCart";// 数据库名称

    private static String user = "adminShoppingCart";// 用户名

    private static String password = "LkD13410707610";// 密码
    public static Connection connection;

    public static Connection getConn(){

          connection = null;
        try{
            if(connection!=null)
            {
                Log.d(TAG, "getConn: 先前创建的连接没有关闭");
            }
            if(connection==null)
            {
                Log.d(TAG, "getConn: 当前没有连接");
            }
            Class.forName(driver);// 动态加载类
            String ip = "172.16.28.135";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            String connectionString="jdbc:mysql://" + ip + ":3306/" + dbName+
                    user+password;
            Log.d(TAG, "getConn: 语句为："+connectionString);

            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);
            if(connection!=null)
            {
                Log.d(TAG, "getConn:成功建立连接 ");
            }
            if(connection==null){
                Log.d(TAG, "getConn: 执行了连接语句，可是失败了");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
