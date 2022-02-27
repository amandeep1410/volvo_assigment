package com.congestion.app.congestionCtrl.service.calculator;

import com.congestion.app.congestionCtrl.TollFreeCalculator;
import com.congestion.app.congestionCtrl.contant.CongestionConstant;
import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.CongestionTaxModel;
import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.rule.MaxTaxPerDayRule;
import com.congestion.app.congestionCtrl.rule.SingleChargeRule;
import com.congestion.app.congestionCtrl.util.DateUtil;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GothenburgTaxCal implements TaxCalculator {

    @Autowired
    TollFreeCalculator tollFreeCalculator;

    @Autowired
    SingleChargeRule singleChargeRule;

    @Autowired
    MaxTaxPerDayRule maxTaxPerDayRule;

    @Override
    public CongestionTaxResponseMdl calculateTax(TaxInputModel taxInputModel) {
        if (tollFreeCalculator.isVehicleTollFree(taxInputModel.getVehicleType()) ||
                tollFreeCalculator.isTollFreeDate(taxInputModel.getDrivingInDateAndTime()) ||
                tollFreeCalculator.isTollFreeTime(taxInputModel) ||
                maxTaxPerDayRule.isMaxTollPaid(taxInputModel)
        ) {
            return Util.createTaxResponseMdl(taxInputModel, 0.0);
        } else {
            return calculateCongestionFee(taxInputModel);
        }
    }

    private CongestionTaxResponseMdl calculateCongestionFee(TaxInputModel taxInputModel) {

        checkIfFirstEntrythenAddTollDetail(taxInputModel);

        CongestionTaxModel matchedBasicTaxModel = getBasicTaxSlab(taxInputModel);
        TaxAppliedModel prevMaxTollApplied = singleChargeRule.getPrevMaxTollApplied(taxInputModel);

        double toll = getTollAmt(matchedBasicTaxModel, prevMaxTollApplied, taxInputModel);

        addTaxAppliedModel(taxInputModel, matchedBasicTaxModel);
        return Util.createTaxResponseMdl(taxInputModel, toll);
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

    private double getTollAmt(CongestionTaxModel matchedBasicTaxModel, TaxAppliedModel prevMaxTollApplied, TaxInputModel taxInputModel){
        double toll = matchedBasicTaxModel.getTaxAmt();
        if(isVehicleOutsideTaxHours(matchedBasicTaxModel) && !isCarEnteringTollFirstTime(prevMaxTollApplied)) {
            if(prevMaxTollApplied.getTaxAmt() < matchedBasicTaxModel.getTaxAmt()){
                toll = matchedBasicTaxModel.getTaxAmt() - prevMaxTollApplied.getTaxAmt();
            } else {
                toll = 0.0;
            }
        }
        if(CongestionConstant.MAX_TOLL - maxTaxPerDayRule.getTotalTaxPaidInOneDay(taxInputModel) < toll) {
            toll = CongestionConstant.MAX_TOLL - maxTaxPerDayRule.getTotalTaxPaidInOneDay(taxInputModel);
        }
        return toll;
    }

    private boolean isVehicleOutsideTaxHours(CongestionTaxModel matchedBasicTaxModel){
        return matchedBasicTaxModel.getTaxAmt() > CongestionConstant.ZERO;
    }

    private boolean isCarEnteringTollFirstTime(TaxAppliedModel prevMaxTollApplied){
        return prevMaxTollApplied == null;
    }

    @Override
    public String getTaxCalculatorCity() {
        return "Gothenburg";
    }
}
