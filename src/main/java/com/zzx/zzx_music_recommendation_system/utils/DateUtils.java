package com.zzx.zzx_music_recommendation_system.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 20:20
 */
public class DateUtils {

    // 显示日期的格式
    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    // 显示日期的格式
    public static final String yyyyMMddg = "yyyy/MM/dd";

    // 显示日期的格式
    public static final String yyyy = "yyyy";

    // 显示日期的格式
    public static final String yyyyMMdd = "yyyyMMdd";

    // 显示日期的格式
    public static final String yyyy_MM = "yyyy-MM";

    // 显示日期的格式
    public static final String yyyyMM = "yyyyMM";

    // 显示日期的格式
    public static final String MMddHHmm = "MMddHHmm";

    // 显示日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    // 显示日期时间的格式
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    // 显示日期时间的格式
    public static final String yyyyMMdd_HH_mm_ss = "yyyy/MM/dd HH:mm:ss";

    // 显示日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    // 显示日期时间的格式
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    // 显示日期时间的格式
    public static final String yyMMddHHmmss = "yyMMddHHmmss";

    // 显示日期时间的格式
    public static final String yyMMddHHmm = "yyMMddHHmm";


    // 显示简体中文日期的格式
    public static final String yyyy_MM_dd_zh = "yyyy年MM月dd日";

    // 显示简体中文日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_ss_zh = "yyyy年MM月dd日HH时mm分ss秒";

    // 显示简体中文日期时间的格式
    public static final String yyyy_MM_dd_HH_mm_zh = "yyyy年MM月dd日HH时mm分";


    //W——周
    public static final String DATE_UNIT_W = "W";
    //M——月
    public static final String DATE_UNIT_M = "M";
    //Y——年
    public static final String DATE_UNIT_Y = "Y";


    /**
     * 获取时间日期
     *
     * @return 字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getDateyyyyMMddHHmmssStr(LocalDateTime localDateTime) {
        return DateUtil.format(localDateTime, yyyy_MM_dd_HH_mm_ss);
    }

    public static String getDateyyyyMMStr(LocalDateTime localDateTime) {
        return DateUtil.format(localDateTime, yyyy_MM);
    }

    public static String getDateyyyyMMddStr(LocalDateTime localDateTime) {
        return DateUtil.format(localDateTime, yyyy_MM_dd);
    }

    /**
     * 获取当前日期时间
     *
     * @return 字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurDateTimeStr() {
        return DateUtil.format(LocalDateTime.now(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取当前日期时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getCurDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return long yyyyMMddHHmmss
     */
    public static long getCuryyyyMMddHHmmss() {
        return Long.parseLong(DateUtil.format(LocalDateTime.now(), yyyyMMddHHmmss));
    }

    /**
     * 获取日期时间
     *
     * @param datetime 需要解析的时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String datetime, String format) {
        return DateUtil.parseLocalDateTime(datetime, format);
    }

    /**
     * 获取2个时间相差的天数
     * ps:此处天数取整
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 天数
     */
    public static Long getBetweenDays(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toDays();
    }

    /**
     * 获取今日剩余时间秒
     *
     * @return 秒数
     */
    public static Integer getRestDateToday() {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 8, 0, 0);
        return (int) (tomorrowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * description:当日开始时间
     *
     * @param :
     * @return: * @return {@link null}
     */
    public static LocalDateTime getStartTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atTime(0, 0, 0, 0);
    }



    /**
     * description:当日结束时间
     *
     * @param :
     * @return: * @return {@link null}
     */
    public static LocalDateTime getEndTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atTime(23, 59, 59, 59);
    }

    public static String[] getWeekTime() {
        LocalDateTime today = LocalDateTime.now();
        String monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern(yyyyMMdd));
        String sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).format(DateTimeFormatter.ofPattern(yyyyMMdd));
        return new String[]{monday, sunday};
    }

    public static String[] getWeekTime(Integer week) {
        LocalDateTime today = LocalDateTime.now();
        String monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern(yyyyMMdd));
        String sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).format(DateTimeFormatter.ofPattern(yyyyMMdd));
        if (week != null && week > 0) {
            monday = String.valueOf(NumberUtil.parseInt(monday) - week * 7);
            sunday = String.valueOf(NumberUtil.parseInt(monday) - week);
        }
        return new String[]{monday, sunday};
    }
}
