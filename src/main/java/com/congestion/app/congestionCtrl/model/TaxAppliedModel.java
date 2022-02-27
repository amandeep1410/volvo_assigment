package com.congestion.app.congestionCtrl.model;

import java.time.Instant;

public class TaxAppliedModel {

    Instant drivingInDateAndTime;
    String vehicleNum;
    String vehicleType;
    String city;
    String startTime;
    String endTime;
    double taxAmt;

    public TaxAppliedModel(Instant drivingInDateAndTime, String vehicleNum, String vehicleType, String city, String startTime, String endTime, double taxAmt) {
        this.drivingInDateAndTime = drivingInDateAndTime;
        this.vehicleNum = vehicleNum;
        this.vehicleType = vehicleType;
        this.city = city;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taxAmt = taxAmt;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }
}
