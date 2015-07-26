package ru.alex.sibrivertest.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseArrayModel {

    public ResponseArrayModel (int id, String name, int status, String address, float lat, float lon, String created){
        this.id = id;
        this.name = name;
        this.status = status;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.created = created;
    }
    public ResponseArrayModel(){}

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private Integer status;

    @SerializedName("address")
    private String address;

    @SerializedName("lat")
    private float lat;

    @SerializedName("lon")
    private float lon;

    @SerializedName("created")
    private String created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", created='" + created + '\'' +
                '}';
    }
}
