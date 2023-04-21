package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;

import java.util.List;

public interface RecommendDao extends IService<Recommend> {

    List<Long> getMusicIdsByRecommend();
}
