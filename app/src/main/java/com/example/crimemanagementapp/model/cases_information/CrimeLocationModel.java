package com.example.crimemanagementapp.model.cases_information;

import com.google.gson.annotations.SerializedName;

public class CrimeLocationModel {
    @SerializedName("crime_id")
    int crimeId;
    int id;
    String address;

    public CrimeLocationModel(int crimeId, int id, String address) {
        this.crimeId = crimeId;
        this.id = id;
        this.address = address;
    }

    public CrimeLocationModel(String address) {

        this.address = address;
    }

    public int getCrimeId() {
        return crimeId;
    }

    @Override
    public String toString() {
        return "CrimeLocationModel{" +
                "crimeId=" + crimeId +
                ", address='" + address + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCrimeId(int crimeId) {
        this.crimeId = crimeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
