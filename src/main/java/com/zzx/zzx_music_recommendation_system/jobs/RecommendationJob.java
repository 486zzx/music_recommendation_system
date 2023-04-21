package com.zzx.zzx_music_recommendation_system.jobs;

import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 19:59
 */
@Slf4j
public class RecommendationJob {

    @Autowired
    private RecommendService recommendService;

    public void recommendation() {
        log.info("定时推荐开始!");
        try {
            recommendService.updateRecommend();
            log.info("定时推荐成功!");
        } catch (Exception e) {
            log.info("定时推荐失败!");
            throw new MyException("定时推荐失败!", e);
        }
    }


}
