package com.example.crimemanagementapp.model.cases_information;

import java.util.List;

public class CrimeScenePicturesDefaultResponse {
    boolean flag;
    List<CrimeScenePicturesModel> serialized_data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<CrimeScenePicturesModel> getSerialized_data() {
        return serialized_data;
    }

    public void setSerialized_data(List<CrimeScenePicturesModel> serialized_data) {
        this.serialized_data = serialized_data;
    }
}