package com.congestion.app.congestionCtrl.service;

import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.service.calculator.TaxCalculator;
import org.springframework.stereotype.Service;

@Service
public class CongestionTaxCalculatorService {

    public CongestionTaxResponseMdl calculateTax(TaxInputModel taxInputModel) {
        TaxCalculator taxCalculator = TaxCalculatorFactory.getTaxCalculator(taxInputModel.getCity());
        return taxCalculator.calculateTax(taxInputModel);
    }

}
