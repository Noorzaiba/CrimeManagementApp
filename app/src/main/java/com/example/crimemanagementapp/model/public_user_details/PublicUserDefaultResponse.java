package com.example.crimemanagementapp.model.public_user_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublicUserDefaultResponse {
    @SerializedName("flag")
    boolean flag;
    @SerializedName("serialized_data")
    List<PublicUserModel> serailizedData;



    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public List<PublicUserModel> getSerailizedData() {
        return serailizedData;
    }

    public void setSerailizedData(List<PublicUserModel> serailizedData) {
        this.serailizedData = serailizedData;
    }
}
