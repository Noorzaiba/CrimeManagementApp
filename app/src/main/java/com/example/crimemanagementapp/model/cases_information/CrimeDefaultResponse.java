package com.example.crimemanagementapp.model.cases_information;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrimeDefaultResponse {

    boolean flag;

    @SerializedName("serialized_data")
    List<CrimeLiveUpdationModel> serialized_data_crime_live_update;
    @SerializedName("serialized_data_crime_register")
    List<CrimeRegisterModel>serialized_data_crime_register;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<CrimeLiveUpdationModel> getSerialized_data_crime_live_update() {
        return serialized_data_crime_live_update;
    }

    public void setSerialized_data_crime_live_update(List<CrimeLiveUpdationModel> serialized_data_crime_live_update) {
        this.serialized_data_crime_live_update = serialized_data_crime_live_update;
    }

    public List<CrimeRegisterModel> getSerialized_data_crime_register() {
        return serialized_data_crime_register;
    }

    public void setSerialized_data_crime_register(List<CrimeRegisterModel> serialized_data_crime_register) {
        this.serialized_data_crime_register = serialized_data_crime_register;
    }
}
