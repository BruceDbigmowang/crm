package com.knowhy.crm.util;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TimeUtil {

    public static LocalDate getTechnicalFirstTime(int cycle){
        if(cycle > 1){
            LocalDate localDate = LocalDate.now();
            LocalDate targetTime=null;
            if( localDate.getMonthValue() + cycle > 12){
                targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + cycle - 12, 1);
            }else{
                targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + cycle, 1);
            }
            return targetTime;
        }else{
            LocalDate localDate = LocalDate.now();
            LocalDate targetTime=null;
            if(localDate.getMonthValue() + cycle > 12){
                targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + cycle-12, 15);
            }else{
                targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + cycle, 15);
            }
            return targetTime;
        }
    }

    public static LocalDate getTechnicalSecondTime(int cycle){
        if(cycle > 1){
            LocalDate localDate = LocalDate.now();
            LocalDate targetTime=null;
            if(localDate.getMonthValue() + cycle > 12){
                targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + cycle-12, 1);
            }else{
                targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + cycle, 1);
            }
            targetTime = targetTime.with(TemporalAdjusters.lastDayOfMonth());
            targetTime = targetTime.minusDays(14);
            return targetTime;
        }else{
            LocalDate localDate = LocalDate.now();
            LocalDate targetTime=null;
            if(localDate.getMonthValue() + cycle > 12){
                targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + cycle-12, 1);
            }else{
                targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + cycle, 1);
            }

            targetTime = targetTime.with(TemporalAdjusters.lastDayOfMonth());
            targetTime = targetTime.minusDays(7);
            return targetTime;
        }
    }

    public static LocalDate getTechnicalLastTime(int cycle){
        LocalDate localDate = LocalDate.now();
        LocalDate targetTime=null;
        if(localDate.getMonthValue() + cycle > 12){
            targetTime = LocalDate.of(localDate.getYear() + 1, localDate.getMonthValue() + cycle -12 , 1);
        }else{
            targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + cycle, 1);
        }
        targetTime = targetTime.with(TemporalAdjusters.lastDayOfMonth());
        return targetTime;
    }

    public static LocalDate getReportTimeFirst(){
        LocalDate localDate = LocalDate.now();
        LocalDate targetTime=null;
        if(localDate.getMonthValue() + 1 > 12){
            targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + 1 - 12 , 1);
        }else{
            targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + 1, 1);
        }

        targetTime = targetTime.with(TemporalAdjusters.lastDayOfMonth());
        targetTime = targetTime.minusDays(7);
        return targetTime;

    }

    public static LocalDate getReportTimeSecond(){
        LocalDate localDate = LocalDate.now();
        LocalDate targetTime=null;
        if(localDate.getMonthValue() + 1 > 12){
            targetTime = LocalDate.of(localDate.getYear()+1, localDate.getMonthValue() + 1 - 12, 1);
        }else{
            targetTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue() + 1, 1);
        }

        targetTime = targetTime.with(TemporalAdjusters.lastDayOfMonth());
        return targetTime;

    }
}
