package com.congestion.app.congestionCtrl.rule;

import com.congestion.app.congestionCtrl.contant.CongestionConstant;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaxTaxPerDayRule {

    public boolean isMaxTollPaid(TaxInputModel taxInputModel) {
        double totalTaxPaid = getTotalTaxPaidInOneDay(taxInputModel);
        if(totalTaxPaid < CongestionConstant.MAX_TOLL ) {
            return false;
        } else {
            return true;
        }
    }

    public double getTotalTaxPaidInOneDay(TaxInputModel taxInputModel) {
        List<TaxAppliedModel> taxsPaidInOneDay = Util.findTaxsPaidInOneDay(taxInputModel.getVehicleNum(), taxInputModel.getDrivingInDateAndTime());
        double totalTaxPaid = taxsPaidInOneDay.stream().map(taxPaid -> taxPaid.getTaxAmt()).reduce(0.0, (prevtax, currTax) -> prevtax + currTax);
        return totalTaxPaid;
    }

}
