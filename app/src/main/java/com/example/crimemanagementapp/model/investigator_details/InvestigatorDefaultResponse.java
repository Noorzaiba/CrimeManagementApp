package com.example.crimemanagementapp.model.investigator_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvestigatorDefaultResponse {

    boolean flag;
    @SerializedName("serialized_data")
    List<InvestigatorAdministrativeInformationModel> serailizedData;
    @SerializedName("serialized_investigator_register_data")
    List<InvestigatorRegisterModel> serailizedInvestigatorModel;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<InvestigatorAdministrativeInformationModel> getSerailizedData() {
        return serailizedData;
    }

    public void setSerailizedData(List<InvestigatorAdministrativeInformationModel> serailizedData) {
        this.serailizedData = serailizedData;
    }


    public List<InvestigatorRegisterModel> getSerailizedInvestigatorModel() {
        return serailizedInvestigatorModel;
    }

    public void setSerailizedInvestigatorModel(List<InvestigatorRegisterModel> serailizedInvestigatorModel) {
        this.serailizedInvestigatorModel = serailizedInvestigatorModel;
    }
}
