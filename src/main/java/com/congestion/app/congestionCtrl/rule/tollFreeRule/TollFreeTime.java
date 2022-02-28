package com.congestion.app.congestionCtrl.rule.tollFreeRule;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.util.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class TollFreeTime implements ITollFree {

    @Override
    public boolean apply(TaxInputModel taxInputModel) {
        double toll = (LoadData.congestionTaxModelList.stream()
                .filter(congestionTaxMdl -> DateUtil.checkTime(congestionTaxMdl.getStartTime(),
                        congestionTaxMdl.getEndTime(),
                        DateUtil.getLocalTimeStr(taxInputModel.getDrivingInDateAndTime()))).findAny())
                .map(congestionTaxModel -> congestionTaxModel.getTaxAmt()).orElse(0.0);
        if(toll == 0.0) {
            return true;
        } else {
            return false;
        }
    }
}
