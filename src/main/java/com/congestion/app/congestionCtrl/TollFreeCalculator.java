package com.congestion.app.congestionCtrl;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import com.congestion.app.congestionCtrl.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Component
public class TollFreeCalculator {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");

    public boolean isTollFreeDate(Instant drivingInInstant){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(drivingInInstant, ZoneOffset.UTC);
        return (checkIfWeekend(localDateTime) || isPublicHoliday(localDateTime) || isADayBeforePublicHoliday(localDateTime));
    }

    private boolean checkIfWeekend(LocalDateTime localDateTime){
        boolean isWeekend = false;
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek. SUNDAY) {
            isWeekend = true;
        }
        return isWeekend;
    }

    private boolean isPublicHoliday(LocalDateTime localDateTime){
        String currDate = dtf.format(localDateTime);
        return LoadData.publicHolidays.contains(currDate);
    }

    private boolean isADayBeforePublicHoliday(LocalDateTime localDateTime){
        LocalDateTime localDateTimePlusOneDay = localDateTime.plusDays(1);
        return isPublicHoliday(localDateTimePlusOneDay);
    }

    public boolean isTollFreeTime(TaxInputModel taxInputModel) {
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

    public boolean isVehicleTollFree(String vehicleType){
        if(LoadData.tollFreeVehicleMap.get(vehicleType) == null) {
            return false;
        } else {
            return true;
        }
    }

}
