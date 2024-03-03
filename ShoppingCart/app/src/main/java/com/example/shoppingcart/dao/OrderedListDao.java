package com.example.shoppingcart.dao;

import android.util.Log;

import com.example.shoppingcart.Domain.FoodDomain;
import com.example.shoppingcart.Helper.JDBCUtils;
import com.example.shoppingcart.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderedListDao {
    private static final String TAG="mysql-OrderedListDao";
    public boolean addToOrderedListDB(User user, ArrayList<FoodDomain> foodList) throws SQLException {
        Log.d(TAG, "addToOrderedListDB: 购物车要建立连接了");
        Connection connection = JDBCUtils.getConn();
        Log.d(TAG, "addToOrderedListDB: 购物车成功建立连接");
        try {
            String sql = "INSERT INTO shopping_list (user_id, title, pic, description, fee, number_in_cart) VALUES (?, ?, ?, ?, ?, ?)";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    for (FoodDomain food : foodList) {
                        ps.setInt(1, user.getId());  // 使用用户的id关联购物清单
                        ps.setString(2, food.getTitle());
                        ps.setString(3, food.getPic());
                        ps.setString(4, food.getDescription());
                        ps.setDouble(5, food.getFee());
                        ps.setInt(6, food.getNumberInCart());

                        ps.addBatch();  // 将当前项添加到批处理中
                    }

                    int[] results = ps.executeBatch();
                    Log.d(TAG, "addToOrderedListDB: 应该把所有下单食物都放进去了");
                    // 检查是否所有项都成功插入
                    for (int result : results) {
                        if (result <= 0) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常addToShoppingCart：" + e.getMessage());
            return false;
        } finally {
            // 在 finally 块中关闭数据库连接，确保资源释放
            if (connection != null) {
                connection.close();
                Log.d(TAG, "addToOrderedListDB: 关闭数据库连接");
            }
        }
    }
    public ArrayList<FoodDomain> getOrderedListFromDB(User user) throws SQLException {
        Log.d(TAG, "getShoppingListFromDB: 获取购物车数据，准备建立连接");
        Connection connection = JDBCUtils.getConn();
        Log.d(TAG, "getShoppingListFromDB: 购物车数据连接成功建立");

        ArrayList<FoodDomain> shoppingList = new ArrayList<>();

        try {
            String sql = "SELECT title, pic, description, fee, number_in_cart FROM shopping_list WHERE user_id = ?";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setInt(1, user.getId());  // 使用用户的id获取购物清单
                    Log.d(TAG, "getOrderedListFromDB:用户id "+user.getId());
                    ResultSet rs = ps.executeQuery();
                    if(rs!=null)
                    {
                        Log.d(TAG, "getOrderedListFromDB:rs!=null ");
                    }

                    while (rs.next()) {
                        FoodDomain food = new FoodDomain();
                        food.setTitle(rs.getString("title"));
                        food.setPic(rs.getString("pic"));
                        food.setDescription(rs.getString("description"));
                        food.setFee(rs.getDouble("fee"));
                        food.setNumberInCart(rs.getInt("number_in_cart"));

                        shoppingList.add(food);
                    }
                    if (shoppingList != null) {
                        for (FoodDomain food : shoppingList) {
                            Log.d(TAG, "getOrderedListFromDB: 打印食物数据"+food.toString());
                        }
                    } else {
                        System.out.println("获取购物车数据失败");
                    }
                    Log.d(TAG, "getShoppingListFromDB: 获取购物车数据成功");

                    return shoppingList;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常getShoppingListFromDB：" + e.getMessage());
            return null;
        } finally {
            // 在 finally 块中关闭数据库连接，确保资源释放
            if (connection != null) {
                connection.close();
                Log.d(TAG, "getShoppingListFromDB: 关闭数据库连接");
            }
        }
    }

}
