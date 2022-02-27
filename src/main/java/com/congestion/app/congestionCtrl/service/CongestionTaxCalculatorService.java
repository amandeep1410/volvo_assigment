package com.congestion.app.congestionCtrl.service;

import com.congestion.app.congestionCtrl.contant.CongestionConstant;
import com.congestion.app.congestionCtrl.model.CongestionTaxModel;
import com.congestion.app.congestionCtrl.TollFreeCalculator;
import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.service.calculator.TaxCalculator;
import com.congestion.app.congestionCtrl.util.DateUtil;
import com.congestion.app.congestionCtrl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CongestionTaxCalculatorService {

    public CongestionTaxResponseMdl calculateTax(TaxInputModel taxInputModel) {
        TaxCalculator taxCalculator = TaxCalculatorFactory.getTaxCalculator(taxInputModel.getCity());
        return taxCalculator.calculateTax(taxInputModel);
    }

}
