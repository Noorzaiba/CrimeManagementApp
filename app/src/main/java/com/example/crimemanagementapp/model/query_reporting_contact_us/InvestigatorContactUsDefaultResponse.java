package com.example.crimemanagementapp.model.query_reporting_contact_us;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvestigatorContactUsDefaultResponse {
    boolean flag;
    @SerializedName("serialized_data")
    List<InvestigatorContactUsModel> serailizedData;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<InvestigatorContactUsModel> getSerailizedData() {
        return serailizedData;
    }

    public void setSerailizedData(List<InvestigatorContactUsModel> serailizedData) {
        this.serailizedData = serailizedData;
    }
}
