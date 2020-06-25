package com.example.crimemanagementapp.model.cases_information;

public class CrimeScenePicturesModel {

    String crime_id;
    String image_name;
    int id;

    public CrimeScenePicturesModel(String crime_id, String image_name) {
        this.crime_id = crime_id;
        this.image_name = image_name;
    }

    public CrimeScenePicturesModel(String crime_id, String image_name, int id) {
        this.crime_id = crime_id;
        this.image_name = image_name;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrime_id() {
        return crime_id;
    }

    public void setCrime_id(String crime_id) {
        this.crime_id = crime_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
