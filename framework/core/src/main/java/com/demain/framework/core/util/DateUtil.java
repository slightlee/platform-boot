package com.demain.framework.core.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期处理工具类
 *
 * @author demain_lee
 * @since 2024/2/26
 */
@SuppressWarnings("unused")
public class DateUtil {
    
    /**
     * 获取中国时区的 ZoneId 中国的时区通常使用 "Asia/Shanghai"
     *
     * @return ZoneId
     */
    public static ZoneId getChinaZoneId() {
        return ZoneId.of("Asia/Shanghai");
    }
    
    /**
     * 获取指定时区的 ZoneId
     *
     * @param zoneId 时区ID，非 null eg："Asia/Shanghai"
     * @return ZoneId
     */
    public static ZoneId getZoneId(String zoneId) {
        return ZoneId.of(zoneId);
    }
    
    /**
     * 时间戳 转为 UTC LocalDateTime
     *
     * @param timestamp 时间戳 1650718663
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToUtcLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }
    
    /**
     * 时间戳 转为 指定时区 LocalDateTime
     *
     * @param timestamp 时间戳 1650718663
     * @param zoneId 时区ID
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToZoneLocalDateTime(long timestamp, ZoneId zoneId) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, zoneId);
    }
    
    /**
     * LocalDateTime 转为 指定时区 时间戳
     *
     * @param localDateTime localDateTime
     * @param zoneId 时区
     * @return 时间戳
     */
    public static long convertLocalDateTimeToTimestamp(LocalDateTime localDateTime, ZoneId zoneId) {
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return instant.getEpochSecond();
    }
    
    /**
     * 获取当前时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * 获取当前日期
     *
     * @return LocalDate
     */
    public static LocalDate currentLocalDate() {
        return LocalDate.now();
    }
    
    /**
     * 格式化日期
     *
     * @param date 日期
     * @param dateFormat 格式
     * @return 格式化后的日期
     */
    public static String dateFormat(LocalDateTime date, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(formatter);
    }
    
    /**
     * 添加秒
     *
     * @param date 日期
     * @param second 秒
     * @return 添加后的日期
     */
    public static LocalDateTime addSecond(LocalDateTime date, int second) {
        return date.plusSeconds(second);
    }
    
    /**
     * 添加分钟
     *
     * @param date 日期
     * @param minute 分钟
     * @return 添加后的日期
     */
    public static LocalDateTime addMinute(LocalDateTime date, int minute) {
        return date.plusMinutes(minute);
    }
    
    /**
     * 添加小时
     *
     * @param date 日期
     * @param hour 小时
     * @return 添加后的日期
     */
    public static LocalDateTime addHour(LocalDateTime date, int hour) {
        return date.plusHours(hour);
    }
    
    /**
     * 添加天
     *
     * @param date 日期
     * @param day 天
     * @return 添加后的日期
     */
    public static LocalDateTime addDay(LocalDateTime date, int day) {
        return date.plusDays(day);
    }
    
    /**
     * 添加月
     *
     * @param date 日期
     * @param month 月
     * @return 添加后的日期
     */
    public static LocalDateTime addMonth(LocalDateTime date, int month) {
        return date.plusMonths(month);
    }
    
    /**
     * 添加年
     *
     * @param date 日期
     * @param year 年
     * @return 添加后的日期
     */
    public static LocalDateTime addYear(LocalDateTime date, int year) {
        return date.plusYears(year);
    }
    
    public static final String[] WEEK_DAY_OF_CHINESE = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    
    /**
     * 日期转星期
     *
     * @param date 日期
     * @return 星期
     */
    public static String dateToWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return WEEK_DAY_OF_CHINESE[dayOfWeek.getValue() % 7];
    }
    
    /**
     * 获取一天的开始时间
     *
     * @param date 日期 2024-05-09T16:56:47.906073800
     * @return 一天的开始时间 2024-05-09T00:00
     */
    public static LocalDateTime getStartTimeOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            // 获取一天的开始时间，即00:00
            return date.toLocalDate().atStartOfDay();
        }
    }
    
    /**
     * 获取一天的结束时间
     *
     * @param date 日期 2024-05-09T17:00:35.878008700
     * @return 一天的结束时间 2024-05-09T23:59:59.999999999
     */
    public static LocalDateTime getEndTimeOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            // 获取一天的结束时间，即23:59:59.999999999
            return date.toLocalDate().atTime(LocalTime.MAX);
        }
    }
    
    /**
     * 判断当前时间是否在指定时间范围内
     *
     * @param nowTime 当前时间
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return true 在指定时间范围内，false 不在指定时间范围内
     */
    public static Boolean betweenStartAndEnd(Instant nowTime, Instant beginTime, Instant endTime) {
        return nowTime.isAfter(beginTime) && nowTime.isBefore(endTime);
    }
    
}
