package com.example.crimemanagementapp.model.cases_information;

import com.google.gson.annotations.SerializedName;

public class CrimeLiveUpdationModel {
    int id;
    @SerializedName("crime_id")
    int crimeId;
    String comments;
    @SerializedName("doc")
          String  doc;
    @SerializedName("dou")
    String  dou;

    public CrimeLiveUpdationModel(int id, int crimeId, String comments, String doc) {
        this.id = id;
        this.crimeId = crimeId;
        this.comments = comments;
        this.doc = doc;
    }

    @Override
    public String toString() {
        return "CrimeLiveUpdationModel{" +
                "id=" + id +
                ", crimeId=" + crimeId +
                ", comments='" + comments + '\'' +
                ", doc='" + doc + '\'' +
                '}';
    }

    public CrimeLiveUpdationModel(int crimeId, String statement){
    this.crimeId=crimeId;
    this.comments=statement;
}

    public String getDou() {
        return dou;
    }

    public void setDou(String dou) {
        this.dou = dou;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public int getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(int crimeId) {
        this.crimeId = crimeId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
