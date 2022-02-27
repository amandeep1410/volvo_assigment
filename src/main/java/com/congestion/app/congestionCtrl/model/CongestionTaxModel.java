package com.congestion.app.congestionCtrl.model;

public class CongestionTaxModel {

    String startTime;
    String endTime;
    double taxAmt;

    public CongestionTaxModel(String startTime, String endTime, double taxAmt) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.taxAmt = taxAmt;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

}
