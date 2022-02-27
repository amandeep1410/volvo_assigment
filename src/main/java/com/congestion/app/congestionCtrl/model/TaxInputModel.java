package com.congestion.app.congestionCtrl.model;

import java.time.Instant;

public class TaxInputModel {

    Instant drivingInDateAndTime;
    String vehicleNum;
    String vehicleType;
    String city;

    public Instant getDrivingInDateAndTime() {
        return drivingInDateAndTime;
    }

    public void setDrivingInDateAndTime(Instant drivingInDateAndTime) {
        this.drivingInDateAndTime = drivingInDateAndTime;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
