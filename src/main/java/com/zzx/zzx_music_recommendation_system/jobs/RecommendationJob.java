package com.zzx.zzx_music_recommendation_system.jobs;

import com.zzx.zzx_music_recommendation_system.algorithm.RecommendAlgor;
import com.zzx.zzx_music_recommendation_system.constant.DateTimeConstants;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 19:59
 */
@Slf4j
@Component
public class RecommendationJob {

    @Autowired
    private RecommendAlgor recommendAlgor;

    @Scheduled(cron = DateTimeConstants.CRON_EVERYDAY_START_TIME_FORMAT)
    public void recommendation() {
        log.info("定时推荐开始!");
        try {
            recommendAlgor.recommend();
            log.info("定时推荐成功!");
        } catch (Exception e) {
            log.info("定时推荐失败!");
        }
        log.info("定时推荐结束!");
    }


}
