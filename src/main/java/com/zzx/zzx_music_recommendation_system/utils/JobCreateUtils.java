package com.zzx.zzx_music_recommendation_system.utils;

import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import org.quartz.*;
import org.springframework.util.StringUtils;

import static com.zzx.zzx_music_recommendation_system.constant.StringConstants.*;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 17:55
 */
public class JobCreateUtils {

    public static JobDetail createJobDetail(Class clazz, String jobName, String jobGroup) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        JobDetail jobDetail = JobBuilder.newJob(clazz)
            .withIdentity(pre + JOB + (StringUtils.hasText(jobName) ? jobName : DEFAULT))
            //任务就算没有绑定Trigger仍保留在Quartz的JobStore中
            .storeDurably(true)
            .build();
        return jobDetail;
    }

    public static JobDetail createJobDetail(Class clazz, String jobName) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(pre + JOB + (StringUtils.hasText(jobName) ? jobName : DEFAULT))
                //任务就算没有绑定Trigger仍保留在Quartz的JobStore中
                .storeDurably(true)
                .build();
        return jobDetail;
    }

    public static Trigger createTrigger(Class clazz, String triggerName, JobDetail jobDetail, String key, String val, String cron) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        triggerName = StringUtils.hasText(triggerName) ? (pre  + TRIGGER + triggerName) : (pre  + TRIGGER + DEFAULT);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .usingJobData(key, val)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobDetail)
                .build();

        return trigger;
    }

    public static Trigger createTrigger(Class clazz, String triggerName, String triggerGroup, JobDetail jobDetail, String key, String val, String cron) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        triggerName = StringUtils.hasText(triggerName) ? (pre  + TRIGGER + triggerName) : (pre  + TRIGGER + DEFAULT);
        triggerGroup = pre + TRIGGER_GROUP + (StringUtils.hasText(triggerGroup) ? triggerGroup : DEFAULT);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .usingJobData(key, val)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobDetail)
                .build();

        return trigger;
    }

    public static Trigger createTrigger(Class clazz, String triggerName, String triggerGroup, JobDetail jobDetail, JobDataMap map, String cron) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        triggerName = StringUtils.hasText(triggerName) ? (pre  + TRIGGER + triggerName) : (pre  + TRIGGER + DEFAULT);
        triggerGroup = pre + TRIGGER_GROUP + (StringUtils.hasText(triggerGroup) ? triggerGroup : DEFAULT);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .usingJobData(map)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobDetail)
                .build();

        return trigger;
    }

    public static Trigger createTrigger(Class clazz, String triggerName, JobDetail jobDetail, JobDataMap map, String cron) {
        String pre = clazz.getSimpleName() + IN_LINE_STRING;
        triggerName = StringUtils.hasText(triggerName) ? (pre  + TRIGGER + triggerName) : (pre  + TRIGGER + DEFAULT);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .usingJobData(map)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobDetail)
                .build();

        return trigger;
    }

}
