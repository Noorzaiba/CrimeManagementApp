package com.example.crimemanagementapp.model.accounts;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("flag")
    private boolean flag;
    @SerializedName("message")
    private String msg;
    private User user;

    public LoginResponse(boolean flag, String msg, User user) {
        this.flag = flag;
        this.msg = msg;
        this.user = user;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }
}
