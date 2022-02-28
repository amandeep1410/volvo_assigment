package com.congestion.app.congestionCtrl.rule.tollFreeRule;

import com.congestion.app.congestionCtrl.initializedata.LoadData;
import com.congestion.app.congestionCtrl.model.TaxInputModel;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class TollFreeDate implements ITollFree {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");

    @Override
    public boolean apply(TaxInputModel taxInputModel) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(taxInputModel.getDrivingInDateAndTime(), ZoneOffset.UTC);
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

}
