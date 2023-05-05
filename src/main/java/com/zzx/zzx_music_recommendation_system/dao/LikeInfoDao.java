package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface LikeInfoDao extends IService<LikeInfo> {

    List<String> updateMouthRank(LocalDateTime startTime, LocalDateTime endTime);

    List<String> updateDayRank(LocalDateTime startTime, LocalDateTime endTime);

    Map<Integer, Long> getTypeCountMap(Long musicId);

    /**
     * 获得该用户的播放记录
     * @param userId
     * @return
     */
    Integer getPlayCount(Long userId);

    List<LikeInfo> getCollectSongs(Long userId);

    Integer getMusicPlayCount(Long musicId);
}
