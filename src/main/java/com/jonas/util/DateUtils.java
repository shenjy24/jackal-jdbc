package com.jonas.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author shenjy
 * @date 2021/1/21
 * @description
 */
public class DateUtils {

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * "yyyy-MM-dd HH:mm:ss"格式 转化为 秒时间戳
     *
     * @param dateTime
     * @return
     */
    public static int getSecondFromTime(String dateTime) {
        return (int) (getStampFromTime(dateTime) / 1000);
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"格式 转化为 毫秒时间戳
     *
     * @param dateTime
     * @return
     */
    public static long getStampFromTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD_HH_MM_SS));
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
