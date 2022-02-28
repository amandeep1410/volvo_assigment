package com.congestion.app.congestionCtrl.model;

public class TaxModel extends TaxInputModel {

    private double toll;

    public TaxModel(double toll, TaxInputModel taxInputModel) {
        this.toll = toll;
        this.city = taxInputModel.getCity();
        this.drivingInDateAndTime = taxInputModel.getDrivingInDateAndTime();
        this.vehicleNum = taxInputModel.getVehicleNum();
        this.vehicleType = taxInputModel.getVehicleType();
    }

    public double getToll() {
        return toll;
    }

    public void setToll(double toll) {
        this.toll = toll;
    }
}
