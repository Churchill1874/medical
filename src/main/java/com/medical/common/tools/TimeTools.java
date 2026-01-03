package com.medical.common.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeTools {

    /**
     * 获取几个月之前的时间 并且从1号开始
     *
     * @param month
     * @return
     */
    public static LocalDateTime getBeforeTimeFrom1DayStart(int month) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(month).withDayOfMonth(1);
        return startDate.atStartOfDay();
    }

    /**
     * 指定日期 00:00:00
     */
    public static LocalDateTime getDayStart(LocalDate date) {
        return date.atStartOfDay();
    }

    /**
     * 指定日期 23:59:59.999999999
     */
    public static LocalDateTime getDayEnd(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

}
