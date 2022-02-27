package com.congestion.app.congestionCtrl.service.calculator;

import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxInputModel;

public interface TaxCalculator {

    public CongestionTaxResponseMdl calculateTax(TaxInputModel taxInputModel);

    public String getTaxCalculatorCity();

}
