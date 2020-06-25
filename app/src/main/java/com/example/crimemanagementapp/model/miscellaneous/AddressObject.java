package com.example.crimemanagementapp.model.miscellaneous;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressObject  implements Serializable {
    private String location,city,state;
    private  double  longitude,latitude;
    private int id;
    @SerializedName("zip_code")
    private long zipCode;
    @SerializedName("resident_id")
    private int residentId;
    private boolean flag;

    @Override
    public String toString() {
        return '{'+"\"id_address\":" + id +
                ",\"location\":\"" + location + "\"," +
                "\"city\":\"" + city + "\"," +
                "\"state\":\"" + state + "\"," +
                "\"longitude\":" + longitude +
                ",\"latitude\":" + latitude +
                ",\"zipCode\":" + zipCode +
                ",\"public_user\":" + residentId +
                '}';
    }

    public AddressObject(String location, String city, String state, double longitude, double latitude, long zipCode) {
        this.location = location;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.zipCode = zipCode;

    }

    public AddressObject(String location, String city, String state, double longitude,double latitude, long zipCode,int residentId) {
        this.location = location;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.zipCode = zipCode;
        this.residentId=residentId;

    }


    public AddressObject(int id,String location, String city, String state,double longitude, double latitude, long zipCode,int residentId) {
        this.id=id;
        this.location = location;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
        this.zipCode = zipCode;
        this.residentId =residentId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getZipCode() {
        return zipCode;
    }

    public void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}


