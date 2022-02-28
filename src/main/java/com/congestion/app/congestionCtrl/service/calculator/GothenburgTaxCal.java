package com.congestion.app.congestionCtrl.service.calculator;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.*;
import com.congestion.app.congestionCtrl.rule.taxRule.ITaxRule;
import com.congestion.app.congestionCtrl.rule.taxRule.SingleChargeRule;
import com.congestion.app.congestionCtrl.rule.tollFreeRule.ITollFree;
import com.congestion.app.congestionCtrl.util.DateUtil;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GothenburgTaxCal implements TaxCalculator {

    @Autowired
    SingleChargeRule singleChargeRule;

    @Autowired
    List<ITollFree> tollFreeValidators;

    @Autowired
    List<ITaxRule> iTaxRulesList;

    @Override
    public CongestionTaxResponseMdl calculateTax(TaxInputModel taxInputModel) {
        if (isTollFree(taxInputModel)) {
            return Util.createTaxResponseMdl(taxInputModel, 0.0);
        } else {
            return calculateCongestionFee(taxInputModel);
        }
    }

    private CongestionTaxResponseMdl calculateCongestionFee(TaxInputModel taxInputModel) {

        checkIfFirstEntrythenAddTollDetail(taxInputModel);

        CongestionTaxModel matchedBasicTaxModel = getBasicTaxSlab(taxInputModel);
        TaxModel taxModel = new TaxModel(matchedBasicTaxModel.getTaxAmt(), taxInputModel);
        iTaxRulesList.stream().forEach(taxRule -> taxRule.apply(taxModel));

        addTaxAppliedModel(taxInputModel, matchedBasicTaxModel);
        return Util.createTaxResponseMdl(taxInputModel, taxModel.getToll());
    }

    private CongestionTaxModel getBasicTaxSlab(TaxInputModel taxInputModel) {
        return (LoadData.congestionTaxModelList.stream()
                .filter(congestionTaxMdl -> DateUtil.checkTime(congestionTaxMdl.getStartTime(),
                        congestionTaxMdl.getEndTime(),
                        DateUtil.getLocalTimeStr(taxInputModel.getDrivingInDateAndTime()))).findAny()).orElse(null);
    }

    private void checkIfFirstEntrythenAddTollDetail(TaxInputModel taxInputModel){
        LoadData.vehicleDtlTaxMap.computeIfAbsent(taxInputModel.getVehicleNum(), k -> new ArrayList<>());
    }

    private TaxAppliedModel addTaxAppliedModel(TaxInputModel taxInputModel, CongestionTaxModel matchedBasicTaxModel) {
        TaxAppliedModel taxAppliedModel = Util.createTaxAppliedModel(taxInputModel, matchedBasicTaxModel);
        LoadData.vehicleDtlTaxMap.get(taxInputModel.getVehicleNum()).add(taxAppliedModel);
        return taxAppliedModel;
    }

    private boolean isTollFree(TaxInputModel taxInputModel) {
        boolean isTollfree = false;
        isTollfree = tollFreeValidators.stream().map(tollValidator -> tollValidator.apply(taxInputModel)).filter(isvalid -> isvalid).findFirst().orElse(false);
        return isTollfree;
    }

    @Override
    public String getTaxCalculatorCity() {
        return "Gothenburg";
    }
}
