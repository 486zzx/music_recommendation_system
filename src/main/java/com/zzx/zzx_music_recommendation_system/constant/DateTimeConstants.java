package com.zzx.zzx_music_recommendation_system.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author admin
 * @Description
 */
public class DateTimeConstants {

    public static final LocalDateTime MIN = LocalDateTime.of(1, 1, 1, 0, 0, 0);

    public static final LocalDateTime JUDGE = LocalDateTime.of(2, 2, 2, 0, 0, 0);

    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";

    public static final String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN);

    public static final DateTimeFormatter STANDARD_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATETIME_PATTERN);

    /**
     * 导出文件名格式
     */
    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * cron表达式每天定时执行
     */
    public static final String CRON_DATE_TIME_FORMAT = "ss mm HH * * ? *";

    /**
     * cron表达式每天零点
     */
    public static final String CRON_EVERYDAY_START_TIME_FORMAT = "0 0 0 * * ? ";
}
