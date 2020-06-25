package com.example.crimemanagementapp.model.query_reporting_contact_us;

public class InvestigatorContactUsModel {
    String description;
    int id;
    String email;
    String status;
    String doc;
    String dou;

    public InvestigatorContactUsModel(String description,  String email, String status) {
        this.description = description;
        this.email = email;
        this.status = status;
    }

    public InvestigatorContactUsModel(String description, int id, String email, String status) {
        this.description = description;
        this.id = id;
        this.email = email;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
