package com.congestion.app.congestionCtrl.initializedata;

import com.congestion.app.congestionCtrl.model.CongestionTaxModel;
import com.congestion.app.congestionCtrl.model.TaxAppliedModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class LoadData {

    public static List<CongestionTaxModel> congestionTaxModelList = new ArrayList<>();
    public static Map<String, Integer> tollFreeVehicleMap = new HashMap<>();
    public static Map<String, List<TaxAppliedModel>> vehicleDtlTaxMap = new HashMap<>();
    public static Set<String> publicHolidays = new HashSet<>();

    @PostConstruct
    public void loadCongestionTaxData() {
        initializeCongestionTaxData();
        initializeTollFreeVehicles();
        initializePublicHoliday();
    }

    public void initializeCongestionTaxData() {
        congestionTaxModelList.add(new CongestionTaxModel("06:00:00", "06:29:00", 8.0));
        congestionTaxModelList.add(new CongestionTaxModel("06:30:00", "06:59:00", 13.0));
        congestionTaxModelList.add(new CongestionTaxModel("07:00:00", "07:59:00", 18.0));
        congestionTaxModelList.add(new CongestionTaxModel("08:00:00", "08:29:00", 13.0));
        congestionTaxModelList.add(new CongestionTaxModel("08:30:00", "14:59:00", 8.0));
        congestionTaxModelList.add(new CongestionTaxModel("15:00:00", "15:29:00", 13.0));
        congestionTaxModelList.add(new CongestionTaxModel("15:30:00", "16:59:00", 18.0));
        congestionTaxModelList.add(new CongestionTaxModel("17:00:00", "17:59:00", 13.0));
        congestionTaxModelList.add(new CongestionTaxModel("18:00:00", "18:29:00", 8.0));
        congestionTaxModelList.add(new CongestionTaxModel("18:30:00", "05:59:00", 0.0));
    }

    public void initializeTollFreeVehicles() {
        tollFreeVehicleMap.put("Motorcycle", 0);
        tollFreeVehicleMap.put("Tractor", 1);
        tollFreeVehicleMap.put("Emergency", 2);
        tollFreeVehicleMap.put("Diplomat", 3);
        tollFreeVehicleMap.put("Foreign", 4);
        tollFreeVehicleMap.put("Military", 5);
    }

    public void initializePublicHoliday(){
        //publicHolidays.add("ddMMyyyy");
        publicHolidays.add("26Jan2022");
        publicHolidays.add("01Mar2022");
        publicHolidays.add("18Mar2022");
        publicHolidays.add("15Apr2022");
        publicHolidays.add("01May2022");
        publicHolidays.add("15Aug2022");
        publicHolidays.add("24Oct2022");
        publicHolidays.add("08Nov2022");
        publicHolidays.add("25Dec2022");

    }

}
