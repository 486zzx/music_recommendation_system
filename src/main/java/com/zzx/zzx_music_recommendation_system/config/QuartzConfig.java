package com.zzx.zzx_music_recommendation_system.config;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


import static com.zzx.zzx_music_recommendation_system.constant.DateTimeConstants.CRON_DATE_TIME_FORMAT;

/**
 * @author zhangzhenxiong
 * @Description 定时任务
 * @create 2022/5/17 17:43
 **/
@Configuration
@Slf4j
public class QuartzConfig {

    @Autowired
    Scheduler scheduler;

    /*public static final JobDetail JOB_DETAIL = JobBuilder.newJob(ExecuteServiceOnTimeJob.class)
            .withIdentity(JOB_NAME, JOB_GROUP)
            //任务就算没有绑定Trigger仍保留在Quartz的JobStore中
            .storeDurably(true)
            .build();

    *//**
     * spring容器初始化bean后启动定时任务
     *//*
    @PostConstruct
    private void startExecuteServiceJobs() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new ("启动服务服务执行定时任务异常！", e);
        }
        List<SysServicePO> timeList = sysServiceDAO.getAutoExecuteTimeList();
        timeList.forEach(sysServicePO -> doJob(getCron(sysServicePO.getAutoExecuteTime()), Long.toString(sysServicePO.getEid()), JobOperationEnum.ADD.getCode(), scheduler));
    }

    *//**
     * 在调度器增/删/改自动执行服务的触发器
     * @param cron 时间
     * @param serviceId 服务id
     * @param operation 操作标识参数
     * @param scheduler 调度器
     *//*
    public static void doJob(String cron, String serviceId, Integer operation, Scheduler scheduler) {
        try {

            if (Objects.isNull(scheduler.getJobDetail(JOB_DETAIL.getKey())) && JobOperationEnum.ADD.getCode().equals(operation)) {
                scheduler.addJob(JOB_DETAIL, false);
            }
            String triggerName = JOB_NAME + StringConstants.LINE_STRING + TRIGGER_NAME + serviceId;
            String triggerGroup = JOB_GROUP + StringConstants.LINE_STRING + TRIGGER_GROUP;

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroup)
                    .usingJobData(SERVICE_ID, serviceId)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .forJob(JOB_DETAIL)
                    .build();

            if (JobOperationEnum.ADD.getCode().equals(operation)) {
                scheduler.scheduleJob(trigger);
            } else if (JobOperationEnum.UPDATE.getCode().equals(operation)) {
                scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup), trigger);
            } else if (JobOperationEnum.DELETE.getCode().equals(operation)) {
                scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
            }

        } catch (SchedulerException e) {
            log.error("定时任务{}:{}出错！", JobOperationEnum.getCodeByDesc(operation), JOB_NAME + StringConstants.LINE_STRING + TRIGGER_NAME + serviceId, e);
            throw new BusinessException("定时任务设置出错!", e);
        }
    }*/

    /**
     * job操作枚举
     */
    public enum JobOperationEnum {
        /**
         * 增
         */
        ADD(1),
        /**
         * 改
         */
        UPDATE(2),
        /**
         * 删
         */
        DELETE(3);

        private final Integer code;

        JobOperationEnum(Integer code) {
            this.code = code;
        }

        public static JobOperationEnum getCodeByDesc(Integer code) {
            if (Objects.nonNull(code)) {
                for (JobOperationEnum value :
                        values()) {
                    if (value.code.equals(code)) {
                        return value;
                    }
                }
            }
            return null;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 将时间转为cron表达式（每天执行）
     * @param date 时间
     * @return cron格式时间
     */
    public static String getCron(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern(CRON_DATE_TIME_FORMAT));
    }

}