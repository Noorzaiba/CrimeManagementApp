package com.example.crimemanagementapp.model.accounts;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    @SerializedName("first_name")
    private  String name;
    private String email;
    @SerializedName("last_name")
    private String token;
    @SerializedName("is_superuser")
    private boolean isSuperuser;
    @SerializedName("is_staff")
    private boolean isStaff;


    public User(int id, String name, String email, String token, boolean isSuperuser, boolean isStaff) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
        this.isSuperuser = isSuperuser;
        this.isStaff = isStaff;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(boolean superuser) {
        isSuperuser = superuser;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }
}
