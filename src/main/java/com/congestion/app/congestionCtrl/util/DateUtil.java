package com.congestion.app.congestionCtrl.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static boolean checkTime(String startTime, String endTime, String checkTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isInBetween = false;
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true;
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true;
        }
        return isInBetween;
    }

    public static String getLocalTimeStr(Instant instant) {
        LocalDateTime vehicleTaxLocalDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return dtf.format(vehicleTaxLocalDateTime);
    }

}
