package com.congestion.app.congestionCtrl.util;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.CongestionTaxModel;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Util {

    public static TaxAppliedModel findModelWithMaxToll(List<TaxAppliedModel> taxAppliedModelList){
        Optional<TaxAppliedModel> taxAppliedModel = taxAppliedModelList.stream().max(Comparator.comparing(taxAppliedMdl -> taxAppliedMdl.getTaxAmt()));
        return taxAppliedModel.orElse(null);
    }

    public static TaxAppliedModel createTaxAppliedModel(TaxInputModel inputModel, CongestionTaxModel congestionTaxModel){
        return new TaxAppliedModel(inputModel.getDrivingInDateAndTime(), inputModel.getVehicleNum(), inputModel.getVehicleType(),
                inputModel.getCity(), congestionTaxModel.getStartTime(), congestionTaxModel.getEndTime(), congestionTaxModel.getTaxAmt());
    }

    public static CongestionTaxResponseMdl createTaxResponseMdl(TaxInputModel inputModel, double toll){
        return new CongestionTaxResponseMdl(inputModel.getDrivingInDateAndTime(),
                inputModel.getVehicleNum(), inputModel.getVehicleType(),inputModel.getCity(), toll);
    }

    public static List<TaxAppliedModel> findListOfTollsWitinXMins(String vehicleNum, Instant currInstant, int mins) {
        List<TaxAppliedModel> taxAppliedModelPrevToll = new ArrayList<>();
        List<TaxAppliedModel> taxAppliedModelList = LoadData.vehicleDtlTaxMap.get(vehicleNum);
        if(taxAppliedModelList != null) {
            for(int index = taxAppliedModelList.size() - 1;index >= 0 ;index--){
                Duration minsBetweenPrevToll = Duration.between(taxAppliedModelList.get(index).getDrivingInDateAndTime(),currInstant);
                if(Math.abs(minsBetweenPrevToll.getSeconds())/60 > mins) {
                    break;
                } else {
                    taxAppliedModelPrevToll.add(taxAppliedModelList.get(index));
                }
            }
        }
        return taxAppliedModelPrevToll;
    }

    public static List<TaxAppliedModel> findTaxsPaidInOneDay(String vehicleNum, Instant currInstant) {
        List<TaxAppliedModel> taxAppliedModelPrevToll = new ArrayList<>();
        List<TaxAppliedModel> taxAppliedModelList = LoadData.vehicleDtlTaxMap.get(vehicleNum);
        Instant currInstantTruc = currInstant.truncatedTo(ChronoUnit.DAYS);

        if(taxAppliedModelList != null) {
            for(int index = taxAppliedModelList.size() - 1;index >= 0 ;index--){
                Instant prevInstant = taxAppliedModelList.get(index).getDrivingInDateAndTime().truncatedTo(ChronoUnit.DAYS);
                if(currInstantTruc.equals(prevInstant)) {
                    taxAppliedModelPrevToll.add(taxAppliedModelList.get(index));
                } else {
                    break;
                }
            }
        }
        return taxAppliedModelPrevToll;
    }

}
