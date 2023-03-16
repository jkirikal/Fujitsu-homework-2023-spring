package com.example.homework_v1.controller;

//holds the data for the latest request
public class RequestInfo {
    private String city;
    private String transportation;

    public RequestInfo(String city, String transportation){
        this.city = city;
        this.transportation = transportation;
    }

    public String getCity() {
        return city;
    }

    public String getTransportation() {
        return transportation;
    }
}
