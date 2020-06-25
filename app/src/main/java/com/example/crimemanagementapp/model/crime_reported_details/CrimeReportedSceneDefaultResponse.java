package com.example.crimemanagementapp.model.crime_reported_details;

import java.util.List;

public class CrimeReportedSceneDefaultResponse {
    boolean flag;
    List<CrimeReportedScenePicturesModel> serialized_data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<CrimeReportedScenePicturesModel> getSerialized_data() {
        return serialized_data;
    }

    public void setSerialized_data(List<CrimeReportedScenePicturesModel> serialized_data) {
        this.serialized_data = serialized_data;
    }
}
