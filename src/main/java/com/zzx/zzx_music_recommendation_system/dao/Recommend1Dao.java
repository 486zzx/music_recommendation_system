package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.Recommend1;

import java.util.List;

public interface Recommend1Dao extends IService<Recommend1> {

    boolean isExistRecommends();

    List<String> getMusicIdsByRecommend();
}
