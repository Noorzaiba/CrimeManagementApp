package com.example.crimemanagementapp.model.miscellaneous;

public class PasswordResetModel {
    String email;
    String otp;
    boolean flag;
    String message;
    String password;

    public PasswordResetModel(String email) {
        this.email = email;
    }

    public PasswordResetModel(String email, String otp,boolean flag) {
        this.email = email;
        this.otp = otp;
    }

    public PasswordResetModel(String password,String email) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
