package com.example.crimemanagementapp.model.crime_reported_details;

import java.io.Serializable;
import java.util.List;

public class CrimeReportedDefaultResponse implements Serializable {

    List<CrimeReportedByPublicModel> serialized_data_crime_reported;
    boolean flag;

    public List<CrimeReportedByPublicModel> getSerialized_data_crime_reported() {
        return serialized_data_crime_reported;
    }

    public void setSerialized_data_crime_reported(List<CrimeReportedByPublicModel> serialized_data_crime_reported) {
        this.serialized_data_crime_reported = serialized_data_crime_reported;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
