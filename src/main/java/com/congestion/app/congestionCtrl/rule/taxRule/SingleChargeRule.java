package com.congestion.app.congestionCtrl.rule.taxRule;

import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxModel;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleChargeRule implements ITaxRule {

    @Override
    public void apply(TaxModel taxModel) {
        List<TaxAppliedModel> taxAppliedModelPrevToll = Util.findListOfTollsWitinXMins(taxModel.getVehicleNum(), taxModel.getDrivingInDateAndTime(), 60);

        if(!taxAppliedModelPrevToll.isEmpty()) {
            TaxAppliedModel taxAppModelWithMaxToll = Util.findModelWithMaxToll(taxAppliedModelPrevToll);

            double toll = taxModel.getToll();
            if(taxAppModelWithMaxToll.getTaxAmt() < taxModel.getToll()){
                toll = taxModel.getToll() - taxAppModelWithMaxToll.getTaxAmt();
            } else {
                toll = 0.0;
            }
            taxModel.setToll(toll);
        }
    }
}
