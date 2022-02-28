package com.congestion.app.congestionCtrl.rule.tollFreeRule;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import org.springframework.stereotype.Component;

@Component
public class TollFreeVehicle implements ITollFree{

    @Override
    public boolean apply(TaxInputModel taxInputModel) {
        if(LoadData.tollFreeVehicleMap.get(taxInputModel.getVehicleType()) == null) {
            return false;
        } else {
            return true;
        }
    }
}
