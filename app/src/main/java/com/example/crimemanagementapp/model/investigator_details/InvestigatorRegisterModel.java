package com.example.crimemanagementapp.model.investigator_details;

import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvestigatorRegisterModel {

    int id;
    String first_name;
    String last_name;
    @SerializedName("email")
    String email_id;
    String dob;
    long phone_no;
    String gender;
    String password;
    boolean status;
    long adhaar_no;

    @SerializedName("is_superuser")
    boolean isSuperuser;
    @SerializedName("is_active")
    boolean isActive;
    @SerializedName("is_staff")
    boolean isStaff;
    String profile_image;


    public InvestigatorRegisterModel(int id, String first_name, String last_name, String email_id, String dob, long phone_no, String gender,   long adhaar_no, boolean isSuperuser, boolean isActive, boolean isStaff,  String profile_image) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.dob = dob;
        this.phone_no = phone_no;
        this.gender = gender;
        this.adhaar_no = adhaar_no;
        this.isSuperuser = isSuperuser;
        this.isActive = isActive;
        this.isStaff = isStaff;
        this.profile_image=profile_image;
    }

    public InvestigatorRegisterModel(int id, String first_name, String last_name, String email_id, String dob, long phone_no, String gender, long adhaar_no,  String profile_image) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.dob = dob;
        this.phone_no = phone_no;
        this.gender = gender;
        this.profile_image=profile_image;
        this.adhaar_no = adhaar_no;

    }

    public InvestigatorRegisterModel( String first_name, String last_name, String email_id, String dob, long phone_no, String gender, String password,  long adhaar_no,  String profile_image) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.dob = dob;
        this.phone_no = phone_no;
        this.gender = gender;
        this.password = password;
        this.profile_image=profile_image;
        this.adhaar_no = adhaar_no;

    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
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

    public boolean isSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(boolean superuser) {
        isSuperuser = superuser;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getAdhaar_no() {
        return adhaar_no;
    }

    public void setAdhaar_no(long adhaar_no) {
        this.adhaar_no = adhaar_no;
    }



}
