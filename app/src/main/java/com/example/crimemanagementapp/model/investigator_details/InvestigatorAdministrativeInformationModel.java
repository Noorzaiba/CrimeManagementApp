package com.example.crimemanagementapp.model.investigator_details;

public class InvestigatorAdministrativeInformationModel {

    int id;
    double salary;
    String achivements;
    String position;
    String email;

    public InvestigatorAdministrativeInformationModel(int id, double salary, String achivements, String position, String email) {
        this.id = id;
        this.salary = salary;
        this.achivements = achivements;
        this.position = position;
        this.email = email;
    }

    public InvestigatorAdministrativeInformationModel(double salary, String achivements, String position, String email) {

        this.salary = salary;
        this.achivements = achivements;
        this.position = position;
        this.email = email;
    }

    @Override
    public String toString() {
        return "InvestigatorAdministrativeInformationModel{" +
                "id=" + id +
                ", salary=" + salary +
                ", achivements='" + achivements + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAchivements() {
        return achivements;
    }

    public void setAchivements(String achivements) {
        this.achivements = achivements;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
