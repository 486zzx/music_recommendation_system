package com.zzx.zzx_music_recommendation_system.jobs;

import com.zzx.zzx_music_recommendation_system.constant.DateTimeConstants;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 19:58
 */
@Slf4j
public class UpdateRankingJob {

    @Autowired
    private LikeInfoService likeInfoService;

    @Scheduled(cron = DateTimeConstants.CRON_MOUTH_TIME_FORMAT)
    public void updateMouthRank() {
        log.info("更新月榜开始!");
        if (likeInfoService.updateMouthRank()) {
            log.info("更新月榜成功!");
        } else {
            log.error("更新月榜失败!");
        }
        log.info("更新月榜结束!");
    }

    @Scheduled(cron = DateTimeConstants.CRON_EVERYDAY_START_TIME_FORMAT)
    public void updateDayRank() {
        log.info("更新日榜开始!");
        if (likeInfoService.updateDayRank()) {
            log.info("更新日榜成功!");
        } else {
            log.error("更新日榜失败!");
        }
        log.info("更新日榜结束!");
    }

}
