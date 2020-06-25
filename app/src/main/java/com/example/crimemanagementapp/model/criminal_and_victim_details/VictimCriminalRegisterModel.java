package com.example.crimemanagementapp.model.criminal_and_victim_details;

import com.example.crimemanagementapp.model.miscellaneous.AddressObject;

import java.io.Serializable;
import java.util.List;

public class VictimCriminalRegisterModel implements Serializable {

    int id;
    String first_name;
    String last_name;
    String email;
    int age;
    long phone_no;
    String gender;
    long salary;
    String occupation;
int crime_id;
    String doc;
    String dou;
    long adhaar_no;
    String remarks;
    String profile_image;


    public VictimCriminalRegisterModel(int id, String first_name, String last_name, String email, int age, long phone_no,String remarks, String gender, long salary, String occupation, int crime_id, String doc, String dou, long adhaar_no,String  profile_image) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.age = age;
        this.phone_no = phone_no;
        this.remarks=remarks;
        this.gender = gender;
        this.salary = salary;
        this.occupation = occupation;
        this.crime_id = crime_id;
        this.doc = doc;
        this.dou = dou;
        this.adhaar_no = adhaar_no;
        this.profile_image=profile_image;

}


    @Override
    public String toString() {
        return "VictimCriminalRegisterModel{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phone_no=" + phone_no +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", occupation='" + occupation + '\'' +
                ", crime_id=" + crime_id +
                ", doc='" + doc + '\'' +
                ", dou='" + dou + '\'' +
                ", adhaar_no=" + adhaar_no +
                ", remarks='" + remarks + '\'' +

                '}';
    }

    public VictimCriminalRegisterModel(String first_name, String last_name, String email, int age, long phone_no, String remarks, String gender, long salary, String occupation, int crime_id, long adhaar_no,String profile_image) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.age = age;
        this.phone_no = phone_no;
        this.gender = gender;
        this.remarks=remarks;
        this.salary = salary;
        this.occupation = occupation;
        this.crime_id = crime_id;
        this.adhaar_no = adhaar_no;
        this.profile_image=profile_image;

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }



    public int getCrime_id() {
        return crime_id;
    }

    public void setCrime_id(int crime_id) {
        this.crime_id = crime_id;
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

    public long getAdhaar_no() {
        return adhaar_no;
    }

    public void setAdhaar_no(long adhaar_no) {
        this.adhaar_no = adhaar_no;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}

