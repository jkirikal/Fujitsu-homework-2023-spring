package com.example.homework_v1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.sql.Timestamp;

//saves a specific station's latest weather data as a database object
@Entity
@Table(name = "Weather_Data")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phenomenon")
    private String phenomenon;

    @Column(name="wmo_code")
    private int wmo_code;

    @Column(name="air_temp")
    private double air_temp;

    @Column(name="wind_speed")
    private double wind_speed;


    @Column(name="timestamp")
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public int getWmo_code() {
        return wmo_code;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public double getAir_temp() {
        return air_temp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public void setWmo_code(int wmo_code) {
        this.wmo_code = wmo_code;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setAir_temp(double air_temp) {
        this.air_temp = air_temp;
    }
}
