package com.congestion.app.congestionCtrl.rule;

import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleChargeRule {

    public TaxAppliedModel getPrevMaxTollApplied(TaxInputModel taxInputModel) {
        List<TaxAppliedModel> taxAppliedModelPrevToll = Util.findListOfTollsWitinXMins(taxInputModel.getVehicleNum(), taxInputModel.getDrivingInDateAndTime(), 60);
        TaxAppliedModel taxAppModelWithMaxToll = Util.findModelWithMaxToll(taxAppliedModelPrevToll);
        return taxAppModelWithMaxToll;
    }

}
