package com.congestion.app.congestionCtrl.rule.taxRule;

import com.congestion.app.congestionCtrl.contant.CongestionConstant;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxModel;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaxTaxPerDayRule implements ITaxRule {

    @Override
    public void apply(TaxModel taxModel) {
        double totalTaxPaid = getTotalTaxPaidInOneDay(taxModel);
        if(totalTaxPaid < CongestionConstant.MAX_TOLL ) {
            if(taxModel.getToll() > (CongestionConstant.MAX_TOLL - totalTaxPaid)){
                taxModel.setToll(CongestionConstant.MAX_TOLL - totalTaxPaid);
            }
        }
    }

    public double getTotalTaxPaidInOneDay(TaxModel taxModel) {
        List<TaxAppliedModel> taxsPaidInOneDay = Util.findTaxsPaidInOneDay(taxModel.getVehicleNum(), taxModel.getDrivingInDateAndTime());
        double totalTaxPaid = taxsPaidInOneDay.stream().map(taxPaid -> taxPaid.getTaxAmt()).reduce(0.0, (prevtax, currTax) -> prevtax + currTax);
        return totalTaxPaid;
    }


}
