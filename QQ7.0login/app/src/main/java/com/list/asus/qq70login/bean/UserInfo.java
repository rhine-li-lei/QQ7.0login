package com.list.asus.qq70login.bean;

/*
 * Created by ASUS on 2017/6/4.
 */

import java.util.HashMap;

import static android.R.id.list;

public class UserInfo {
    private int avatar;
    private String qqNum;
    private String password;
    private int delete;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }
}
