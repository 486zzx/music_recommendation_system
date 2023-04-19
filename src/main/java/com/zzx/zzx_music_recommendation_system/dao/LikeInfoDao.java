package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LikeInfoDao extends IService<LikeInfo> {

    List<String> updateMouthRank(LocalDateTime startTime, LocalDateTime endTime);

    List<String> updateDayRank(LocalDateTime startTime, LocalDateTime endTime);
}
