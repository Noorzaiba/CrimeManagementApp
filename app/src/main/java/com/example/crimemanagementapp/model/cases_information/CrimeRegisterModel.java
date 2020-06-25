package com.example.crimemanagementapp.model.cases_information;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrimeRegisterModel {
    int id;

    String description;
    @SerializedName("type_of_crime")
    String typeOfCrime;
 String time;
 String date;
    String doc;
    String dou;
    String status;
    @SerializedName("investigator_id")
    String investigatorId;



    public CrimeRegisterModel( String description, String typeOfCrime, String time,String date, String status, String investigatorId) {
        this.description = description;
        this.typeOfCrime = typeOfCrime;
       this.date=date;
       this.time=time;
        this.status = status;
        this.investigatorId = investigatorId;

    }

    public CrimeRegisterModel(int id, String description, String typeOfCrime, String time,String date, String doc, String dou, String status, String investigatorId) {
        this.id = id;
        this.description = description;
        this.typeOfCrime = typeOfCrime;
        this.date=date;
        this.time=time;
        this.doc = doc;
        this.dou = dou;
        this.status = status;
        this.investigatorId = investigatorId;

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

    @Override
    public String toString() {
        return "CrimeRegisterModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", typeOfCrime='" + typeOfCrime + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", doc='" + doc + '\'' +
                ", dou='" + dou + '\'' +
                ", status='" + status + '\'' +
                ", investigatorId='" + investigatorId + '\'' +
                '}';
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

    public String getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(String investigatorId) {
        this.investigatorId = investigatorId;
    }
}
