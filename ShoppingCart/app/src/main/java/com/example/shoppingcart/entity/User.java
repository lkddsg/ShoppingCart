package com.example.shoppingcart.entity;


import java.io.Serializable;

public class User implements Serializable {
    //单例模式
    private static User userInstance;

    public static void setUserInstance(User userInstance) {
        User.userInstance = userInstance;
    }

    // 获取 User 实例的方法
    public static synchronized User getUserInstance() {
        if (userInstance == null) {
            // 如果实例为空，创建一个新的实例
            userInstance = new User();
        }
        return userInstance;
    }


    private int id;
    private String userAccount;
    private String userPassword;
    private String userName;
    private int userType;
    private int userState;
    private int userDel;


    public User() {
    }

    public User(int id, String userAccount, String userPassword, String userName, int userType, int userState, int userDel) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userType = userType;
        this.userState = userState;
        this.userDel = userDel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public int getUserDel() {
        return userDel;
    }

    public void setUserDel(int userDel) {
        this.userDel = userDel;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userAccount='" + userAccount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", userState=" + userState +
                ", userDel=" + userDel +
                '}';
    }
}




