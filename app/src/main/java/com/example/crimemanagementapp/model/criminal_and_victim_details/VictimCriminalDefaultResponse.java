package com.example.crimemanagementapp.model.criminal_and_victim_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VictimCriminalDefaultResponse {
    boolean flag;
    @SerializedName("serialized_data")
    List<VictimCriminalRegisterModel> victimCriminalRegisterModelList;


    public VictimCriminalDefaultResponse(boolean flag, List<VictimCriminalRegisterModel> victimCriminalRegisterModelList) {
        this.flag = flag;
        this.victimCriminalRegisterModelList = victimCriminalRegisterModelList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<VictimCriminalRegisterModel> getVictimCriminalRegisterModelList() {
        return victimCriminalRegisterModelList;
    }

    public void setVictimCriminalRegisterModelList(List<VictimCriminalRegisterModel> victimCriminalRegisterModelList) {
        this.victimCriminalRegisterModelList = victimCriminalRegisterModelList;
    }
}
