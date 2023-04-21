package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-04-04
 */
public interface RecommendService extends IService<Recommend> {

    void updateRecommend();

    List<Long> getRecommendMusic();

}
