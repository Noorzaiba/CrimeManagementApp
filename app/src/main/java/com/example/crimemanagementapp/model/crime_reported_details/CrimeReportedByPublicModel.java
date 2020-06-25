package com.example.crimemanagementapp.model.crime_reported_details;

import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CrimeReportedByPublicModel implements Serializable {
    int id;

    String description;
    @SerializedName("type_of_crime")
    String typeOfCrime;
    String time;
    String date;
    String doc;
    String dou;
    String status;
    String user;
    List<AddressObject> address;

    public CrimeReportedByPublicModel( String description, String typeOfCrime, String time, String date, String doc, String dou, String status, String user) {

        this.description = description;
        this.typeOfCrime = typeOfCrime;
        this.time = time;
        this.date = date;
        this.doc = doc;
        this.dou = dou;
        this.status = status;
        this.user = user;
        this.address = address;
    }

    public CrimeReportedByPublicModel(int id, String description, String typeOfCrime, String time, String date, String doc, String dou, String status, String user) {
        this.id = id;
        this.description = description;
        this.typeOfCrime = typeOfCrime;
        this.time = time;
        this.date = date;
        this.doc = doc;
        this.dou = dou;
        this.status = status;
        this.user = user;
        this.address = address;
    }

    public CrimeReportedByPublicModel( String description, String typeOfCrime, String time, String date,String user ) {
        this.description = description;
        this.typeOfCrime = typeOfCrime;
        this.time = time;
        this.date = date;
        this.user=user;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfCrime() {
        return typeOfCrime;
    }

    public void setTypeOfCrime(String typeOfCrime) {
        this.typeOfCrime = typeOfCrime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDou() {
        return dou;
    }

    public void setDou(String dou) {
        this.dou = dou;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<AddressObject> getAddress() {
        return address;
    }

    public void setAddress(List<AddressObject> address) {
        this.address = address;
    }
}
