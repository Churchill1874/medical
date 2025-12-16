package com.medical.common.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeTools {

    /**
     * 获取几个月之前的时间 并且从1号开始
     * @param month
     * @return
     */
    public static LocalDateTime getBeforeTimeFrom1DayStart(int month) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(month).withDayOfMonth(1);
        return startDate.atStartOfDay();
    }


}
