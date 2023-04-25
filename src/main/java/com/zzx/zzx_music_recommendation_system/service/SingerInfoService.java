package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.SingerInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface SingerInfoService extends IService<SingerInfo> {

    Map<String, String> getSingerName(List<String> singIds);
}
