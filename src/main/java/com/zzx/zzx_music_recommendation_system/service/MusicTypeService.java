package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface MusicTypeService extends IService<MusicType> {

    List<MusicType> getMusicType();


}
