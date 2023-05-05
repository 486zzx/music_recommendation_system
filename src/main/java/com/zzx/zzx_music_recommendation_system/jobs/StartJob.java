package com.zzx.zzx_music_recommendation_system.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/3 21:14
 */
@Component
public class StartJob {

    @Autowired
    private UpdateRankingJob updateRankingJob;

    @Autowired
    private RecommendationJob recommendationJob;

    @PostConstruct
    public void start() {
        updateRankingJob.updateMouthRank();
        updateRankingJob.updateDayRank();
        recommendationJob.recommendation();
    }
}
