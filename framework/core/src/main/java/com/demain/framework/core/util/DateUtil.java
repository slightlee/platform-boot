package com.demain.framework.core.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.Instant;

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
     * @param zoneId 时区ID，非 null  eg："Asia/Shanghai"
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
     * @param zoneId    时区ID
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
    
}
