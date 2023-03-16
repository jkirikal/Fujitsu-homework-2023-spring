package com.example.homework_v1.service;

public class VehicleUsageError extends RuntimeException {


    public VehicleUsageError() {
        super("Usage of selected vehicle type is forbidden");
    }
}