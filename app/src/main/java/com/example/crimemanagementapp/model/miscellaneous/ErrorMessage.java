package com.example.crimemanagementapp.model.miscellaneous;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {
    @SerializedName("detail")
    String msg;

    public ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
