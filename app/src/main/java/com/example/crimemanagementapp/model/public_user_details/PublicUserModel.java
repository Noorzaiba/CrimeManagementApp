package com.example.crimemanagementapp.model.public_user_details;

import com.example.crimemanagementapp.model.miscellaneous.AddressObject;

import java.util.List;

public class PublicUserModel {
    int id;
    String first_name;
    String last_name;
    String email_id;
    String dob;
    long phone_no;
    String gender;
    String password;
    long adhaar_no;
    String doc;
    String dou;
    boolean status;
    boolean flag;
    String profile_image;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" +id +
                ",\"first_name\":\"" + first_name + '\"' +
                ",\"last_name\":\"" + last_name + '\"' +
                ",\"email_id\":\"" + email_id + '\"' +
                ",\"date\":\"" + dob+ '\"' +
                ",\"phone_no\":" + phone_no +
                ",\"gender\":\"" + gender + '\"' +
                ",\"password\":\"" + password + '\"' +
                '}';
    }

    public PublicUserModel(String first_name, String last_name, String email_id, String dob, long phone_no, String gender, String password,long adhaar_no) {
        this.dob=dob;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.phone_no = phone_no;
        this.gender = gender;
        this.password = password;
        this.adhaar_no=adhaar_no;

    }

    public PublicUserModel(int id, String first_name, String last_name, String email_id, long phone_no,  String dob,String gender, long adhaar_no,String  profile_image) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.dob = dob;
        this.phone_no = phone_no;
        this.gender = gender;
        this.password = password;
        this.adhaar_no = adhaar_no;
        this.doc = doc;
        this.dou = dou;
        this.status = status;
        this.flag = flag;
        this.profile_image=profile_image;
    }

    public PublicUserModel(int id, String first_name, String email_id) {
        this.id = id;
        this.first_name = first_name;
        this.email_id = email_id;

    }

    public PublicUserModel(  String email_id, String password) {
        this.email_id = email_id;
        this.password = password;
    }

    public PublicUserModel(int id, String first_name, String last_name, String email_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public long getAdhaar_no() {
        return adhaar_no;
    }

    public void setAdhaar_no(long adhaar_no) {
        this.adhaar_no = adhaar_no;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
