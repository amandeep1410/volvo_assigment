package com.congestion.app.congestionCtrl.controller;

import com.congestion.app.congestionCtrl.model.CongestionTaxResponseMdl;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.service.CongestionTaxCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CongestionTaxCalculatorCtrl {

    @Autowired
    CongestionTaxCalculatorService congestionTaxCalculatorService;

    @PostMapping(value = "/calcToll")
    public ResponseEntity<CongestionTaxResponseMdl> getCongestionTax(@RequestBody TaxInputModel taxInputModel) {
        CongestionTaxResponseMdl congestionTaxRespMdl = congestionTaxCalculatorService.calculateTax(taxInputModel);
        return new ResponseEntity<CongestionTaxResponseMdl>(congestionTaxRespMdl, HttpStatus.OK);
    }

}
