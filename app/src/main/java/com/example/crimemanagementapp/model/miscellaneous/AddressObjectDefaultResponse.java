package com.example.crimemanagementapp.model.miscellaneous;

import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressObjectDefaultResponse {

    boolean flag;
    @SerializedName("serialized_data")
    List<AddressObject> serailizedData;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<AddressObject> getSerailizedData() {
        return serailizedData;
    }

    public void setSerailizedData(List<AddressObject> serailizedData) {
        this.serailizedData = serailizedData;
    }
}
