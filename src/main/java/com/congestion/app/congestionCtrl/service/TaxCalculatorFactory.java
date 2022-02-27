package com.congestion.app.congestionCtrl.service;

import com.congestion.app.congestionCtrl.service.calculator.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TaxCalculatorFactory {

    @Autowired
    List<TaxCalculator> taxCalculators;

    private static final Map<String, TaxCalculator> taxCalcCache = new HashMap<>();

    @PostConstruct
    public void initializeTacCalculators(){
        taxCalculators.stream().forEach(taxCalc -> taxCalcCache.put(taxCalc.getTaxCalculatorCity(), taxCalc));
    }

    public static TaxCalculator getTaxCalculator(String city) {
        return taxCalcCache.get(city);
    }

}
